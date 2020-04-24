package sound;

/**
 * A single midi note.
 * @author Jody Paul
 * @version 1.2
 */
public class Note {
    // Apple-OSX-Midi-instrument:int associations
    /** int value associated with PIANO. */
    public static final int PIANO       =   1;
    /** int value associated with ELECTPIANO. */
    public static final int ELECTPIANO  =   4;
    /** int value associated with HARPSICHORD. */
    public static final int HARPSICHORD =   6;
    /** int value associated with VIBES. */
    public static final int VIBES       =  11;
    /** int value associated with ORGAN. */
    public static final int ORGAN       =  19;
    /** int value associated with ACCORDIAN. */
    public static final int ACCORDIAN   =  23;
    /** int value associated with BANJO. */
    public static final int BANJO       =  25;
    /** int value associated with PIZZACATTO. */
    public static final int PIZZACATTO  =  45;
    /** int value associated with VIOLIN. */
    public static final int VIOLIN      =  52;
    /** int value associated with TRUMPET. */
    public static final int TRUMPET     =  56;
    /** int value associated with TROMBONE. */
    public static final int TROMBONE    =  57;
    /** int value associated with VIOLIN2. */
    public static final int VIOLIN2     =  59;
    /** int value associated with VIOLIN3. */
    public static final int VIOLIN3     =  65;
    /** int value associated with TENORSAX. */
    public static final int TENORSAX    =  66;
    /** int value associated with FLUTE. */
    public static final int FLUTE       =  73;
    /** int value associated with PANFLUTE. */
    public static final int PANFLUTE    =  75;
    /** int value associated with PIANO1. */
    public static final int PIANO1      =  80;
    /** int value associated with SYNVOICE. */
    public static final int SYNVOICE    =  85;
    /** int value associated with BASSDRUM. */
    public static final int BASSDRUM    = 116;
    /** int value associated with MELODICTOM. */
    public static final int MELODICTOM  = 117;
    /** int value associated with SNAREDRUM. */
    public static final  int SNAREDRUM   = 120;
    /** Midi Note Middle-C. */
    public static final int C  = 60;
    /** Midi Minimum Note Pitch. */
    public static final int LOW = 25;
    /** Midi Maximum Note Pitch. */
    public static final int HIGH = 120;
    /** Midi Note Pitch Range. */
    public static final int RANGE = HIGH - LOW;
    /** Whole-Note Duration (in milliseconds). */
    public static final int DW = 2400;
    /** Eighth-Note Duration (in milliseconds). */
    public static final int DE = DW / 8;
    /** Quarter-Note Duration. */
    public static final int DQ = DW / 4;
    /** Half-Note Duration. */
    public static final int DH = DW / 2;
    /** Midi Minimum Note-On Velocity - Pianissimo. */
    public static final int VPP = 25;
    /** Midi Maximum Note-On Velocity - Fortissimo. */
    public static final int VFF = 120;
    /** Midi Note-On Velocity Range. */
    public static final int VRANGE = VFF - VPP;
    /** Special Note that represents the Null Note. */
    public static final Note NULL_NOTE = new Note(-1, -1, -1, -1);

    // Instance Data

    /** Channel from 0 to 15. */
    private int channel;
    /** Pitch from 0 to 127; 60 = Middle C. */
    private int pitch;
    /** Pitch in milliseconds. */
    private int duration;
    /** 0 to 127 (Note-On Velocity). */
    private int velocityOn;

    /**
     * Construct a specific Note.
     * @param c channel
     * @param p pitch
     * @param d duration
     * @param v velocityOn
     */
    public Note(final int c, final int p, final int d, final int v) {
        channel = c;
        pitch = p;
        duration = d;
        velocityOn = v;
    }
    /**
     * Construct a specific Note.
     * @param c channel
     * @param p pitch
     * @param d duration
     * @param v velocityOn
     */
    public Note(final int c, final Integer p, final int d, final int v) {
        channel = c;
        pitch = p.intValue();
        duration = d;
        velocityOn = v;
    }

    /**
     * Access this note's channel.
     * @return the channel
     */
    public int channel() {
        return channel;
    }

    /**
     * Access this note's pitch.
     * @return the pitch
     */
    public int pitch() {
        return pitch;
    }

    /**
     * Access this note's duration.
     * @return the channel
     */
    public int duration() {
        return duration;
    }

    /**
     * Access this note's velocity.
     * @return the velocity
     */
    public int velocityOn() {
        return velocityOn;
    }

    /** Represents the channel, pitch,
     * duration and velocityOn in String format.
     * @return String representing the instance data
     */
    public String toString() {
        return "[Channel " + channel
        + "; Pitch " + pitch
        + "; Duration " + duration
        + "; velocityOn " + velocityOn + "]";
    }
}
