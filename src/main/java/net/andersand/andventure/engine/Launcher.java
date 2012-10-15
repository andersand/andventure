package net.andersand.andventure.engine;

import net.andersand.andventure.engine.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author asn
 */
public class Launcher {

    public Launcher() {
        try {
            Game g = new Game();
            AppGameContainer container = new AppGameContainer(g);
            g.setAppContainer(container);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }

    }
}
