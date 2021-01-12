package com.bernhardp.utils;

public class IdGenerator {
    private static IdGenerator instance = null;

    private int nextRequestId;
    private int nextElevatorId;

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }

        return instance;
    }

    private IdGenerator() {
        nextRequestId = 0;
        nextElevatorId = 0;
    }

    public int getNextRequestId() {
        return nextRequestId++;
    }

    public int getNextElevatorId() {
        return nextElevatorId++;
    }
}
