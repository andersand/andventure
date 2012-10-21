package net.andersand.andventure.model.elements;

import java.util.List;

/**
 * @author asn
 */
public interface ContiguousElement {

    /**
     * The instance will align itself using these parameters
     */
    public void align(Element left, Element right, Element up, Element down);

    /**
     * The instance must define what elements it will align to. Often times this.getClass()
     * is what's needed, eg. to make tables, however other times eg. chair orientation
     * alignment to other elements is needed. Chair will align to (Wall, Table) in that order,
     * meaning if a chair is next to wall and table it will always align to table rather than wall.
     */
    public List<Class<? extends Element>> getAlignmentClasses();
    
}
