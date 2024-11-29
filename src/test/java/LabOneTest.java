import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ws.server.lab.*;

public class LabOneTest {
    @Test
    public void testGetIp(){
        BaseLab l1 = new LabOne();
        String actual = l1.getHostnameByIP("10.100.71.222");
        assertEquals("LAB01-22",actual);
    }

    @Test
    public void testGetIpLessThan10(){
        BaseLab l1 = new LabOne();
        String actual = l1.getHostnameByIP("10.100.71.208");
        assertEquals("LAB01-08",actual);
    }
}