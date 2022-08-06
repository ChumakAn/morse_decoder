package com.example.projectkursova.serial;

import com.fazecast.jSerialComm.SerialPort;

public class SerialCommunication {
    private static final SerialPort[] SERIAL_PORTS = SerialPort.getCommPorts();
    private static final SerialPort COM_1 = SERIAL_PORTS[1];

    public static void openPort() {
        if (COM_1.isOpen()) {
            try {
                COM_1.openPort();
                System.out.println("Port was open");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Port could not be open!");
            }
        } else {
            System.out.println("Port is already opened");
        }
    }

    public static void closePort() {
        if (COM_1.isOpen()) {
            COM_1.closePort();
        }
        System.out.println("Port is closed");
    }
}

