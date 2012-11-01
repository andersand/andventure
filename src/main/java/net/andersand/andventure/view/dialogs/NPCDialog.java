package net.andersand.andventure.view.dialogs;

import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.script.ExecutionResult;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author asn
 */
public class NPCDialog extends Dialog implements ExecutionResult {

    protected String text;
    protected String characterName;
    protected String imageFileName;
    
    public NPCDialog(Position position, String text, 
                     String characterName, String imageFileName) {
        super(position);
        this.characterName = characterName;
        this.text = text;
        this.imageFileName = imageFileName;
    }

    @Override
    protected String getTitle() {
        return characterName;
    }

    @Override
    protected String getDynamicText() {
        return text;
    }

    @Override
    protected List<String> getFixedText() {
        return null;
    }

    @Override
    protected Image getImage() {
        return Util.loadImage("npc/" + imageFileName);
    }
}
