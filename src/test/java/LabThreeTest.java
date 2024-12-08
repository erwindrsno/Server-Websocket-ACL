import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ws.server.model.lab.BaseLab;
import ws.server.model.lab.LabThree;

public class LabThreeTest {
    @Test
    public void testGetIp(){
        BaseLab l3 = new LabThree();
        String actual = l3.getHostnameByIP("10.100.73.215");
        assertEquals("LAB03-15",actual);
    }

    @Test
    public void testGetIpLessThan10(){
        BaseLab l3 = new LabThree();
        String actual = l3.getHostnameByIP("10.100.73.205");
        assertEquals("LAB03-05",actual);
    }

    @Test
    public void testGetFirstIp(){
        BaseLab l3 = new LabThree();
        String actual = l3.getHostnameByIP("10.100.73.201");
        assertEquals("LAB03-01",actual);
    }

    @Test
    public void testGetLastIp(){
        BaseLab l3 = new LabThree();
        String actual = l3.getHostnameByIP("10.100.73.235");
        assertEquals("LAB03-35",actual);
    }
}