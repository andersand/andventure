package net.andersand.andventure;

import net.andersand.andventure.model.Position;
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

    public static void drawFloor(Position position) {
        int x = Util.getElementPixelX(position);
        int y = Util.getElementPixelY(position);
        try {
            Image floor = new Image(Const.IMG_DIR + "fl" + Const.ELEMENT_IMG_SUFFIX);
            floor.draw(x, y);
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a random integer between 0 and upperLimit-1.
     * For example if passed upperLimit 5, the method returns minimum 0 and maximum 4.
     */
    public static int random(int upperLimit) {
        return (int) (Math.random() * upperLimit);
    }

    public static Font loadFont(String fntFile, String imgFile) {
        try {
            return new AngelCodeFont(fntFile, imgFile);
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }
}
