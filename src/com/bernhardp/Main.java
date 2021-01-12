package com.bernhardp;

import com.bernhardp.impl.Elevator;
import com.bernhardp.impl.ElevatorController;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        ElevatorController controller = new ElevatorController();
        controller.startProcessingRequests();
        controller.addRequest(0,7);
        controller.addRequest(2,34);
        controller.addRequest(7,42);
        controller.addRequest(2,22);
        controller.addRequest(32,0);
        controller.checkAvailableElevators();


    }
}
