package com.sockets;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPServerClass {
    public static void main(String[] args) throws IOException {
        InetSocketAddress port = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(port, 0);

        server.createContext("/hello", new HelloHandler());

        server.start();
    }
}

class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream out = exchange.getResponseBody();
        String htmlResponse = "world";

        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");

        exchange.sendResponseHeaders(200, htmlResponse.length());
        out.write(htmlResponse.getBytes());
        exchange.close();
    }
}