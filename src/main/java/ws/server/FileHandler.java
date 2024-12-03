package ws.server;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import org.slf4j.*;

public class FileHandler {
    Server server;
    int CHUNK_SIZE = 10240;
    Logger logger = LoggerFactory.getLogger(FileHandler.class);

    public FileHandler(Server server){
        this.server = server;
    }

    public void sendFile(File file){
        if (!file.exists()) {
            logger.error("File not found");
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;

            // logger.info("Sending file by chunking (10240 bytes)");
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // logger.info("Sending 10240");
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);

                this.server.broadcast(byteBuffer);
            }
            logger.info("File sent");
        } catch (Exception e) {
            logger.error("Error at sending file");
            System.err.println(e);
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
            System.err.println(e);
        }
    }
}
