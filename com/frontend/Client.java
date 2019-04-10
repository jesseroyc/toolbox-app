package com.frontend;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    final static int SERVER_PORT = 8080;
    final static int CLIENT_PORT = 8082;

    public static void main(String args[]) throws IOException, InterruptedException {

        DatagramSocket clientServer = new DatagramSocket(CLIENT_PORT);
        GUI gui = new GUI(InetAddress.getLocalHost());
        System.out.println("Client is running on port " + CLIENT_PORT);

        Thread csend = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (this) {
                            if(gui.getUpdateToggle()){
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                try {
                                    ObjectOutput out = new ObjectOutputStream(bos);
                                    out.writeObject(gui.sendObjects());
                                    out.flush();
                                    byte[] data = bos.toByteArray();
                                    DatagramPacket sendPacket = new DatagramPacket(
                                            data,
                                            data.length,
                                            gui.getIp(),
                                            SERVER_PORT
                                    );
                                    clientServer.send(sendPacket);
                                } finally {
                                    try {
                                        bos.close();
                                    } catch (IOException ex) {
                                        // ignore close exception
                                    }
                                }
                            }
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
        });

        Thread creceive;
        creceive = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {

                    while (true) {
                        synchronized (this)
                        {
                            byte[] dataPacket = new byte[1000];

                            DatagramPacket receiveData = new DatagramPacket(
                                    dataPacket,
                                    dataPacket.length
                            );

                            clientServer.receive(receiveData);

                            ByteArrayInputStream bis = new ByteArrayInputStream(dataPacket);
                            ObjectInput in = null;
                            try {
                                in = new ObjectInputStream(bis);
                                gui.receiveObjects(in.readObject());
                            }
                            catch (Exception e) {
                                System.out.println("ERROR: " + e);
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (in != null) {
                                        in.close();
                                        bis.close();
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

        csend.start();
        creceive.start();

        csend.join();
        creceive.join();
    }
}
