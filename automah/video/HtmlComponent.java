package automah.video;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class HtmlComponent {

	public final static int CAMERA_WIDTH=480;
	/*
public static void main(String[] args) {
    NativeInterface.open();
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            JFrame frame = new JFrame("YouTube Viewer");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(getBrowserPanel(), BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        }
    });
    NativeInterface.runEventPump();
    // don't forget to properly close native components
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
            NativeInterface.close();
        }
    }));
}
*/

public static JPanel getBrowserPanel() {
    JPanel webBrowserPanel = new JPanel(new BorderLayout());
    JWebBrowser webBrowser = new JWebBrowser();
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    webBrowser.setBarsVisible(false);
    webBrowser.navigate("http://192.168.43.38:8080/video");
    //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    //webBrowser.setPreferredSize(new Dimension((int) (dim.width*0.75), (int) (dim.height*0.75)));
    webBrowser.setPreferredSize(new Dimension((int) CAMERA_WIDTH, (int) 320));
    
    webBrowser.setBorder(BorderFactory.createLineBorder(Color.black));
	 
    return webBrowserPanel;
}
}