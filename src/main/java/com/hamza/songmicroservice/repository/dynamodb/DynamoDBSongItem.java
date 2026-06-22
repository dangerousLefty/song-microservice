package com.hamza.songmicroservice.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.hamza.songmicroservice.model.Song;

import java.util.Date;

@DynamoDBTable(tableName = "songs")
public class DynamoDBSongItem {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "author_id")
    private String authorId;

    @DynamoDBAttribute(attributeName = "release_date_in_epoch_millis")
    private long releaseDateInEpochMillis;

    @DynamoDBAttribute(attributeName = "duration_in_seconds")
    private long durationInSeconds;

    @DynamoDBAttribute(attributeName = "artifact_uri")
    private String artifactUri;

    public static DynamoDBSongItem fromSong(Song song) {
        DynamoDBSongItem item = new DynamoDBSongItem();

        item.setId(song.getSongId());
        item.setAuthorId(song.getAuthorId());
        item.setReleaseDateInEpochMillis(song.getReleaseDate().getTime());
        item.setDurationInSeconds(song.getDurationInSeconds());
        item.setArtifactUri(song.getArtifactUri());

        return item;
    }

    public Song toSong() {
        Song song = new Song();

        song.setSongId(this.id);
        song.setAuthorId(this.authorId);
        song.setReleaseDate(new Date(this.releaseDateInEpochMillis));
        song.setDurationInSeconds(this.durationInSeconds);
        song.setArtifactUri(this.artifactUri);

        return song;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public long getReleaseDateInEpochMillis() {
        return releaseDateInEpochMillis;
    }

    public void setReleaseDateInEpochMillis(long releaseDateInEpochMillis) {
        this.releaseDateInEpochMillis = releaseDateInEpochMillis;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getArtifactUri() {
        return artifactUri;
    }

    public void setArtifactUri(String artifactUri) {
        this.artifactUri = artifactUri;
    }


}
