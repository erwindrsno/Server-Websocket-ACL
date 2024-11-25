package ws.server;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleServer extends WebSocketServer {
    final int CHUNK_SIZE = 10240;
    Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    public SimpleServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("Client connected from " + conn.getRemoteSocketAddress().getAddress());

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

                conn.send(byteBuffer);

                // conn.sendFragmentedFrame(Opcode.BINARY, byteBuffer, isLastChunk);
            }
            logger.info("File sent");
        } catch (Exception e) {
            logger.error("Error at sending file");
            e.printStackTrace();
        }

        try {
            logger.info("Sending file name and user name");
            String fileName = file.getName();
            UserFile userFile = new UserFile("car", fileName);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userFile);
            conn.send(json);
            logger.info("File name and user name sent");
        } catch (Exception e) {
            logger.error("error at sending file name and user name");
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(
                "closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
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
        logger.info("Server started at ws://localhost:8887");
    }
}