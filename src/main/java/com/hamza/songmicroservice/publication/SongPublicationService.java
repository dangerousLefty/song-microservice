package com.hamza.songmicroservice.publication;

import com.hamza.songmicroservice.model.Song;
import com.hamza.songmicroservice.service.SongService;
import org.springframework.stereotype.Service;

@Service
public class SongPublicationService {

    private final SongService songService;
    private final SongPublicationNotifier songPublicationNotifier;

    public SongPublicationService(
            SongService songService,
            SongPublicationNotifier songPublicationNotifier
    ) {
        this.songService = songService;
        this.songPublicationNotifier = songPublicationNotifier;
    }

    public void publishSong(Song song) {
        songService.storeSong(song);
        songPublicationNotifier.notifySongPublished(song);
    }
}