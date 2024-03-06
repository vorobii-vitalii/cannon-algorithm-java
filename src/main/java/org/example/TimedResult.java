package org.example;

public record TimedResult<T>(T res, long duration) {

}
