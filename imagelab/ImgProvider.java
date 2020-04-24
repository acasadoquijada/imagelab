package imagelab;

import sound.Chord;
import sound.Music;
import sound.Note;
import sound.Scale;
import sound.Tune;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;
import java.awt.image.BufferedImage;


/**
 * ImgProvider is responsible for managing a single image
 * (loading, filtration, rendering, etc.).
 * @author Dr. Aaron Gordon
 * @author Dr. Jody Paul
 * @version 1.1
 */
public class ImgProvider extends JComponent {
    /** Serialization version. */
    private static final long serialVersionUID = 11L;
    /** Boolean all. */
    private static boolean all;
    /** true if this ImgProvider currently holds an image; false otherwise. */
    private boolean         isLoaded;
    /** Image height in pixels. */
    private int             pixheight;
    /** Image width in pixels. */
    private int             pixwidth;
    /** The raw image. */
    private Image           img;
    /** Holders for the color and alpha components of the image. */
    private short[][]      red, green, blue, alpha;
    /** To retrieve pixels from the image. */
    private PixelGrabber    grab;
    /** Holder for the pixels from the image. */
    private int[]          pix;
    /** X-axis increment used for trimming the image. */
    private int   xinc = 0;
    /** Y-axis increment used for trimming the image. */
    private int   yinc = 0;
    /** Holder for the filename of the file that contains the image. */
    private String imgName;
    /** Used for assigning unique IDs to ImgProviders. Incremented when used. */
    private static int count = 0;
    /** Identification used to distinguish one ImgProvider from another. */
    private int id;
    /** ImageLab object. */
    private ImageLab lab;
    /** Sleep period 1000 ms. */
    private static final int SLEEP_1000_MS = 1000;
    /** Sleep period 300 ms. */
    private static final int SLEEP_300_MS = 300;
    /** Sleep period 100 ms. */
    private static final int SLEEP_100_MS = 100;
    /** Value used in the pitch settings. */
    private static final int PITCH_DIV = 256;
    /** Value used to create a Music object. */
    private static final int MUSIC_CHANNEL_NUMBER = 3;

    /** Add 2 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_2 = 2;
    /** Add 3 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_3 = 3;
    /** Add 4 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_4 = 4;
    /** Add 5 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_5 = 5;
    /** Add 7 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_7 = 7;
    /** Add 9 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_9 = 9;
    /** Add 10 to the midi note middle c. */
    private static final int MIDDLE_C_ADD_10 = 10;
    /** Multiply 12 to the midi note middle c. */
    private static final int MIDDLE_C_MULTI_12 = 12;

    /** Initial index for loop in pentatonic methods. */
    private static final int PENTATONIC_FIRST_INDEX = -3;

    /** Last index for loop in pentatonic methods. */
    private static final int PENTATONIC_LAST_INDEX = 4;

    /** Alpha value. */
    private static final int ALPHA = 255;

    /** Value used in operators >> and <<. */
    private static final int SHIFT_8 = 8;

    /** Value used to divide the sum of red+green+blue. */
    private static final int DIVIDE_COLOR = 3;
    /** No-argument constructor.  Sets name to empty string. */
    public ImgProvider() {
        this("");
    } // constructor

    /**
     * Constructor that accepts a filename.
     * @param name The name of the file containing the image
     */
    public ImgProvider(final String name) {
        imgName = name;
        isLoaded = false;
        id = ++count;
    } // constructor

    /** Returns the pixels of the image.
     * @return pix
    */
    public int[] getPix() {
        return pix;
    }

    /** Returns the pix height of the image.
     * @return pix height of the image
    */
    public int getPixHeight() {
        return pixheight;
    }

    /** Returns the pix width of the image.
     * @return pix width of the image
    */
    public int getPixWidth() {
        return pixwidth;
    }

    /**
     * Retrieve this ImgProvider's unique id.
     * For ImageLab's internal use.
     * @return ImgProvider's unique id
     */
    public int getid() {
        return id;
    } //getid

    /**
     * Create a B&W image object based on the parameter.
     * Uses the instance variable pix as destination.
     * @param localImg 2D array of black-and-white pixel values (0-255)
     */
    public void setBWImage(final short[][] localImg) {
        int spot = 0;  //index into pix
        int tmp;
        int localAlpha = ALPHA;
        pixheight = localImg.length;
        pixwidth  = localImg[0].length;
        pix = new int[pixheight * pixwidth];
        for (int row = 0; row < pixheight; row++) {
            for (int col = 0; col < pixwidth; col++) {
                tmp = localAlpha;
                tmp = tmp << SHIFT_8;
                tmp += localImg[row][col];
                tmp = tmp << SHIFT_8;
                tmp += localImg[row][col];
                tmp = tmp << SHIFT_8;
                tmp += localImg[row][col];
                pix[spot++] = tmp;
            } //for col
        } //for row
        separateColors();
        isLoaded = true;
    } //setBWImage

