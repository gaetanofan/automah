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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class UserPreferences extends Properties{
	private static final long serialVersionUID = 1L;
	private static final String CAMERA_IP = "camera_ip";
	private static final String SERVO_IP = "servo_ip";

	private String cam_ip = HtmlComponent.DEFAULT_CAMERA_IP;
	private String servo_ip = UDPClient.IP;

	private static final String FILE_NAME = "automah.properties";

	public String getCam_ip() {
		return cam_ip;
	}

	public String getServo_ip() {
		return servo_ip;
	}


	public static void read() {
		Properties data = new Properties();
		try (FileInputStream stream = new FileInputStream(new File(FILE_NAME))) {
			data.load(stream);
			System.out.println(data.getProperty(CAMERA_IP));
		} catch (IOException e){
		}
	}
	
	UserPreferences(){
		super();
		try (FileInputStream stream = new FileInputStream(new File(FILE_NAME))) {
			this.load(stream);
			cam_ip = this.getProperty(CAMERA_IP);
			servo_ip = this.getProperty(SERVO_IP);
		} catch (FileNotFoundException e) {
			create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new UserPreferences().getCam_ip());
	}

	public void create() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(FILE_NAME);
			this.setProperty(CAMERA_IP, cam_ip);
			this.setProperty(SERVO_IP, servo_ip);
			this.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
