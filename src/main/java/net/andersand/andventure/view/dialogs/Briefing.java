package net.andersand.andventure.view.dialogs;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.level.Meta;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author asn
 */
public class Briefing implements Renderable {

    Position position;
    Level level;

    public Briefing(Position position, Level level) {
        this.level = level;
        this.position = position;
    }

    @Override
    public void render() {
        Meta meta = level.getMeta();
        if (meta != null) {
            Image levelBriefing = Util.loadImage("backgrounds/paper.png");
            levelBriefing.draw(position.getX(), position.getY());
            Font fontWriting = Util.loadFont("src/main/resources/font/fountainpen.fnt", "src/main/resources/font/fountainpen_0.tga");
            Font fontEmblem = Util.loadFont("src/main/resources/font/medieval.fnt", "src/main/resources/font/medieval_0.tga");
            fontWriting.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT, meta.getName(), Color.black);
            if (meta.getDescription().length() < Const.BRIEFING_LINE_MAX_WIDTH) {
                fontWriting.drawString(position.getX() + Const.FONT_INSET, position.getY() + Const.FONT_HEIGHT + fontWriting.getLineHeight()*3, meta.getDescription(), Color.black);
            }
            else {
                List<String> lines = Util.divideIntoLines(meta.getDescription(), Const.BRIEFING_LINE_MAX_WIDTH);
                for (int i=0; i<lines.size(); i++) {
                    fontWriting.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT + fontWriting.getLineHeight() * (3 + i), lines.get(i), Color.black);
                }
            }
            fontEmblem.drawString(position.getX() + Const.WINDOW_MINIMUM_WIDTH - 100, position.getY() + Const.FONT_HEIGHT, "Y", Color.black);
            fontWriting.drawString(position.getX() + Const.FONT_INSET, position.getY() + fontWriting.getLineHeight()*8, "Press space to begin...", Color.black);
        }
    }
 
}
