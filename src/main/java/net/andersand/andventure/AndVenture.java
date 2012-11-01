package net.andersand.andventure;

import net.andersand.andventure.engine.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author asn
 */
public class AndVenture {

    public static void main(String[] args) {
        new AndVenture();
    }

    public AndVenture() {
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
