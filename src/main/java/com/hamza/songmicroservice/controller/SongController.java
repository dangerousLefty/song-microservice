package com.hamza.songmicroservice.controller;

import com.hamza.songmicroservice.model.Song;
import com.hamza.songmicroservice.exception.SongIdentifierExistsException;
import com.hamza.songmicroservice.publication.SongPublicationService;
import com.hamza.songmicroservice.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/songs")
public class SongController {

    private static final Logger logger = LoggerFactory.getLogger(SongController.class);

    private final SongService songService;
    private final SongPublicationService songPublicationService;

    public SongController(
            SongService songService,
            SongPublicationService songPublicationService
    ) {
        this.songService = songService;
        this.songPublicationService = songPublicationService;
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSong(@PathVariable String songId) {
        logger.info("Retrieving song with id: {}", songId);

        return songService.getSong(songId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> publishSong(@RequestBody Song song) {
        logger.info("Publishing song with id: {}", song.getSongId());

        songPublicationService.publishSong(song);

        return ResponseEntity
                .created(URI.create("/songs/" + song.getSongId()))
                .build();
    }

    public record ErrorResponse(String message) { }
}