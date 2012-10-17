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

    public Game() {
        super(Const.GAME_TITLE);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        propertyHolder = new PropertyHolder();
        controller = new Controller(propertyHolder);
        levelLoader = new LevelLoader(propertyHolder);
        Bounds bounds = levelLoader.loadLevels();
        controller.setWindowBounds(setWindowSize(bounds));
        controller.setLevels(levelLoader.getLevels());
        controller.startGame();
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
            controller.getGui().renderBackground();
        }
    }

    public void setAppContainer(AppGameContainer container) {
        this.appGameContainer = container;
    }
}
