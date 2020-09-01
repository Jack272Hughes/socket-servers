package com.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServerSocket {
    private static final String OUTPUT_HEADERS = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "Access-Control-Allow-Origin: *\r\n" +
            "Content-Length: ";
    private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

    public static void main(String[] args) {
        try {
            java.net.ServerSocket server = new ServerSocket(8080);
            Socket connection = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

            String[] requestHead = in.readLine().split(" ");
            for (String s : requestHead) {
                System.out.println(s);
            }

            if (requestHead[0].equals("GET")) {
                String OUTPUT = "You made a get request to " + requestHead[1];
                out.println(OUTPUT_HEADERS + OUTPUT.length() + OUTPUT_END_OF_HEADERS + OUTPUT);
            }


            String line = in.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = in.readLine();
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
