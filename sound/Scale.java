package sound;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A musical scale.
 * Comprised of an ordered set of pitches (midi note numbers).
 *
 * @author Dr. Jody Paul
 * @version 1.2
 */
public class Scale {
    /** The pitches for this tune. */
    private List<Integer> scalePitches;

    /** Initial index for loop in pentatonic methods. */
    private static final int PENTATONIC_FIRST_INDEX = -3;

    /** Last index for loop in pentatonic methods. */
    private static final int PENTATONIC_LAST_INDEX = 4;

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

    /**
     * Construct an empty Scale.
     */
    public Scale() {
        scalePitches = new ArrayList<Integer>();
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param midiPitch the pitch to add
     */
    public void addPitch(final int midiPitch) {
        addPitch(Integer.valueOf(midiPitch));
    }

    /**
     *  Add a pitch to the end of this scale.
     *  @param midiPitch the pitch to add
     */
    public void addPitch(final Integer midiPitch) {
        scalePitches.add(midiPitch);
    }

    /**
     *  Access a specified pitch in this scale.
     *  The index used is the parameter modulo the number of pitches
     *  in the scale.
     *  @param whichPitch the index of the pitch to retrieve
     *  @return the pitch in the scale as specified by the parameter
     */
    public Integer getPitch(final int whichPitch) {
        return scalePitches.get(whichPitch % numPitches());
    }

    /**
     * Create an Iterator over the pitches in this scale.
     * @return iterator over pitches
     */
    public Iterator<Integer> iterator() {
        return scalePitches.iterator();
    }

    /**
     * Access the number of pitches in this scale.
     * @return the number of pitches
     */
    public int numPitches() {
        return scalePitches.size();
    }

    /**
     * Create a 7-octave Pentatonic scale.
     * @return a pentationic scale
     */
    public static Scale pentatonic1() {
        Scale s = new Scale();
        for (int i = PENTATONIC_FIRST_INDEX; i < PENTATONIC_LAST_INDEX; i++) {
            s.addPitch(Note.C  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_2) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_5) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_7) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_9) + (MIDDLE_C_MULTI_12 * i));
        }
        s.addPitch((Note.C + (MIDDLE_C_MULTI_12 * MIDDLE_C_ADD_4)));
        return s;
    }

    /**
     * Create another 7-octave Pentatonic scale.
     * @return a pentationic scale
     */
    public static Scale pentatonic2() {
        Scale s = new Scale();
        for (int i = PENTATONIC_FIRST_INDEX; i < PENTATONIC_LAST_INDEX; i++) {
            s.addPitch(Note.C  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_3)  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_5)  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_7)  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_10) + (MIDDLE_C_MULTI_12 * i));
        }
        s.addPitch((Note.C + (MIDDLE_C_MULTI_12 * MIDDLE_C_ADD_4)));
        return s;
    }

    /** Yet another 7-octave Pentatonic scale.
     * @return a pentationic scale
    */
    public static Scale pentatonic3() {
        Scale s = new Scale();
        for (int i = PENTATONIC_FIRST_INDEX; i < PENTATONIC_LAST_INDEX; i++) {
            s.addPitch(Note.C  + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_2) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_4) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_7) + (MIDDLE_C_MULTI_12 * i));
            s.addPitch((Note.C + MIDDLE_C_ADD_9) + (MIDDLE_C_MULTI_12 * i));
        }
        s.addPitch((Note.C + (MIDDLE_C_MULTI_12 * MIDDLE_C_ADD_4)));
        return s;
    }
}
