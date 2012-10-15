package net.andersand.andventure.model.menu;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.level.Level;
import net.andersand.andventure.model.level.Meta;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author asn
 */
public class Briefing implements Renderable {

    Position position;
    Level level;

    public Briefing(Level level) {
        this.level = level;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void render() {
        Meta meta = level.getMeta();
        if (meta != null) {
            Image levelBriefing = Util.loadImage("backgrounds/paper.png");
            levelBriefing.draw(position.getX(), position.getY());
            Font fontWriting = Util.loadFont("src/main/resources/font/fountainpen.fnt", "src/main/resources/font/fountainpen_0.tga");
            Font fontEmblem = Util.loadFont("src/main/resources/font/medieval.fnt", "src/main/resources/font/medieval_0.tga");
            fontWriting.drawString(position.getX() + 50, position.getY() + 50, meta.getName(), Color.black);
            if (meta.getDescription().length() < 35) {
                fontWriting.drawString(position.getX() + 50, position.getY() + 50 + fontWriting.getLineHeight()*3, meta.getDescription(), Color.black);
            }
            else {
                List<String> lines = divideIntoLines2(meta.getDescription());
                for (int i=0; i<lines.size(); i++) {
                    fontWriting.drawString(position.getX() + 50, position.getY() + 50 + fontWriting.getLineHeight() * (3 + i), lines.get(i), Color.black);
                }
            }
            fontEmblem.drawString(position.getX() + Const.WINDOW_MINIMUM_WIDTH - 100, position.getY() + 50, "Y", Color.black);
            fontWriting.drawString(position.getX() + 50, position.getY() + fontWriting.getLineHeight()*8, "Press space to begin...", Color.black);
        }
    }

    /**
     * @deprecated cuts words in two, use divideIntoLines2
     */
    private List<String> divideIntoLines(String description) {
        List<String> lines = new ArrayList<String>();
        int index = 0;
        int lineWidth = Const.BRIEFING_LINE_MAX_WIDTH;
        while (index < description.length()) {
            if (index + lineWidth > description.length()) {
                lineWidth = description.length() - index;
            }
            String line = description.substring(index, lineWidth + index);
            lines.add(line);
            index += lineWidth;
        }
        return lines;
    }

    private List<String> divideIntoLines2(String description) {
        List<String> lines = new ArrayList<String>();
        List<String> words = Arrays.asList(description.split(" "));
        String line = "";
        Iterator<String> it = words.iterator();
        String nextWord;
        while (it.hasNext()) {
            nextWord = it.next();
            if (line.length() + nextWord.length() < Const.BRIEFING_LINE_MAX_WIDTH) {
                line += nextWord + " ";
            }
            else {
                lines.add(line);
                line = nextWord + " ";
            }
        }
        lines.add(line);
        return lines;
    }
 
}
