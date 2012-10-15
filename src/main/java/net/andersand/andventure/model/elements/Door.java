package net.andersand.andventure.model.elements;

import net.andersand.andventure.Util;
import org.newdawn.slick.Image;

/**
 * @author asn
 */
public class Door extends Structure implements Interactable, Passable {

    protected Image open;
    protected Image closed;
    
    protected State state;

    @Override
    public Image getImage() {
        return state.equals(State.OPEN) ? open : closed;
    }

    @Override
    public void init(char levelDataChar) {
        if ("-".equals(String.valueOf(levelDataChar))) {
            open = Util.loadElementImage("dho");
            closed = Util.loadElementImage("dhc");
        }
        else if ("|".equals(String.valueOf(levelDataChar))) {
            open = Util.loadElementImage("dvo");
            closed = Util.loadElementImage("dvc");
        }
        state = State.CLOSED;
    }

    @Override
    public void interact() {
        state = state == State.OPEN ? State.CLOSED : State.OPEN;
        System.out.println("Using door. Door is now " + state);
    }

    @Override
    public boolean isPassableNow() {
        System.out.println("Door is " + state);
        return state == State.OPEN;
    }

    enum State {
        OPEN,
        CLOSED
    }
}
