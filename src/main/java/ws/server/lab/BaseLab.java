package ws.server.lab;

import java.util.HashMap;

//q : kenapa perlu bikin parent class?
//a : reusability

public class BaseLab {
    HashMap<String, String> clients = new HashMap<>();

    BaseLab(int labId, int clientsCount){
        for (int i = 1; i <= clientsCount; i++) {
            StringBuilder ip = new StringBuilder("10.100.7"+labId+".2");
            StringBuilder hostname = new StringBuilder("LAB0"+labId+"-");
            if(i < 10){
                ip.append("0").append(i);
                hostname.append("0").append(i);
                String strIp = ip.toString();
                String strHostname = hostname.toString();

                clients.put(strIp, strHostname);
                continue;
            }
            ip.append(i);
            hostname.append(i);
            String strIp = ip.toString();
            String strHostname = hostname.toString();
            clients.put(strIp, strHostname);
        }
    }

    public String getHostnameByIP(String ip){
        return clients.get(ip);
    }
}
