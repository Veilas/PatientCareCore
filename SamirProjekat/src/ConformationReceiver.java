package src;

import java.net.ServerSocket;
import java.sql.Connection;
import java.util.ArrayList;
import java.io.*;
import java.net.SocketTimeoutException;

public class ConformationReceiver implements Runnable {
    private ServerSocket server = null;
    private ArrayList<ServiceData> data = new ArrayList<ServiceData>();
    private ArrayList<ServiceData> oldData = new ArrayList<ServiceData>();
    private MailServer mailServer = null;
    private DBOps dbOps = null;
    
    public ConformationReceiver(ServerSocket s, ArrayList<ServiceData> d, ArrayList<ServiceData> oD) {
        server = s;
        data = d;
        oldData = oD;
        mailServer = new MailServer();
        dbOps = new DBOps();
    }
    
    @Override
    public void run() { 
        Connection connection = dbOps.connect();
        while(!oldData.isEmpty()) {
            System.out.println("Cekam primanje potvrde za neki krevet...");
            SocketOps socketOps = new SocketOps(server);
            
            int bedNum = -1;
            try {
                bedNum = socketOps.receiveResponse();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            } 
            
            System.out.println("Primio sam potvrdu za neki krevet...");
            if(bedNum > 0 && bedNum < 27) {
                System.out.println("Krevet je broj: " + bedNum + ", a data.size(): " + data.size());   
               
                ArrayList<ServiceData> temp = new ArrayList<ServiceData>();
                for(int i = 0; i < data.size(); i++) {
                    if(Integer.valueOf(data.get(i).getBed_id()).equals(bedNum)) {
                        System.out.println(i + " " + data.size() + " " + data.get(i).getRequest_time());
                        temp.add(data.get(i));
                        data.remove(i);
                        data.trimToSize();
                    }
                }
                
                dbOps.confirm(connection, temp);
                for(int i = 0; i < temp.size(); i++) {          
                    new Thread(new MailSender(String.valueOf(bedNum), temp.get(i).getRequest_time(), 
                    String.valueOf(temp.get(i).getService_time()))).start();
                }
            }
            socketOps.purgeReceiver();
        }
        dbOps.disconnect(connection);
        System.out.println("Zavrsen thread...");
    }

}
