package com.rsldm.myapplication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MagicPackage {
//    log for debug
    private static final Logger logger = Logger.getLogger(MagicPackage.class.getName());

    public static void sendMagicPacket(String macAddress) {
        try {
            byte[] bytes = new byte[102];
            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramSocket socket = new DatagramSocket();

            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xFF;
            }

            byte[] macBytes = macAddressToBytes(macAddress);


            if (macBytes != null) {
                for (int i = 6; i < 102; i += 6) {
                    System.arraycopy(macBytes, 0, bytes, i, 6);
                }
            }


            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to send magic packet", e);
        }
    }

//    need to process to compute string mac address
    private static byte[] macAddressToBytes(String macAddress) {
        String[] hex = macAddress.split("[:-]");
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
        return bytes;
    }
}
