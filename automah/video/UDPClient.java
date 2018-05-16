/**
AUTOMAH - Home Automation Project
Copyright (C) 2018  Gaetano F. Anastasi

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**/

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
