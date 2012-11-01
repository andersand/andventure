package net.andersand.andventure.engine;

import net.andersand.andventure.interactions.Interaction;
import net.andersand.andventure.interactions.SimpleInteraction;
import net.andersand.andventure.model.Position;
import net.andersand.andventure.model.elements.*;
import net.andersand.andventure.model.level.Level;
import org.newdawn.slick.Input;

/**
 * Consider refactoring this somehow, as it now creates a lot of these instances
 * 
 * @author asn
 */
public class InteractionHandler {

    protected boolean moveRequested;
    protected int changeX = 0;
    protected int changeY = 0;
    protected Level currentLevel;

    public InteractionHandler(Input input, Level level) {
        currentLevel = level;
        queryInput(input);
    }

    protected void queryInput(Input input) {
        if (input.isKeyDown(Input.KEY_A)) {
            changeX = -1;
        }
        if (input.isKeyDown(Input.KEY_S)) {
            changeY = 1;
        }
        if (input.isKeyDown(Input.KEY_D)) {
            changeX = 1;
        }
        if (input.isKeyDown(Input.KEY_W)) {
            changeY = -1;
        }
        moveRequested = !(changeX == 0 && changeY == 0);
    }

    public Interaction perform(Creature actor) {
        // you gotta look before you go bro...
        // or else you might get stuck in a wall or something...
        Position lookingDestination = actor.look(changeX, changeY);
        Element elementAtDestination = currentLevel.look(lookingDestination);
        if (elementAtDestination == null) {
            actor.move(changeX, changeY);
            return SimpleInteraction.MOVE_PERFORMED;
        }
        if (elementAtDestination instanceof Passable) {
            Passable passable = (Passable) elementAtDestination;
            if (passable.isPassableNow()) {
                actor.move(changeX, changeY);
                return SimpleInteraction.MOVE_PERFORMED;
            }
        }
        if (elementAtDestination instanceof Interactable) {
            return ((Interactable) elementAtDestination).interact(actor);
        }
        if (elementAtDestination instanceof Foe) {
            actor.attack(elementAtDestination);
        }
        return SimpleInteraction.MOVE_PROHIBITED;
    }

    public boolean isMoveRequested() {
        return moveRequested;
    }
}