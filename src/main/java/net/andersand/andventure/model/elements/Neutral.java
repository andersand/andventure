package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.Script;
import org.newdawn.slick.Image;

/**
 * Neutrals can be either passive or NPCs
 * 
 * @author asn
 */
public class Neutral extends Creature {
    
    protected Script script;

    @Override
    public void init(char levelDataChar) {
        image = Util.loadElementImage("n");
        script = levelListener.getMeta().getScriptForNPC(position);
    }

    @Override
    protected void doInteraction() {
        // todo: NPC script triggering here
    }

    @Override
    protected void setDeadImage() {
    }
}
