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
    private int blinkFPS = Const.FPS;
    private final static int BLINK_FRAME_WEND = Const.FPS * Const.TEXT_BLINK_RATE;
    protected String emblem;
    protected String dynamicText;
    protected List<String> fixedText;
    protected Image image;
    protected String title;

    protected void loadGenericFont() {
        genericFont = Util.loadFont("fatal-fury");
    }

    protected Dialog(Position position) {
        this.position = position;
        loadGenericFont();
    }

    public void setup() {
        emblem = getEmblem();
        if (emblem == null) {
            emblem = Util.randomAlphaNumeric();
        }
        dynamicText = getDynamicText();
        fixedText = getFixedText();
        image = getImage();
        title = getTitle();
    }

    protected abstract String getTitle();
    protected abstract String getDynamicText();
    protected abstract List<String> getFixedText();
    protected abstract Image getImage();
    protected abstract String getEmblem();

    @Override
    public void render() {
        Image paper = Util.loadImage("backgrounds/paper.png");
        paper.draw(position.getX(), position.getY());
        Font writingFont = Util.loadFont("fountainpen");
        writingFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT, title, Color.black);
        if (dynamicText != null) {
            drawDynamicText(writingFont);
        }
        else if (fixedText != null) {
            drawFixedText();
        }
        if (image != null) {
            drawImage();
        }
        else {
            drawEmblem(emblem);
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
        image.draw(position.getX() + Const.PAPER_WIDTH - 100, position.getY() + Const.FONT_HEIGHT);
    }

    protected void drawEmblem(String emblem) {
        Font emblemFont = Util.loadFont("medieval");
        int adjust = emblemFont.getWidth(emblem);
        emblemFont.drawString(position.getX() + Const.PAPER_WIDTH - (80 + adjust), position.getY() + Const.FONT_HEIGHT, emblem, Color.black);
    }

    protected void drawFixedText() {
        for (int i=0; i<fixedText.size(); i++) {
            genericFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT + genericFont.getLineHeight() * (10 + i), fixedText.get(i), Color.black);
        }
    }

    protected void drawDynamicText(Font writingFont) {
        if (dynamicText.length() < Const.BRIEFING_LINE_MAX_WIDTH) {
            writingFont.drawString(position.getX() + Const.FONT_INSET, position.getY() + Const.FONT_HEIGHT + writingFont.getLineHeight()*3, dynamicText, Color.black);
        }
        else {
            List<String> lines = Util.divideIntoLines(dynamicText, Const.BRIEFING_LINE_MAX_WIDTH);
            for (int i=0; i<lines.size(); i++) {
                writingFont.drawString(position.getX() + 50, position.getY() + Const.FONT_HEIGHT + writingFont.getLineHeight() * (3 + i), lines.get(i), Color.black);
            }
        }
    }

}
