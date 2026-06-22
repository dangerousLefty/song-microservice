package com.hamza.songmicroservice.service;

import com.hamza.songmicroservice.model.Song;
import com.hamza.songmicroservice.exception.SongIdentifierExistsException;
import com.hamza.songmicroservice.repository.SongsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SongService {

    private final SongsRepository songsRepository;

    public SongService(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public Optional<Song> getSong(String songId) {
        return songsRepository.getSong(songId);
    }

    public void storeSong(Song song) {
        Optional<Song> existingSong = getSong(song.getSongId());

        if (existingSong.isPresent()) {
            throw new SongIdentifierExistsException(song.getSongId());
        }

        songsRepository.storeSong(song);
    }
}