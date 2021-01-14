package com.bernhardp.impl;

import com.bernhardp.interfaces.RequestFactory;

public class Request implements RequestFactory {
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

    @Override
    public String print(){
        return "from current: " + currentFloor + " to: " + nextFloor + ", " + direction + ".";
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public int getNextFloor() {
        return nextFloor;
    }

}
