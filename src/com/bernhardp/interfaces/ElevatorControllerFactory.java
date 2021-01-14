package com.bernhardp.interfaces;

import com.bernhardp.impl.Elevator;

public interface ElevatorControllerFactory {
    public void addRequest(int currentFloor, int floor) throws InterruptedException;
}
