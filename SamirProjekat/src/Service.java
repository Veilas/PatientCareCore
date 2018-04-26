package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Service {
    
    private ArrayList<ServiceData> data = new ArrayList<ServiceData>();
    private ArrayList<ServiceData> oldDataArray = new ArrayList<ServiceData>();
    private ServiceData oldData = new ServiceData();
    private ServerSocket server = null;
    private SocketOps socketOps = null;
    private String clientAddress = null;
    
    public void run() {
        
        try {
            server = new ServerSocket(5000);
            Socket init = server.accept();
            clientAddress = init.getRemoteSocketAddress().toString().substring(1, 14);
            System.out.println("Remote ip address: " + clientAddress);
            init.close();
            System.out.println("Initial connection established...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while(true) {
            DBOps ops = new DBOps();
            Connection connection = ops.connect();
            System.out.println("\nKonektovan sam na bazu...");
            if(ops.checkLastData(connection, oldData)) {
                System.out.println("Ima podataka...");
                
                data = ops.loadData(connection);
                oldDataArray = data;
                System.out.println("Podaci su ucitani...");
                
                socketOps = new SocketOps(clientAddress);
                if(socketOps.getSenderSocket().isConnected()) {
                    try {
			            socketOps.sendBedIds(Utilities.getByteArray(data));
		            } catch (IOException e) {
			            e.printStackTrace();
			            break;
		            }
                    socketOps.purgeSender();
                }
                else continue;
                
                new Thread(new ConformationReceiver(server, data, oldDataArray)).start();
            } else System.out.println("Nema nista novo u bazi...");
            ops.disconnect(connection);
            
            try {
               Thread.sleep(10000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            
            if(!data.isEmpty()) data.clear();
        }
    } 
}
