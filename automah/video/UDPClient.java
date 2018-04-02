package automah.video;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;


class UDPClient{
	final static String IP = "192.168.1.177";
	final static int PORT = 8888;
	
	public static void send(String var) throws IOException {
		UserPreferences pref = new UserPreferences();
		String ip = pref.getCam_ip();
		
		System.out.println("Connecting to " + ip + "... \n");
		
		InetAddress IPAddress = InetAddress.getByName(ip);
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket(PORT, IPAddress);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String sentence = var;
			sendData = sentence.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + modifiedSentence);
			
		} catch (SocketException e) {
			System.out.println("Impossibile connettersi a " + IPAddress);
			// e.printStackTrace();
		}
		finally {
			if (clientSocket != null) 
				clientSocket.close();
		}
	}
	
	public static void main(String args[]) throws Exception{
		send("20");
		TimeUnit.SECONDS.sleep(5);
		send("40");
		TimeUnit.SECONDS.sleep(5);
		send("60");
	}
}
