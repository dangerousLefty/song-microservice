package com.hamza.songmicroservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.hamza.songmicroservice.model.Song;
import com.hamza.songmicroservice.repository.dynamodb.DynamoDBSongItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SongsRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public SongsRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Optional<Song> getSong(String songId) {
        DynamoDBSongItem item = dynamoDBMapper.load(DynamoDBSongItem.class, songId);

        if (item == null) {
            return Optional.empty();
        }

        return Optional.of(item.toSong());
    }

    public void storeSong(Song song) {
        DynamoDBSongItem item = DynamoDBSongItem.fromSong(song);
        dynamoDBMapper.save(item);
    }
}