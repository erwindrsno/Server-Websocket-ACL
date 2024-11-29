package ws.server;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ws.server.lab.*;

public class SimpleServer extends WebSocketServer {
    final int CHUNK_SIZE = 10240;
    Logger logger = LoggerFactory.getLogger(SimpleServer.class);
    Scanner sc = new Scanner(System.in);
    private final ConcurrentHashMap<String, WebSocket> clients01 = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocket> clients02 = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocket> clients03 = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocket> clients04 = new ConcurrentHashMap<>();

    BaseLab l1 = new LabOne();
    BaseLab l2 = new LabTwo();

    public SimpleServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("Client connected from " + conn.getRemoteSocketAddress().getAddress());

        //TODO: Temporary storing UUID as key
        //IDEA: STORING LAB NAME 
        //e.g: LAB01-08, and etc
        // Map<String, Integer> map = new HashMap<>();
        // String clientId = UUID.randomUUID().toString();
        // clients.put(clientId, conn);
        String ip = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        if(ip.startsWith("10.100.71")){
            clients01.put(conn.getRemoteSocketAddress().getAddress().getHostAddress(), conn);
        } 
        else if(ip.startsWith("10.100.72")){
            clients02.put(conn.getRemoteSocketAddress().getAddress().getHostAddress(), conn);
        }
        else if(ip.startsWith("10.100.73")){
            clients03.put(conn.getRemoteSocketAddress().getAddress().getHostAddress(), conn);
        } 
        else{
            clients04.put(conn.getRemoteSocketAddress().getAddress().getHostAddress(), conn);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        // logger.info("Received Pong from : " + conn.getRemoteSocketAddress());
        if(message.equals("PONG")){
            String connIp = conn.getRemoteSocketAddress().getAddress().getHostAddress();
            if(connIp.startsWith("10.100.71")){
                l1.setActiveByIP(connIp);
                logger.info(l1.getHostnameByIP(connIp) + " is active");
            } 
            else if(connIp.startsWith("10.100.72")){
                l2.setActiveByIP(connIp);
                logger.info(l2.getHostnameByIP(connIp) + " is active");
            }
            // else if(connIp.startsWith("10.100.73")){
            //     l3.setActiveByIP(connIp);
            // } 
            // else{
            //     l4.setActiveByIP(connIp);
            // }
            // l2.setActiveByIP(connIp);
            // logger.info(l2.getHostnameByIP(connIp) + " is active");
            // logger.info("Received Pong from : " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        String myIP = "";
        try{
            InetAddress me = InetAddress.getLocalHost();
            myIP += me.getHostAddress();
        } catch(Exception e){
            logger.error("Error getting localhost IP address");
            e.printStackTrace();
        }
        logger.info("Server started at ws://" + myIP + ":8887");

        new Thread(() -> {
            logger.info("Command receiver thread started");
            while(true){
                System.out.print("Command : ");
                String command = sc.nextLine();
                switch(command){
                    case "ping":
                        broadcast("PING");
                        break;

                    case "ping 1":
                        pingLabOne();
                        break;

                    case "ping 2":
                        pingLabTwo();
                        break;

                    case "ping 3":
                        pingLabThree();
                        break;

                    case "ping 4":
                        pingLabFour();
                        break;
                    
                    case "send":
                        sendFile();
                        break;
                }
            }
        }).start();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(
                "closed " + conn.getRemoteSocketAddress().getAddress() + " with exit code " + code + " additional info: " + reason);
    }

    public void sendFile() {
        File file = new File("T06xxyyy.zip");

        if (!file.exists()) {
            logger.error("File not found");
            return;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;

            // logger.info("Sending file by chunking (10240 bytes)");
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                logger.info("Sending 10240");
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);

                broadcast(byteBuffer);
            }
            logger.info("File sent");
        } catch (Exception e) {
            logger.error("Error at sending file");
            e.printStackTrace();
        }

        try {
            //TODO : NEED TO REHANDLE

            // logger.info("Sending file name and user name");
            // String fileName = file.getName();
            // UserFile userFile = new UserFile("car", fileName);

            // ObjectMapper mapper = new ObjectMapper();
            // String json = mapper.writeValueAsString(userFile);
            // broadcast(json);
            // logger.info("File name and user name sent");
        } catch (Exception e) {
            logger.error("error at sending file name and user name");
            e.printStackTrace();
        }
    }

    public void pingLabOne(){
        for(WebSocket client : clients01.values()){
            client.send("PING");
        }
    }

    public void pingLabTwo(){
        for(WebSocket client : clients02.values()){
            client.send("PING");
        }
    }

    public void pingLabThree(){
        for(WebSocket client : clients03.values()){
            client.send("PING");
        }
    }

    public void pingLabFour(){
        for(WebSocket client : clients04.values()){
            client.send("PING");
        }
    }
}