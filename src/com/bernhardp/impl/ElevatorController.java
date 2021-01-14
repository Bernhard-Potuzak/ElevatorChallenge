package com.bernhardp.impl;

import com.bernhardp.interfaces.ElevatorControllerFactory;
import com.bernhardp.utils.Constants;
import com.bernhardp.utils.ElevatorDirection;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class ElevatorController implements ElevatorControllerFactory {
    private BlockingQueue<Elevator> elevatorList;
    private BlockingQueue<Request> requests;
    private Integer overallFloors;
    private final ExecutorService executor;

    public ElevatorController(){
        this.overallFloors = Constants.MAXIMUM_FLOOR_NUMBER;
        this.elevatorList = new LinkedBlockingQueue<>(getElevators());
        this.requests = new LinkedBlockingQueue<>();
        this.executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_ELEVATORS);
    }
    /**Function for creating the Threads for the requests.
     * implemented with a lambda function to directly implement the runnable */
    public void startProcessingRequests() {
        new Thread(() -> {
            while (true) {
                processNextRequest();
            }
        }).start();
    }


    /**the "main" function, here is the simulation part */
    private void processNextRequest() {
        try {
            final var nextRequest = requests.take();
            final var nextElevator = elevatorList.take();

            executor.submit(() -> {
                print(nextElevator, "starts moving", nextRequest);
                //To simulate the movement of the elevator let thread sleep
                int originWaitTime = abs(nextElevator.getCurrentFloor() - nextRequest.getCurrentFloor());
                int timeToArriveAtFloor = abs(nextRequest.getCurrentFloor() - nextRequest.getNextFloor());
                long waitingTime =  TimeUnit.SECONDS.toMillis(originWaitTime + timeToArriveAtFloor);
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //If time is up, elevator has reached the destination floor and can be put in to the list again
                nextElevator.setCurrentFloor(nextRequest.getNextFloor());
                if(nextRequest.getNextFloor() < nextElevator.getCurrentFloor())
                    nextElevator.setStatus(ElevatorDirection.ELEVATOR_DOWN);
                if(nextRequest.getNextFloor() > nextElevator.getCurrentFloor())
                    nextElevator.setStatus(ElevatorDirection.ELEVATOR_UP);
                try {
                    elevatorList.put(nextElevator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print(nextElevator, "arrived at destination ", nextRequest);
                nextElevator.setStatus(ElevatorDirection.ELEVATOR_HOLD);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(final Elevator elevator, final String printStatement, final Request request){
        System.out.printf("%s - %s - %s.\n", elevator.print(), printStatement, request.print());
    }

    @Override
    public void addRequest(int currentFloor, int floor) throws InterruptedException {
        Request request = new Request(currentFloor,floor);
        requests.put(request);
    }

    /** Here the 7 elevators are created */
    public static List<Elevator> getElevators() {
        List<Elevator>availableElevators = new ArrayList<>();
        Elevator elevator1 = new Elevator();
        availableElevators.add(elevator1);
        Elevator elevator2 = new Elevator();
        availableElevators.add(elevator2);
        Elevator elevator3 = new Elevator();
        availableElevators.add(elevator3);
        Elevator elevator4 = new Elevator();
        availableElevators.add(elevator4);
        Elevator elevator5 = new Elevator();
        availableElevators.add(elevator5);
        Elevator elevator6 = new Elevator();
        availableElevators.add(elevator6);
        Elevator elevator7 = new Elevator();
        availableElevators.add(elevator7);
        return availableElevators;
    }


}
