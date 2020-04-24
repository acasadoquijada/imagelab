import imagelab.ImageLab;
/**
 * ImageLab is a framework for student exploration of image processing.
 * copyright (C) 2016,2019 by Aaron Gordon & Jody Paul
 * This program comes with ABSOLUTELY NO WARRANTY.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see https://www.gnu.org/licenses/
 */
/**
 * Run the ImageLab application.
 * @author Aaron Gordon
 * @author Jody Paul
 * @version 1.8.2
 */
public final class Run {
     /**
     * Private constructor to avoid the "Utility classes should not have a
     * public or default constructor." warning as indicated in this
     * stackoverflow post:
     * https://stackoverflow.com/questions/7766277/why-am-i-getting-this
     * -warning-about-utility-classes-in-java/7768378#7768378.
     */
    private Run() { }
    /**
     * Main method that runs the ImageLab application.
     * @param args arguments for the ImageLab application
     */
    public static void main(final String[] args) {
        ImageLab.main(args);
    }
}
