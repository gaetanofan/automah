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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;


public class SliderGui extends JPanel
                        implements ActionListener,
                                   WindowListener,
                                   ChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int MIN_VAL = 0;
    static final int MAX_VAL = 180;
    static final int INITAl_VAL = (MAX_VAL - MIN_VAL)/2 ;   
    
    int frameNumber = 0;
    int NUM_FRAMES = 14;
    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
    int delay;
    boolean frozen = false;

    JLabel picture;

    public SliderGui() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        delay = 1000 / INITAl_VAL;

        //Create the label.
        JLabel sliderLabel = new JLabel("Ruota la fotocamera", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Create the slider.
        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,MIN_VAL, MAX_VAL, INITAl_VAL);
        

        framesPerSecond.addChangeListener(this);

        //Turn on labels at major tick marks.
        framesPerSecond.setMajorTickSpacing(30);
        framesPerSecond.setMinorTickSpacing(5);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);

        //Create the label that displays the animation.
        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
        updatePicture(0); 
        
        //Put everything together.
        add(sliderLabel);
        add(framesPerSecond);
        add(picture);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    }

    /** Add a listener for window events. */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    public void windowIconified(WindowEvent e) {
        stopAnimation();
    }
    public void windowDeiconified(WindowEvent e) {
        startAnimation();
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    /** Listen to the slider. */
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            updatePicture(fps);
            try {
				UDPClient.send(""+fps);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }

    public void startAnimation() {
        //Start (or restart) animating!
        frozen = false;
    }

    public void stopAnimation() {
        //Stop the animating thread.
        frozen = true;
    }

    //Called when the Timer fires.
    public void actionPerformed(ActionEvent e) {
        //Advance the animation frame.
        if (frameNumber == (NUM_FRAMES - 1)) {
            frameNumber = 0;
        } else {
            frameNumber++;
        }

        updatePicture(frameNumber); //display the next picture

        if ( frameNumber==(NUM_FRAMES - 1)
          || frameNumber==(NUM_FRAMES/2 - 1) ) {
         
        }
    }

    /** Update the label to display the image for the current frame. */
    protected void updatePicture(int frameNum) {
        
        //Set the image.
        if (images[frameNumber] != null) {
            picture.setIcon(images[frameNumber]);
        } else { 
            picture.setText("" + frameNum);
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SliderGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            // System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    	// get 2/3 of the height, and 2/3 of the width
    	int height = screenSize.height * 2 / 3;
    	int width = HtmlComponent.CAMERA_WIDTH + 10;

    	JFrame frame = new JFrame("Demo VideoSorveglianza");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SliderGui animator = new SliderGui();
                
        //Add content to the window.
        frame.add(HtmlComponent.getBrowserPanel(), BorderLayout.NORTH);

        frame.add(animator, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        NativeInterface.open();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
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
}

