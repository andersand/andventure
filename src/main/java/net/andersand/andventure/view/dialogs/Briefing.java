package net.andersand.andventure.view.dialogs;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author asn
 */
public class Briefing extends Dialog {

    protected Level level;

    public Briefing(Position position, Level level) {
        super(position);
        this.level = level;
    }

    @Override
    protected String getTitle() {
        return level.getMeta().getName();
    }

    @Override
    protected String getDynamicText() {
        return level.getMeta().getDescription();
    }

    @Override
    protected List<String> getFixedText() {
        return null;
    }

    @Override
    protected Image getImage() {
        return null;
    }
}
