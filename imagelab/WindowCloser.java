package imagelab;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/**
 * Used to close an active window.
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 */
public class WindowCloser extends WindowAdapter {

    /** The image frame to be killed. */
    private ILFrame theFrame;

    /** Constructor that sets the image frame to be killed.
     * @param f image frame
     */
    public WindowCloser(final ILFrame f) {
        theFrame = f;
    }

    /** Closes the image frame and shows bye message.
     * @param e WindowEvent
     */
    public void windowClosing(final WindowEvent e) {
        theFrame.setVisible(false);
        theFrame.byebye();
    }

    /** Activates the image frame.
     * @param e WindowEvent
     */
    public void windowActivated(final WindowEvent e) {
        //System.out.println("WindowCloser:windowActive");
        theFrame.setActive();
    }

}
