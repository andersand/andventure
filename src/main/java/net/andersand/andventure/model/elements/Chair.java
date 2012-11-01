package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * A chair may be a simple enough element, however it is complicated enough to align!
 * 
 * @author asn
 */
public class Chair extends Structure implements Passable, ContiguousElement {

    protected Image image;
    private char[] indexToDirection = {'l', 'r', 'u', 'd'};

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        // needs to be aligned; no initing necessary
    }

    @Override
    public boolean isPassableNow() {
        return true;
    }

    @Override
    public void align(Element left, Element right, Element up, Element down) {
        char leftEl = what(left);
        char rightEl = what(right);
        char upEl = what(up);
        char downEl = what(down);
        char direction = resolveDirection(new char[]{leftEl, rightEl, upEl, downEl});
        String partialFileName = "chair_" + direction;
        image = Util.loadElementImage(partialFileName);
    }

    private char resolveDirection(char[] neighbours) {
        char directionTable = findNeighbour(neighbours, 't');
        char directionWall = findNeighbour(neighbours, 'w');
        return directionTable != 'x' ? directionTable 
                : (directionWall != 'x' ? mirror(directionWall) : 'r');
    }

    private char mirror(char dir) {
        switch (dir) {
            case 'l':
                return 'r';
            case 'r':
                return 'l';
            case 'u':
                return 'd';
            case 'd':
                return 'u';
        }
        return 'x';
    }

    private char findNeighbour(char[] neighbours, char t) {
        for (int i = 0; i < neighbours.length; i++) {
            char neighbour = neighbours[i];
            if (neighbour == t) {
                return indexToDirection[i];
            }
        }
        return 'x';
    }

    private char what(Element el) {
        if (el == null) {
            return 'x';
        }
        return el instanceof Table ? 't' : 'w';
    }

    @Override
    public List<Class<? extends Element>> getAlignmentClasses() {
        List<Class<? extends Element>> alignmentClasses = new ArrayList<Class<? extends Element>>();
        alignmentClasses.add(Wall.class);
        alignmentClasses.add(Table.class);
        return alignmentClasses;
    }
}
