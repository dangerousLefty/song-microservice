package com.hamza.songmicroservice.grpc;

import com.hamza.songmicroservice.exception.SongIdentifierExistsException;
import com.hamza.songmicroservice.model.*;
import com.hamza.songmicroservice.grpc.catalog.CatalogServiceGrpc;
import com.hamza.songmicroservice.grpc.catalog.Empty;
import com.hamza.songmicroservice.grpc.catalog.Song;
import com.hamza.songmicroservice.grpc.catalog.SongIdentifier;
import com.hamza.songmicroservice.publication.SongPublicationService;
import com.hamza.songmicroservice.service.SongService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CatalogGrpcService extends CatalogServiceGrpc.CatalogServiceImplBase {

    private final SongService songService;
    private final SongPublicationService songPublicationService;

    public CatalogGrpcService(
            SongService songService,
            SongPublicationService songPublicationService
    ) {
        this.songService = songService;
        this.songPublicationService = songPublicationService;
    }

    @Override
    public void getSong(SongIdentifier request, StreamObserver<Song> responseObserver) {
        try {
            Optional<com.hamza.songmicroservice.model.Song> songOptional =
                    songService.getSong(request.getId());

            if (songOptional.isEmpty()) {
                responseObserver.onError(
                        Status.NOT_FOUND
                                .withDescription("Song with id " + request.getId() + " was not found.")
                                .asRuntimeException()
                );
                return;
            }

            responseObserver.onNext(toGrpcSong(songOptional.get()));
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Failed to get song: " + e.getMessage())
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void publishSong(Song request, StreamObserver<Empty> responseObserver) {
        try {
            com.hamza.songmicroservice.model.Song song = toModelSong(request);

            songPublicationService.publishSong(song);

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();

        } catch (SongIdentifierExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    private Song toGrpcSong(com.hamza.songmicroservice.model.Song song) {
        return Song.newBuilder()
                .setId(nullToEmpty(song.getSongId()))
                .setAuthorId(nullToEmpty(song.getAuthorId()))
                .setReleaseDate(song.getReleaseDate() == null ? 0L : song.getReleaseDate().getTime())
                .setDurationInSeconds(song.getDurationInSeconds())
                .setArtifactUri(nullToEmpty(song.getArtifactUri()))
                .build();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private com.hamza.songmicroservice.model.Song toModelSong(Song grpcSong) {
        com.hamza.songmicroservice.model.Song song =
                new com.hamza.songmicroservice.model.Song();

        song.setSongId(grpcSong.getId());
        song.setAuthorId(grpcSong.getAuthorId());
        song.setReleaseDate(new Date(grpcSong.getReleaseDate()));
        song.setDurationInSeconds(grpcSong.getDurationInSeconds());
        song.setArtifactUri(grpcSong.getArtifactUri());

        return song;
    }
}
