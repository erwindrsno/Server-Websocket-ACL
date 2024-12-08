package ws.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Set;
import java.nio.file.attribute.AclEntryPermission;

import org.slf4j.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;

import ws.server.model.Acl;
import ws.server.model.metadata.PostMetadata;
import ws.server.model.metadata.PreMetadata;

public class FileHandler {
    Server server;
    int CHUNK_SIZE = 10240;
    Logger logger = LoggerFactory.getLogger(FileHandler.class);
    File file;

    Acl aclFetch = new Acl();

    public FileHandler(Server server, File file){
        this.server = server;
        this.file = file;
    }

    public void startSend(){
        if (!this.file.exists()) {
            logger.error("File not found");
            return;
        }
        logger.info("SENDING FILE");

        try (FileInputStream fileInputStream = new FileInputStream(this.file)) {
            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
                this.server.broadcast(byteBuffer);
            }
            logger.info("File sent");
        } catch (Exception e) {
            logger.error("Error at sending file");
            System.err.println(e);
        }
    }

    public void sendPreMetadata(){
        try{
            PreMetadata pmd = new PreMetadata(this.file.length(), this.CHUNK_SIZE);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pmd);
            this.server.broadcast("PRE-METADATA~"+json);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendPostMetadata(){
        logger.info("SENDING POST META DATA");
        try{
            Path filePath = Paths.get(file.getName());

            //hashing
            String hashed = Hashing.sha256().hashBytes(Files.readAllBytes(filePath)).toString();

            //generate ACL
            Set<AclEntryPermission> aclEntry = aclFetch.getRWXAcl();

            logger.info(aclEntry.toString());

            PostMetadata postMetadata = new PostMetadata(this.file.getName(), "car", hashed, aclEntry);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(postMetadata);
            this.server.broadcast("POST-METADATA~"+json);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}