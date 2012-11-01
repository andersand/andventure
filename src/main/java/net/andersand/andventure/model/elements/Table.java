package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.level.script.Script;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * 2x2 minimum size
 * 
 * @author asn
 */
public class Table extends Structure implements ContiguousElement {

    protected Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void init(char levelDataChar, Script script) {
        // needs to be aligned; no initing necessary
    }

    @Override
    public void align(Element left, Element right, Element up, Element down) {
        String partialFileName = "table_" + Util.directionString(
                left != null, right != null, up != null, down != null);
        image = Util.loadElementImage(partialFileName);
    }

    @Override
    public List<Class<? extends Element>> getAlignmentClasses() {
        List<Class<? extends Element>> alignmentClasses = new ArrayList<Class<? extends Element>>();
        alignmentClasses.add(Table.class);
        return alignmentClasses;
    }
}
