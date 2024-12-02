import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.*;

import org.junit.jupiter.api.Test;

import com.google.common.hash.*;

public class FileTest {
    @Test
    public void hashingFile(){
        Path filePath = Paths.get("T06xxyyy.zip");
        
        try{
            byte[] fileBytes = Files.readAllBytes(filePath);
            
            String hashedBytes = Hashing.sha256().hashBytes(fileBytes).toString();
            System.out.println("Hashed is : " + hashedBytes);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}