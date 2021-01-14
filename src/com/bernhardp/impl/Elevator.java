package com.bernhardp.impl;

import com.bernhardp.interfaces.ElevatorFactory;
import com.bernhardp.utils.Constants;
import com.bernhardp.utils.ElevatorDirection;
import com.bernhardp.utils.IdCounter;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Elevator implements ElevatorFactory {
    private int currentFloor;
    public int id;
    private Queue<Integer> nextFloors;
    public ElevatorDirection status;

    public Elevator() {
        this.id = IdCounter.getInstance().generateNextId();;
        this.currentFloor = 0;
        this.status = ElevatorDirection.ELEVATOR_HOLD;
    }

    @Override
    public void moveUp() {
        currentFloor++;
    }

    @Override
    public void moveDown() {
        currentFloor--;
    }

    public int getElevatorId(){
        return this.id;
    }


    public String getStatus(){
        return this.status.toString();
    }

    public int nextFloor(){
        return this.nextFloors.peek();
    }

    @Override
    public int getCurrentFloor(){
        return this.currentFloor;
    }

    @Override
    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    @Override
    public void setStatus(ElevatorDirection status){
        this.status = status;
    }

    @Override
    public String print(){
        return "Elevator(id: " + id + ", currentFloor: " + currentFloor + ")";
    }


}