    /**
     * Return the image in black and white.
     * @return 2D array of pixel grey-values (0 to 255)
     */
    public short[][] getBWImage() {
        //read in image into pix[]
        if (!isLoaded) {
            readinImage();
        }
        toBW();         //convert it to black and white

        //copy from int []pix to short [][]b and filter outliers
        short[][] b = new short[pixheight][pixwidth];
        int spot = 0;
        short tmp;
        for (int r = 0; r < pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                tmp = (short) (pix[spot++] & ALPHA);
                b[r][c] = tmp;
            } //for c
        } //for r
        //showImage(b,"B & W with compressed range of values");
        return b;
    } //getBWImage

    /** Read in the image. */
    public void readinImage() {
        img = getToolkit().getImage(imgName);
        if (img == null) {
             System.err.println(
                 "\n\n**ImgProvider: getImage: img is null!!! ***\n\n");
        }
        int width   = img.getWidth(null)  - xinc;
        int height  = img.getHeight(null) - yinc;
        //forceRGB=true
        grab        = new PixelGrabber(img, xinc, yinc, width, height, true);
        try {
            grab.grabPixels();
        } catch (Exception e) {
            System.err.println(
                "ImgProvider:getBWImage: pixel grabbing failed!!");
            return;
            //System.exit(-1);
        }
        pix = (int[]) grab.getPixels();
        pixwidth = img.getWidth(null) - xinc;
        pixheight = img.getHeight(null) - yinc;
        isLoaded = true;
        separateColors();
        //System.out.println(
        //"pix width and height are: " + pixwidth + ",  " + pixheight);
        if (all) {
            showPix("Original in Color");      //display original picture
        }
        try {
            Thread.sleep(SLEEP_300_MS);
        } catch (Exception e) { }
    } //readinImage

    /**
     * Cut out x columns and y rows from the NW corner of the image.
     * @param x the number of columns to remove
     * @param y the number of rows to remove
     */
    public void setTrim(final int x, final int y) {
        xinc = x;
        yinc = y;
    } //setTrim

    /** Convert from color to gray scale (black and white). */
    private void toBW() {
        int  localAlpha, localRed, localGreen, localBlue, black;

        for (int i = 0; i < pix.length; i++) {
            int num = pix[i];
            localBlue = num & ALPHA;
            num = num >> SHIFT_8;
            localGreen = num & ALPHA;
            num = num >> SHIFT_8;
            localRed = num & ALPHA;
            num = num >> SHIFT_8;
            localAlpha = num & ALPHA;
            black = (localRed + localGreen + localBlue) / DIVIDE_COLOR;
            num = localAlpha;
            num = (num << SHIFT_8) + black;
            num = (num << SHIFT_8) + black;
            num = (num << SHIFT_8) + black;
            pix[i] = num;
        }
        if (all) {
            showPix("Black and White");
        }
        try {
            Thread.sleep(SLEEP_300_MS);
        } catch (Exception e) { }
    } //toBW

    /** Alias for showPix. (Syntactic sugar)
     * @param name String alias for showPix
     */
    public void showImage(final String name) {
        showPix(name);
    } //showImage

    /**
     * Display this image in a window.
     * @param name The title for the window.
     */
    public void showPix(final String name) {
        //System.out.println("ImgProvider:showPix:  before readIn");
        if (!isLoaded) {
            readinImage();
        }
        //System.out.println("ImgProvider:showPix:  after readIn");
        img = getToolkit().createImage(
                new MemoryImageSource(pixwidth, pixheight, pix, 0, pixwidth));
        //System.out.println("ImgProvider:showPix:  before displayImage");
        DisplayImage dis = new DisplayImage(this, name, true);
        //System.out.println("ImgProvider:showPix:  after displayImage");
        try {
            Thread.sleep(SLEEP_100_MS);
        } catch (Exception e) { } //make sure image has time to display
    } //showPix

    /**
     * Pull the image apart into its RGB and Alpha components.
     */
    void separateColors() {
        if (pix == null) {
            return;
        }
        alpha   = new short[pixheight][pixwidth];
        red     = new short[pixheight][pixwidth];
        green   = new short[pixheight][pixwidth];
        blue    = new short[pixheight][pixwidth];
        int spot = 0;       //index into pix
        for (int r = 0; r < pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                int num = pix[spot++];
                blue[r][c]   = (short) (num & ALPHA);
                num = num >> SHIFT_8;
                green[r][c]  = (short) (num & ALPHA);
                num = num >> SHIFT_8;
                red[r][c]    = (short) (num & ALPHA);
                num = num >> SHIFT_8;
                alpha[r][c] = (short) (num & ALPHA);
            } //for c
        } //for r
    } //separateColors

    /**
     * Set the RGB and Alpha components for this image.
     * @param rd 2D array that represents the image's red component
     * @param g  2D array that represents the image's green component
     * @param b  2D array that represents the image's blue component
     * @param al 2D array that represents the image's alpha channel
     */
    public void setColors(
        final short[][]rd,
        final short[][]g,
        final short[][]b,
        final short[][]al) {

        pixheight = rd.length;
        pixwidth  = rd[0].length;
        red     = new short[pixheight][pixwidth];
        green   = new short[pixheight][pixwidth];
        blue    = new short[pixheight][pixwidth];
        alpha   = new short[pixheight][pixwidth];
        pix     = new int[pixwidth * pixheight];
        int tmp;
        int spot = 0;
        for (int r = 0; r < pixheight; r++) {
            for (int c = 0; c < pixwidth; c++) {
                red[r][c]   = rd[r][c];
                green[r][c] = g[r][c];
                blue[r][c]  = b[r][c];
                alpha[r][c] = al[r][c];
                tmp = alpha[r][c];
                tmp = tmp << SHIFT_8;
                tmp += red[r][c];
                tmp = tmp << SHIFT_8;
                tmp += green[r][c];
                tmp = tmp << SHIFT_8;
                tmp += blue[r][c];
                pix[spot++] = tmp;
            } //for c
        } //for r
        isLoaded = true;
    } //setColors

    /**
     * Retrieve the image's green component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getRed() {
        int nrows = red.length;
        int ncols = red[0].length;
        short[][] redcp = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                redcp[r][c] = red[r][c];
            }
        }
        return redcp;
    } //getRed

    /**
     * Retrieve the image's green component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getGreen() {
        int nrows = green.length;
        int ncols = green[0].length;
        short[][] thecopy = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                thecopy[r][c] = green[r][c];
            }
        }
        return thecopy;
    } //getGreen

    /**
     * Retrieve the image's blue component.
     * @return A 2D array of values from 0 to 255.
     */
    public short[][] getBlue() {
        int nrows = blue.length;
        int ncols = blue[0].length;
        short[][] thecopy = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                thecopy[r][c] = blue[r][c];
            }
        }
        return thecopy;
    } //getBlue

    /**
     * Retrieve the image's alpha component.
     * @return A 2D array with values from 0 to 255.
     */
    public short[][] getAlpha() {
        int nrows = alpha.length;
        int ncols = alpha[0].length;
        short[][] al = new short[nrows][ncols];
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                al[r][c] = alpha[r][c];
            }
        }
        return al;
    }

    /**
     * retrieve the image's width.
     * @return image's width
     */
    public int getWidth() {
        return pixwidth;
     }

    /**
     * Retrieve the image's height.
     * @return image's height
     */
    public int getHeight() {
        return pixheight;
     }

    /**
     * Retrieve the image's raw image.
     * Note that img is not always consistent with RGBA arrays.
     * @return The image's raw image
     */
    public Image getImage() {
        return img;
    } //getImage

    /**
     * Called when the window containing this image is selected.
     */
    public void setActive() {
        if (lab != null) {
            lab.setActive(this);
        } else {
            System.err.println("*** error ** ImgProvider:setActive - no lab");
        }
    }

    /** Called when the window containing this image is closed. */
    public void setInactive() {
        if (lab != null) {
            lab.setInactive(this);
            ImageLab.setImgProvider(null);
        } else {
            System.err.println("*** error ** ImgProvider:setInactive - no lab");
        }
    } //setInactive

    /**
     * Used by ImageLab to register itself with this ImgProvider.
     * @param iml ImageLab to set
     */
    void setLab(final ImageLab iml) {
        lab = iml;
    }

    /** Used by ImageLab to save an image to a file. */
    void save() {
        JFileChooser fd;
        JFrame myframe = new JFrame();      //to have a parent
        fd = new JFileChooser();
        int returnVal = fd.showSaveDialog(myframe);
        String fname;
        File  theFile;
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(
                myframe, "Encountered a problem in ImgProvider.save()"
                 + "\n- Please try again.");
            return;
        }
        fname = fd.getSelectedFile().getName();
        theFile = fd.getSelectedFile();

        BufferedImage bufim = new BufferedImage(
            pixwidth, pixheight, BufferedImage.TYPE_INT_RGB);
        bufim.setRGB(0, 0, pixwidth, pixheight, pix, 0, pixwidth);

        try {
            if (!ImageIO.write(bufim, "jpeg", theFile)) {
                System.err.println("Couldn't write file - save failed");
            }
            //else System.out.println("File written");
        } catch (IOException ioe) {
            System.err.println("Attempt to save file failed.");
        } //catch
    } //save

    /**
     * Used by ImageLab to render an image as sound.
     * Three channels of sound are created, using the
     * average the Red, Green and Blue values of each
     * row to establish the pitches for the channels.
     * The average of Hue, Saturation and Brightness
     * values of each row are used as the velocities
     * of the Red, Green and Blue notes respectively.
     */
    public void play() {
        short[][] localRed = getRed();     // Red plane
        short[][] localGreen = getGreen(); // Green plane
        short[][] localBlue = getBlue();   // Blue plane
        short[][] bw = getBWImage();  // Black & white image
        short[][] localAlpha = getAlpha(); // Alpha channel
        short[][] hue;
        short[][] saturation;
        short[][] brightness;

        int height = bw.length;
        int width  = bw[0].length;

        //System.out.println("Playing image number " + getid());

        Tune tune = new Tune();
        /* A 7-octave pentatonic scale. */
        Scale scale = new Scale();
        for (int i = PENTATONIC_FIRST_INDEX; i < PENTATONIC_LAST_INDEX; i++) {
            scale.addPitch(Note.C + (MIDDLE_C_MULTI_12 * i));
            scale.addPitch((Note.C + MIDDLE_C_ADD_3)
            + (MIDDLE_C_MULTI_12 * i));
            scale.addPitch((Note.C + MIDDLE_C_ADD_5)
            + (MIDDLE_C_MULTI_12 * i));
            scale.addPitch((Note.C + MIDDLE_C_ADD_7)
            + (MIDDLE_C_MULTI_12 * i));
            scale.addPitch((Note.C + MIDDLE_C_ADD_10)
             + (MIDDLE_C_MULTI_12 * i));
        }
        int pitchRange = scale.numPitches();
        Chord chord;
        int[] velocity = {0, 0, 0};
        int velocityRange = Note.VRANGE;
        int tempo = Note.DE / 2;
        int rowSum = 0;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        float[] hsb = {0, 0, 0};
        float hueSum = 0;
        float satSum = 0;
        float brtSum = 0;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                rowSum += (bw[row][column]);
                redSum += (localRed[row][column]);
                greenSum += (localGreen[row][column]);
                blueSum += (localBlue[row][column]);
                java.awt.Color.RGBtoHSB(
                    localRed[row][column],
                    localGreen[row][column],
                    localBlue[row][column], hsb);
                hueSum += hsb[0];
                satSum += hsb[1];
                brtSum += hsb[2];
            } //for column
            velocity[0] = (int) (Note.VPP
            + (velocityRange * (hueSum / width)));
            velocity[1] = (int) (Note.VPP
            + (velocityRange * (satSum / width)));
            velocity[2] = (int) (Note.VPP
            + (velocityRange * (brtSum / width)));
            chord = new Chord();
            chord.addNote(
                new Note(0, (
                    scale.getPitch(
                        pitchRange * redSum / width / PITCH_DIV)),
                        tempo, velocity[0]));
            chord.addNote(
                new Note(1, (
                    scale.getPitch(
                        pitchRange * greenSum / width / PITCH_DIV)),
                        tempo, velocity[1]));
            chord.addNote(new Note(2,
             (scale.getPitch(
                 pitchRange *  blueSum / width / PITCH_DIV)), tempo,
                velocity[2]));
            tune.addChord(chord);
            rowSum = 0;
            redSum = 0;
            greenSum = 0;
            blueSum = 0;
            hueSum = 0;
            satSum = 0;
            brtSum = 0;
        } //for row
        int[] instruments = {Note.VIBES, Note.PIZZACATTO, Note.MELODICTOM};
        Music m = new Music(MUSIC_CHANNEL_NUMBER, instruments);
        m.playTune(tune);
    }

    /**
     * Display this image a line at a time in a window.
     * @param name The title for the window.
     */
    public void showPixNew(final String name) {
        System.out.println("ImgProvider:showSlow: Before readinImage");
        if (!isLoaded) {
            readinImage();
        }
        System.out.println("ImgProvider:showSlow: After readinImage");
        img = getToolkit().createImage(
                new MemoryImageSource(pixwidth, pixheight, pix, 0, pixwidth));
        DynDisplayImage dImage1 = new DynDisplayImage(this, name, true);
        dImage1.setVisible(true);
        dImage1.repaint();
        System.out.println("ImgProvider:showSlow: Constructed DynaPanel");
        System.out.println("ImgProvider:showSlow: size is ("
        + pixwidth + ", " + pixheight + ")");
        try {
            Thread.sleep(SLEEP_1000_MS);  //give image time to display
        } catch (Exception e) { }
        dImage1.changeImage(this, "Second Pass");
        System.out.println("ImgProvider:showSlow: Second Pass");
    }

}
