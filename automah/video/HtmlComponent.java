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