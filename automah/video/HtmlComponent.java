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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class HtmlComponent {

	public final static String DEFAULT_CAMERA_IP = "192.168.43.38";
	public final static int CAMERA_WIDTH = 480;

	public static JPanel getBrowserPanel() {
		String camera_ip = new UserPreferences().getCam_ip();
		JPanel webBrowserPanel = new JPanel(new BorderLayout());
		JWebBrowser webBrowser = new JWebBrowser();
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		webBrowser.setBarsVisible(false);
		webBrowser.navigate("http://"+ camera_ip + ":8080/video");
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//webBrowser.setPreferredSize(new Dimension((int) (dim.width*0.75), (int) (dim.height*0.75)));
		webBrowser.setPreferredSize(new Dimension((int) CAMERA_WIDTH, (int) 320));

		webBrowser.setBorder(BorderFactory.createLineBorder(Color.black));

		return webBrowserPanel;
	}
}