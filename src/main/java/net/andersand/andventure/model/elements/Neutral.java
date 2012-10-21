package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.level.script.Script;

/**
 * Neutrals can be either passive or NPCs
 * 
 * @author asn
 */
public class Neutral extends Creature {
    
    @Override
    public void init(char levelDataChar, Script script) {
        image = Util.loadElementImage("n");
    }

    @Override
    protected boolean preventMove() {
        return true; // As of now, NPCs do not move
    }

    @Override
    protected void doInteraction() {
        // todo HIGH NPC script triggering here
    }

    @Override
    protected void setDeadImage() {
    }
}
