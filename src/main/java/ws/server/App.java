package ws.server;

import java.net.InetSocketAddress;

import org.java_websocket.server.WebSocketServer;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // System.out.println("Hello World!");
        String host = "localhost";
        int port = 8887;

        WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
        server.run();
    }
}
