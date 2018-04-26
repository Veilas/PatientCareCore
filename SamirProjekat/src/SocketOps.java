package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SocketOps {
	
	private Socket senderSocket = null;
	private Socket receiverSocket = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	
	public SocketOps(String clientAddress) {
		try {
		    System.out.println(clientAddress);
			senderSocket = new Socket(clientAddress, 8000);
			dos = new DataOutputStream(senderSocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SocketOps(ServerSocket server) {
		try {
			receiverSocket = server.accept();
			receiverSocket.setSoTimeout(600000); //timeout na socketu je 10 minuta
			dis = new DataInputStream(receiverSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purgeSender() {
		try {
			senderSocket.close();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purgeReceiver() {
		try {
			receiverSocket.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBedIds(byte[] array) throws IOException {
	    dos.write(array);
	}
	
	public int receiveResponse() throws IOException, SocketTimeoutException {
	    byte[] b = new byte[4];
	    dis.read(b);
	    ByteBuffer bb = ByteBuffer.wrap(b);
		return bb.getInt();
	}

	public Socket getSenderSocket() {
		return senderSocket;
	}

	public void setSenderSocket(Socket senderSocket) {
		this.senderSocket = senderSocket;
	}

	public Socket getReceiverSocket() {
		return receiverSocket;
	}

	public void setReceiverSocket(Socket receiverSocket) {
		this.receiverSocket = receiverSocket;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}
	
}
