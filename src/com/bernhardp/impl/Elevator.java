package com.bernhardp.impl;

import com.bernhardp.interfaces.ElevatorFactory;
import com.bernhardp.utils.Constants;
import com.bernhardp.utils.ElevatorDirection;
import com.bernhardp.utils.IdGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Elevator implements ElevatorFactory {
    private Integer currentFloor;
    public Integer id;
    private Queue<Integer> nextFloors;
    public ElevatorDirection status;

    public Elevator() {
        this.id = IdGenerator.getInstance().getNextElevatorId();;
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

    public Integer getElevatorId(){
        return this.id;
    }

    public void moveElevator(){
        while(status != ElevatorDirection.ELEVATOR_HOLD){
            if(this.status==ElevatorDirection.ELEVATOR_UP){
                System.out.println("status is up");
                if(currentFloor != nextFloor()) {
                    moveUp();
                    System.out.println("current floor: " + currentFloor);
                }
                if(currentFloor == nextFloor()){
                    System.out.println("Floor reached");
                    nextFloors.poll();
                    //status = ElevatorDirection.ELEVATOR_HOLD;
                }
            }
            if(this.status==ElevatorDirection.ELEVATOR_DOWN){
                if(currentFloor != nextFloor()) {
                    moveDown();
                    System.out.println("current floor: " + currentFloor);
                }
                if(currentFloor == nextFloor()){
                    System.out.println("Floor reached");
                    nextFloors.poll();
                    //status = ElevatorDirection.ELEVATOR_HOLD;
                }
            }
            if(this.nextFloors.isEmpty()){
                status = ElevatorDirection.ELEVATOR_HOLD;
            }
        }
    }

    public String getStatus(){
        return this.status.toString();
    }

    public int nextFloor(){
        return this.nextFloors.peek();
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public void setCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    public void setStatus(ElevatorDirection status){
        this.status = status;
    }

    public String print(){
        return "Elevator(id: " + id + ", currentFloor: " + currentFloor + ")";
    }

    public static List<Elevator> getElevators() {
        return IntStream.range(0, Constants.NUMBER_OF_ELEVATORS)
                .mapToObj(i -> new Elevator())
                .collect(Collectors.toList());
    }

    @Override
    public void addNewFloor(Integer floor) {
        this.nextFloors.add(floor);
        System.out.println("Next floor for elevator " + this.id + " is " +floor);
        System.out.println(this.nextFloors.peek());
        if(this.nextFloors.peek() > currentFloor){
            System.out.println("smalelr");
            this.status = ElevatorDirection.ELEVATOR_UP;
        }
        if(this.nextFloors.peek() < currentFloor){
            System.out.println("bigger");
            this.status = ElevatorDirection.ELEVATOR_DOWN;
        }
        System.out.println(this.status);
        moveElevator();

    }


}
