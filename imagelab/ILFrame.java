package imagelab;

import java.awt.Color;
import javax.swing.JFrame;

/**
 * Graphics frame used to display an image.
 * @author Dr. Jody Paul
 * @version 1.1
 */
public abstract class ILFrame extends JFrame {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;
    /** Image provider that activated this frame. */
    private ImgProvider improvider;
    /** Vertical space needed for frame title. */
    public static final int TITLE_HEIGHT = 40;
    /** Vertical space needed for menu bar. */
    public static final int MENUBAR_HEIGHT = 20;
    /** Vertical space needed for title & menu bar. */
    public static final int EXTRA_HEIGHT = TITLE_HEIGHT + MENUBAR_HEIGHT;
    /** Default background color. */
    public static final Color FRAME_BG_COLOR = new Color(255, 0, 0, 0);

    /** Notification that this window has become active. */
    public void setActive() {
        improvider.setActive();
    }

    /** Called when this window is closed. */
    public void byebye() {
        improvider.setInactive();
    }

    /** Set the ImgProvider object.
     * @param imgProvider ImgProvider object
     */
    public void setImgProvider(final ImgProvider imgProvider) {
        improvider = imgProvider;
    }
}
