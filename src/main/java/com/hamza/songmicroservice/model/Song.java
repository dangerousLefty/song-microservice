package com.hamza.songmicroservice.model;

import java.util.Date;

public class Song {
    private String songId;
    private String authorId;
    private Date releaseDate;
    private long durationInSeconds;
    private String artifactUri;


    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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
