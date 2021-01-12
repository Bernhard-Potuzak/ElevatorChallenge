package com.bernhardp.impl;

import com.bernhardp.interfaces.ElevatorControllerFactory;
import com.bernhardp.utils.Constants;
import com.bernhardp.utils.ElevatorDirection;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.Math.abs;

public class ElevatorController implements ElevatorControllerFactory {
    private BlockingQueue<Elevator> elevatorList;
    private BlockingQueue<Request> requests;
    private Integer overallFloors;
    private final ExecutorService executor;

    public ElevatorController(){
        this.overallFloors = Constants.MAXIMUM_FLOOR_NUMBER;
        this.elevatorList = new LinkedBlockingQueue<>(Elevator.getElevators());
        /*for(int i = 0; i < Constants.NUMBER_OF_ELEVATORS; i++){
            elevatorList.add(new Elevator(i));
        }*/
        this.requests = new LinkedBlockingQueue<>();
        this.executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_ELEVATORS);
    }

    public void startProcessingRequests() {
        new Thread(() -> {
            while (true) {
                processNextRequest();
            }
        }).start();
    }


    private void processNextRequest() {
        try {
            final var nextElevatorRequest = requests.take();
            final var freeElevator = elevatorList.take();

            executor.submit(() -> {
                print(freeElevator, "starts moving", nextElevatorRequest);
                //TODO: Change this part
                try {
                    Thread.sleep(calculateElevatorMovementDelay(nextElevatorRequest, freeElevator));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                freeElevator.setCurrentFloor(nextElevatorRequest.getNextFloor());
                if(nextElevatorRequest.getNextFloor() < freeElevator.getCurrentFloor())
                    freeElevator.setStatus(ElevatorDirection.ELEVATOR_DOWN);
                if(nextElevatorRequest.getNextFloor() > freeElevator.getCurrentFloor())
                    freeElevator.setStatus(ElevatorDirection.ELEVATOR_UP);
                try {
                    elevatorList.put(freeElevator);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print(freeElevator, "arrived at destination", nextElevatorRequest);
                freeElevator.setStatus(ElevatorDirection.ELEVATOR_HOLD);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(final Elevator elevator, final String printStatement, final Request request){
        System.out.printf("%s - %s - %s.\n", elevator.print(), printStatement, request.print());
        Elevator value;
        BlockingQueue<Elevator> checkList = elevatorList;
//        while ((value=checkList.poll())!=null){
//            if(value.getStatus()=="ELEVATOR_HOLD"){
//                System.out.println("Elevator " + value.getElevatorId() + " is available");
//            }
//        }
        Iterator iteratorValues = elevatorList.iterator();

        // Print elements of iterator
        while (iteratorValues.hasNext()) {
            System.out.println("Free elevator(s): " + iteratorValues.next());
        }
    }

    @Override
    public void addRequest(int currentFloor, int floor) throws InterruptedException {
        Request request = new Request(currentFloor,floor);
        requests.put(request);
    }


    private long calculateElevatorMovementDelay(final Request nextElevatorRequest, final Elevator freeElevator) {
        // Wait until the elevator arrives to the floor where the request was made from.
        int secondsToWaitUntilElevatorArrivesAtOrigin = abs(freeElevator.getCurrentFloor() - nextElevatorRequest.getCurrentFloor());
        // Wait until the elevator goes from request starting floor to destination floor.
        int secondsToWaitUntilElevatorArrivesAtDestination =
                abs(nextElevatorRequest.getCurrentFloor() - nextElevatorRequest.getNextFloor());
        return TimeUnit.SECONDS.toMillis(secondsToWaitUntilElevatorArrivesAtOrigin + secondsToWaitUntilElevatorArrivesAtDestination);
    }

    //TODO: implement checking for available elevators
    @Override
    public void checkAvailableElevators() {
        Elevator value;
        BlockingQueue<Elevator> checkList = elevatorList;
        while ((value=checkList.poll())!=null){
            if(value.getStatus()=="ELEVATOR_HOLD"){
                System.out.println("Elevator " + value.getElevatorId() + " is available");
            }
        }
    }

    @Override
    public void addElevator(Elevator elevator) {
        elevatorList.add(elevator);
    }

}
