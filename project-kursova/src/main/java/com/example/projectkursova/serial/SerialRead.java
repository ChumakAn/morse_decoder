package com.example.projectkursova.serial;

import com.example.projectkursova.decoder.MorseDecoder;
import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class SerialRead {

    public static String readDatFromArduino() {
        Scanner console = new Scanner(System.in);
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println("List COM ports");
        SerialPort comPorts[] = SerialPort.getCommPorts();
        for (int i = 0; i < comPorts.length; i++)
            System.out.println("comPorts[" + i + "] = " + comPorts[i].getDescriptivePortName());
        int port = 1;     // array index to select COM port
        comPorts[port].openPort();
        System.out.println("open port comPorts[" + port + "]  " + comPorts[port].getDescriptivePortName());
        comPorts[port].setBaudRate(9600);
        try {
            while (true) {
                if (System.in.available() > 0) {
                    String s = console.nextLine() + "\n";
                    byte[] writeBuffer = s.getBytes();
                    comPorts[port].writeBytes(writeBuffer, writeBuffer.length);
                }

                while (comPorts[port].bytesAvailable() > 0) {
                    byte[] readBuffer = new byte[comPorts[port].bytesAvailable()];
                    int numRead = comPorts[port].readBytes(readBuffer, readBuffer.length);

                    for (byte b : readBuffer) {
                        if (b == 63) {
                            System.out.println(MorseDecoder.decodeLine(stringBuffer.toString()));
                            return MorseDecoder.decodeLine(stringBuffer.toString());
                        }
                        System.out.print((char) b);
                        stringBuffer.append((char) b);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("read data: " + stringBuffer.toString());
        return "err: no data";
    }
}