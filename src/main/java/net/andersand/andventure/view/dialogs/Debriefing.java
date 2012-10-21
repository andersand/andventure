package net.andersand.andventure.view.dialogs;

import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.Foe;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Debriefing extends Dialog {

    protected Level level;

    public Debriefing(Position position, Level level) {
        super(position);
        this.level = level;
    }

    @Override
    protected String getTitle() {
        return "Level completed!";
    }

    @Override
    protected String getDynamicText() {
        return level.getMeta().getDebrief();
    }

    @Override
    protected List<String> getFixedText() {
        if (level.getMeta().getDebrief() == null) {
            return summary();
        }
        return null;
    }

    protected List<String> summary() {
        List<String> lines = new ArrayList<String>();
        int numFoes = level.getFoes().size();
        int numDeadFoes = 0;
        for (Foe foe : level.getFoes()) {
            if (foe.isDead()) {
                numDeadFoes++;
            }
        }
        lines.add(String.format("Foes defeated.........%s of %s", numFoes, numDeadFoes));
        return lines;
    }

    @Override
    protected Image getImage() {
        return null;
    }
}
