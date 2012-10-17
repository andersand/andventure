package net.andersand.andventure.model.menu;

import net.andersand.andventure.*;
import net.andersand.andventure.engine.Bounds;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.Renderable;
import net.andersand.andventure.model.Tile;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class GUI implements Renderable {

    protected Briefing briefing;
    protected GameStateListener gameStateListener;
    protected PropertyHolder propertyHolder;
    protected List<Tile> floorTiles = new ArrayList<Tile>();

    public GUI(GameStateListener gameStateListener, PropertyHolder propertyHolder) {
        this.gameStateListener = gameStateListener;
        this.propertyHolder = propertyHolder;
    }

    private void createBriefing(Level level) {
        Position p = null;
        if (briefing != null) {
            p = briefing.getPosition();
        }
        briefing = new Briefing(level);
        briefing.setPosition(p);
    }
    
    public void render() {
        if (gameStateListener.getGameState().equals(GameState.BRIEFING)) {
            briefing.render();
        }
    }

    public void setBriefingPosition(Position position) {
        briefing.setPosition(position);
    }

    // todo MID incorporate floor type into level metadata (stone/wood for interior, dirt/grass for exterior)
    // this will allow for outdoor levels
    public void initLevel(Bounds bounds, Level currentLevel) {
        if (!propertyHolder.getBoolean("settings.floorAsElements")) {
            String environmentFileName = currentLevel.getMeta().getEnvironment();
            if (environmentFileName == null) {
                environmentFileName = "interior_stone";
            }
            int floorTilesHoriz = bounds.width / Const.BGTILE_SIZE_PIXELS;
            int floorTilesVert = bounds.height / Const.BGTILE_SIZE_PIXELS;
            for (int i = 0; i <= floorTilesHoriz; i++) {
                for (int j = 0; j <= floorTilesVert; j++) {
                    Image image = Util.loadImage("backgrounds/" + environmentFileName + ".png");
                    image.rotate(Util.random(4) * 90);
                    Tile tile = new Tile(image);
                    tile.setPosition(new Position(i, j));
                    floorTiles.add(tile);
                }
            }
        }
        createBriefing(currentLevel);
    }

    private void drawFloorTiles() {
        for (Tile tile : floorTiles) {
            tile.render();
        }
    }


    public void renderBackground() {
        drawFloorTiles();
    }
}
