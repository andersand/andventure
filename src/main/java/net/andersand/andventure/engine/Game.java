package net.andersand.andventure.engine;

import net.andersand.andventure.Const;
import net.andersand.andventure.GameState;
import net.andersand.andventure.PropertyHolder;
import net.andersand.andventure.model.Tile;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.level.LevelLoader;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asn
 */
public class Game extends BasicGame {

    protected PropertyHolder propertyHolder;
    protected LevelLoader levelLoader;
    protected Controller controller;
    protected AppGameContainer appGameContainer;
    protected List<Tile> floorTiles = new ArrayList<Tile>();

    public Game() {
        super(Const.GAME_TITLE);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        propertyHolder = new PropertyHolder();
        controller = new Controller();
        levelLoader = new LevelLoader(propertyHolder);
        Bounds bounds = levelLoader.loadLevels();
        controller.setWindowBounds(setWindowSize(bounds));
        controller.setLevels(levelLoader.getLevels());
        controller.startGame();
        initBackground(bounds);
    }

    private Bounds setWindowSize(Bounds bounds) throws SlickException {
        if (bounds.width < Const.WINDOW_MINIMUM_WIDTH) {
            bounds.width = Const.WINDOW_MINIMUM_WIDTH;
        }
        if (bounds.height < Const.WINDOW_MINIMUM_HEIGHT) {
            bounds.height = Const.WINDOW_MINIMUM_HEIGHT;
        }
        appGameContainer.setDisplayMode(bounds.width, bounds.height, false);
        return bounds;
    }

    // todo MID incorporate floor type into level metadata (stone/wood for interior, dirt/grass for exterior)
    // this will allow for outdoor levels
    private void initBackground(Bounds bounds) {
        if (!propertyHolder.getBoolean("settings.floorAsElements")) {
            int floorTilesHoriz = bounds.width / Const.BGTILE_SIZE_PIXELS;
            int floorTilesVert = bounds.height / Const.BGTILE_SIZE_PIXELS;
            for (int i = 0; i <= floorTilesHoriz; i++) {
                for (int j = 0; j <= floorTilesVert; j++) {
                    Image image = Util.loadImage("backgrounds/floor.png");
                    image.rotate(Util.random(4) * 90);
                    Tile tile = new Tile(image);
                    tile.setPosition(new Position(i, j));
                    floorTiles.add(tile);
                }
            }
        }
    }

    private void drawFloorTiles() {
        for (Tile tile : floorTiles) {
            tile.render();
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (controller.getGameState().equals(GameState.IN_GAME)) {
            controller.handlePlayerInput(container);
            controller.performAI(container);
        }
        controller.handleUserInput(container);
        Display.sync(Const.FPS);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if (controller.getGameState().equals(GameState.IN_GAME)) {
            renderBackground(); // perhaps only needed first tick every level change?
            renderElements();
        }
        renderEnvironment(g);
    }

    private void renderElements() {
        controller.getCurrentLevel().render();
    }

    private void renderEnvironment(Graphics g) {
        controller.getGui().render();
    }

    private void renderBackground() {
        if (!propertyHolder.getBoolean("settings.floorAsElements")) {
            drawFloorTiles();
        }
    }

    public void setAppContainer(AppGameContainer container) {
        this.appGameContainer = container;
    }
}
