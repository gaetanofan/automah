package automah.video;
import java.net.*;
import java.util.concurrent.TimeUnit;


class UDPClient{
	static String ip = "192.168.1.177";
	static int port = 8888;
	
	public static void send(String var) throws Exception{
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(ip);
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = var;
		sendData = sentence.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
	}
	
	public static void main(String args[]) throws Exception{
		send("20");
		TimeUnit.SECONDS.sleep(5);
		send("40");
		TimeUnit.SECONDS.sleep(5);
		send("60");
	}
}
