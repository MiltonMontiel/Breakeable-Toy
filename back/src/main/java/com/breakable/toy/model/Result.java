package com.breakable.toy.model;

public record Result<T>(Status status, String message, T data) {
    public enum Status {
        Ok,
        Err,
    }
}
