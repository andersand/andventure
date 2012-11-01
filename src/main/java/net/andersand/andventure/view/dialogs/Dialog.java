package net.andersand.andventure.view.dialogs;

import net.andersand.andventure.engine.Const;
import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

import java.util.List;

/**
 * @author asn
 */
public abstract class Dialog implements Renderable {

    protected Position position;
    protected Font genericFont;
    protected String randomEmblemChar;
    private int blinkFPS = Const.FPS;
    private final static int BLINK_FRAME_WEND = Const.FPS * Const.TEXT_BLINK_RATE;

    protected void loadGenericFont() {
        genericFont = Util.loadFont("fatal-fury");
    }

    protected Dialog(Position position) {
        this.position = position;
        loadGenericFont();
        randomEmblemChar = Util.randomAlphaNumeric();
    }

    protected abstract String getTitle();
    protected abstract String getDynamicText();
    protected abstract List<String> getFixedText();
    protected abstract Image getImage();

    @Override
    public void render() {
        Image paper = Util.loadImage("backgrounds/paper.png");
        paper.draw(position.getX(), position.getY());
        Font writingFont = Util.loadFont("fountainpen");
        writingFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT, getTitle(), Color.black);
        if (getDynamicText() != null) {
            drawDynamicText(writingFont);
        }
        else if (getFixedText() != null) {
            drawFixedText();
        }
        if (getImage() != null) {
            drawImage();
        }
        else {
            drawRandomEmblem();
        }
        drawPressSpace();
    }

    protected void drawPressSpace() {
        // make the text blink at a specific rate
        if (blinkFPS < BLINK_FRAME_WEND/2) {
            genericFont.drawString(position.getX() + Const.PAPER_WIDTH - 220, position.getY() + Const.PAPER_HEIGHT - 50, "Press space to continue", Color.black);
        }
        if (blinkFPS == 0) {
            blinkFPS = BLINK_FRAME_WEND;
        }
        else {
            blinkFPS--;
        }
    }

    protected void drawImage() {
        Image i = getImage();
        i.draw(position.getX() + Const.PAPER_WIDTH - 100, position.getY() + Const.FONT_HEIGHT);
    }

    protected void drawRandomEmblem() {
        Font emblemFont = Util.loadFont("medieval");
        int adjust = emblemFont.getWidth(randomEmblemChar);
        emblemFont.drawString(position.getX() + Const.PAPER_WIDTH - (80 + adjust), position.getY() + Const.FONT_HEIGHT, randomEmblemChar, Color.black);
    }

    protected void drawFixedText() {
        List<String> lines = getFixedText();
        for (int i=0; i<lines.size(); i++) {
            genericFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT + genericFont.getLineHeight() * (10 + i), lines.get(i), Color.black);
        }
    }

    protected void drawDynamicText(Font writingFont) {
        if (getDynamicText().length() < Const.BRIEFING_LINE_MAX_WIDTH) {
            writingFont.drawString(position.getX() + Const.FONT_INSET, position.getY() + Const.FONT_HEIGHT + writingFont.getLineHeight()*3, getDynamicText(), Color.black);
        }
        else {
            List<String> lines = Util.divideIntoLines(getDynamicText(), Const.BRIEFING_LINE_MAX_WIDTH);
            for (int i=0; i<lines.size(); i++) {
                writingFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT + writingFont.getLineHeight() * (3 + i), lines.get(i), Color.black);
            }
        }
    }

}
