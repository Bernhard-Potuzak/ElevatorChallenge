package com.bernhardp.utils;

/**counter to increment the id of the elevators automatically */
public class IdCounter {
    private static IdCounter instance = null;

    private int nextId;

    public static IdCounter getInstance() {
        if (instance == null) {
            instance = new IdCounter();
        }

        return instance;
    }

    private IdCounter() {
        nextId = 0;
    }


    public int generateNextId() {
        return nextId++;
    }
}
