package net.andersand.andventure;

import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Table;
import net.andersand.andventure.model.elements.Wall;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author asn
 */
public class Util {
    
    public static String readFile(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }

    /**
     * @param partialFileName element image partial filename (without suffix)
     */
    public static Image loadElementImage(String partialFileName) {
        return loadImage("elements/" + partialFileName + Const.ELEMENT_IMG_SUFFIX);
    }

    /**
     * @param fileName image filename (including suffix)
     */
    public static Image loadImage(String fileName) {
        try {
            return new Image(Const.IMG_DIR + fileName);
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String randomizeEquipment() {
        char[] baseEquipment = "ash".toCharArray();
        byte letters2remove = (byte)(Math.random()*4); // 0-3
        for (byte i=0; i<letters2remove; i++) {
            byte index2remove = (byte)(Math.random()*3); // 0-2
            baseEquipment[index2remove] = '?';
        }
        String equipment = "";
        for (char c : baseEquipment) {
            if (c != '?') equipment += c;
        }
        return equipment;
    }

    public static int getElementPixelX(Position position) {
        return position.getX() * Const.ELEMENT_SIZE_PIXELS;
    }

    public static int getElementPixelY(Position position) {
        return position.getY() * Const.ELEMENT_SIZE_PIXELS;
    }

    public static int getTilePixelX(Position position) {
        return position.getX() * Const.BGTILE_SIZE_PIXELS;
    }

    public static int getTilePixelY(Position position) {
        return position.getY() * Const.BGTILE_SIZE_PIXELS;
    }

    public static List<String> divideIntoLines(String text, int lineMaxLength) {
        List<String> lines = new ArrayList<String>();
        List<String> words = getWords(text);
        String line = "";
        Iterator<String> it = words.iterator();
        String nextWord;
        while (it.hasNext()) {
            nextWord = it.next();
            if (line.length() + nextWord.length() < lineMaxLength) {
                line += nextWord + " ";
            }
            else {
                lines.add(line);
                line = nextWord + " ";
            }
        }
        lines.add(line);
        return lines;
    }

    /**
     * Generates a random integer between 0 and upperLimit-1.
     * For example if passed upperLimit 5, the method returns minimum 0 and maximum 4.
     */
    public static int random(int upperLimit) {
        return (int) (Math.random() * upperLimit);
    }

    public static Font loadFont(String fontName) {
        try {
            return new AngelCodeFont(Const.FONT_DIR + fontName + ".fnt", Const.FONT_DIR + fontName + "_0.tga");
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bounds getAdjustedBounds(Bounds bounds) {
        if (bounds.width < Const.PAPER_WIDTH + Const.WINDOW_SIDE_EXTRA_WIDTH) {
            bounds.width = Const.PAPER_WIDTH + Const.WINDOW_SIDE_EXTRA_WIDTH;
        }
        if (bounds.height < Const.PAPER_HEIGHT) {
            bounds.height = Const.PAPER_HEIGHT;
        }
        return bounds;
    }

    public static String randomAlphaNumeric() {
        int diceRoll = Util.random(10);
        if (diceRoll == 0) {
            //digit
            int low = 48;
            int rnd = Util.random(10);
            char c = (char) (low + rnd);
            return String.valueOf(c);
        }
        int low = 65;
        if (diceRoll >= 5) {
            //lowercase
            low += 32;
        }
        int rnd = Util.random(26);
        char c = (char) (low + rnd);
        return String.valueOf(c);
    }

    /**
     * Returns a list of words from parameter line.
     * Useful for parsing stuff ;)
     */
    public static List<String> getWords(String line) {
        return toArrayList(line.split(" "));
    }

    public static <T> List<T> toArrayList(T... objects) {
        return new ArrayList<T>(Arrays.asList(objects));
    }

    public static String directionString(boolean left, boolean right, boolean up, boolean down) {
        StringBuilder sb = new StringBuilder(4);
        if (left) {
            sb.append("l");
        }
        if (right) {
            sb.append("r");
        }
        if (up) {
            sb.append("u");
        }
        if (down) {
            sb.append("d");
        }
        return sb.toString();
    }

    public static void log(String text) {
        System.out.printf("%s %s %s\n", new Date().toString(), Const.PREPEND_LOG, text);
    }

    
}
