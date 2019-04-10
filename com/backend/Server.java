package com.backend;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {


    private final static int SERVER_PORT = 8080;
    private final static int CLIENT_PORT = 8082;

    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);
        Worker worker = new Worker(InetAddress.getLocalHost());
        System.out.println("Server: Listening on port " + SERVER_PORT);

        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (this) {
                            if(worker.getUpdateToggle()) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                ObjectOutput out;
                                try {
                                    out = new ObjectOutputStream(bos);
                                    out.writeObject(worker.getInputObj());
                                    out.flush();
                                    byte[] dataPacket = bos.toByteArray();
                                    DatagramPacket sendPacket = new DatagramPacket(
                                            dataPacket,
                                            dataPacket.length,
                                            worker.getIp(),
                                            CLIENT_PORT
                                    );
                                    serverSocket.send(sendPacket);
                                    worker.setUpdateToggle(false);
                                } finally {
                                    try {
                                        bos.close();
                                    } catch (IOException e) {
                                        // ignore close exception
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (this) {

                            byte[] dataPacket = new byte[1000];

                            DatagramPacket receivePacket = new DatagramPacket(
                                    dataPacket,
                                    dataPacket.length
                            );

                            serverSocket.receive(receivePacket);

                            ByteArrayInputStream bis = new ByteArrayInputStream(dataPacket);
                            ObjectInput in = null;
                            try {
                                in = new ObjectInputStream(bis);
                                worker.handleClientObject(in.readObject());
                            } finally {
                                try {
                                    if (in != null) {
                                        in.close();
                                    }
                                } catch (IOException ex) {
                                    // ignore close exception
                                }
                            }
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        send.start();
        receive.start();

        send.join();
        receive.join();
    }
}