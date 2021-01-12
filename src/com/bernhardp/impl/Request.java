package com.bernhardp.impl;

public class Request {
    public enum Direction{
        UP,
        DOWN
    }

    private final int currentFloor;
    private final int nextFloor;
    private final Direction direction;

    public Request(final int currentFloor, final int nextFloor){
        this.currentFloor = currentFloor;
        this.nextFloor = nextFloor;
        if(nextFloor > currentFloor)
            this.direction = Direction.UP;
        else
            this.direction = Direction.DOWN;
    }

    public String print(){
        return "from current: " + currentFloor
                + "to: " + nextFloor
                + ", " + direction
                + "";
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getNextFloor() {
        return nextFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
