package ws.server;

import java.net.InetSocketAddress;

import org.java_websocket.server.WebSocketServer;

public class App {
    public static void main(String[] args) {
        String host = "192.168.0.102";
        int port = 8887;

        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
    }
}
