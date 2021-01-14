package com.bernhardp.interfaces;

import com.bernhardp.utils.ElevatorDirection;

public interface ElevatorFactory{
    public void moveUp();
    public void moveDown();
    public int getCurrentFloor();
    public void setCurrentFloor(int floor);
    public void setStatus(ElevatorDirection direction);
    public String print();
}
