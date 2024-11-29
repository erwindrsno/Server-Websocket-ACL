package ws.server.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//q : kenapa perlu bikin parent class?
//a : reusability

public class BaseLab {
    // List<Client> clients = new ArrayList<>();
    HashMap<String, Client> clients = new HashMap<>();
    int clientsCounts;

    BaseLab(int labId, int clientsCount){
        this.clientsCounts = clientsCount;
        for (int i = 1; i <= clientsCount; i++) {
            StringBuilder ip = new StringBuilder("10.100.7"+labId+".2");
            StringBuilder hostname = new StringBuilder("LAB0"+labId+"-");
            if(i < 10){
                ip.append("0").append(i);
                hostname.append("0").append(i);
            } else {
                ip.append(i);
                hostname.append(i);
            }
            String strIp = ip.toString();
            String strHostname = hostname.toString();
            clients.put(strIp, new Client(strHostname));
        }
    }

    public int getClientsCounts(){
        return clientsCounts;
    }

    public String getHostnameByIP(String ip){
        return clients.get(ip).getHostname();
    }

    public boolean getStatusByIP(String ip){
        return clients.get(ip).getStatus();
    }

    public void setActiveByIP(String ip){
        clients.get(ip).setActive();
    }

    class Client{
        // private String ip;
        private String hostname;
        private boolean isActive;

        public Client(String hostname){
            this.hostname = hostname;
            this.isActive = false;
        }

        public String getHostname(){
            return this.hostname;
        }

        public void setHostname(String hostname){
            this.hostname = hostname;
        }

        public boolean getStatus(){
            return this.isActive;
        }

        public void setActive(){
            this.isActive = true;
        }

        public void setNotActive(){
            this.isActive = false;
        }
    }
}
