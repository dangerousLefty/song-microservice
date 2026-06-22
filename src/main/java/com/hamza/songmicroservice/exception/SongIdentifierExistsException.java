package com.hamza.songmicroservice.exception;

public class SongIdentifierExistsException extends RuntimeException {

    public SongIdentifierExistsException(String songId) {
        super("Song with identifier " + songId + " already exists.");
    }
}
