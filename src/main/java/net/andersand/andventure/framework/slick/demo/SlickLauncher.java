package net.andersand.andventure.framework.slick.demo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author asn
 */
public class SlickLauncher {

    public SlickLauncher() {
        try {
            AppGameContainer app = new AppGameContainer(new SlickPlaneDemoGame());
            app.setDisplayMode(800, 600, false);
            app.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
