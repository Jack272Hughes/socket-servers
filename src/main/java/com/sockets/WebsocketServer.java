package com.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class WebsocketServer {
    private static final String WEBSOCKET_KEY_CONSTANT = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final String OUTPUT_HEADERS = "HTTP/1.1 101 Switching Protocols\r\n" +
        "Upgrade: websocket\r\n" +
        "Connection: Upgrade\r\n" +
        "Sec-WebSocket-Accept: ";

    public static void main(String[] args) {
        try {
            java.net.ServerSocket server = new ServerSocket(8080);
            Socket connection = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream out = connection.getOutputStream();

            String websocketKey = "";
            String line = in.readLine();
            while (!line.isEmpty()) {
                if (line.contains("Sec-WebSocket-Key")) {
                    websocketKey = line.substring(19);
                    break;
                }
                line = in.readLine();
            }

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedKey = md.digest((websocketKey + WEBSOCKET_KEY_CONSTANT).getBytes());
            String secretKey = Base64.getEncoder().encodeToString(hashedKey);

            byte[] response = (OUTPUT_HEADERS + secretKey + "\r\n\r\n").getBytes();
            System.out.println(new String(response));
            out.write(response);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.getMessage();
        }
    }
}
