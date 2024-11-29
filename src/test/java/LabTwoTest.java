import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ws.server.lab.LabTwo;
import ws.server.lab.BaseLab;

public class LabTwoTest {
    @Test
    public void testGetIp(){
        BaseLab l2 = new LabTwo();
        String actual = l2.getHostnameByIP("10.100.72.215");
        assertEquals("LAB02-15",actual);
    }

    @Test
    public void testGetIpLessThan10(){
        BaseLab l2 = new LabTwo();
        String actual = l2.getHostnameByIP("10.100.72.205");
        assertEquals("LAB02-05",actual);
    }
}