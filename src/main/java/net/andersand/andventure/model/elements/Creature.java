package net.andersand.andventure.model.elements;

import net.andersand.andventure.Const;
import net.andersand.andventure.Util;
import net.andersand.andventure.model.LevelListener;
import net.andersand.andventure.model.Position;
import org.newdawn.slick.Image;

import java.lang.*;

/**
 * Creatures move independently and may perform actions
 * 
 * @author asn
 */
public abstract class Creature extends Element {
    
    private Position goal;
    protected LevelListener levelListener;
    Image image;

    protected String equipmentString;
    protected int attack;
    protected int defense;
    protected boolean dead;

    @Override
    protected void preDraw() {
        if (position == null) {
            return;
        }
        if (propertyHolder.getBoolean("settings.floorAsElements")) {
            Util.drawFloor(position);
        }
    }

    public void setLevelListener(LevelListener levelListener) {
        this.levelListener = levelListener;
    }

    public String getEquipmentString() {
        return equipmentString;
    }

    /**
    * Moves creature from current position
    * 
    * @param changeX -1, 0, 1 normally
    * @param changeY -1, 0, 1 normally
    */
    public void move(int changeX, int changeY) {
        if (position == null) {
            throw new IllegalStateException("Cannot move creature without a current position");
        }
        if (changeX != 0) {
            int x = position.getX();
            position.setX(x + changeX);
        }
        if (changeY != 0) {
            int y = position.getY();
            position.setY(y + changeY);
        }
    }

    /**
     * @return looking destination
     */
    public Position look(int changeX, int changeY) {
        if (position == null) {
            throw new IllegalStateException("Creature cannot look without a current position");
        }
        return new Position(position, changeX, changeY);
    }

    /**
     * AI main method
     * --------------
     * Strategy:
     * 1. Does the creature have a goal? 
     *    --> Move towards goal position
     *    Goals are typically set when
     *    - Player is spotted
     *    - Creature is patrolling on a route
     * 2. Is the creature just idling? 
     *    --> Move randomly or stay still depending on Chance to move
     *    - If creature is anchored to a position (ie guarding) it will not stray far from that
     */
    public void move() {
        if (dead) {
            return;
        }
        if (goal == null) {
            // no goal means idling
            boolean willAttemptMove = Math.random() < Const.AI_IDLE_CHANCE_TO_MOVE;
            if (willAttemptMove) {
                int changeX = Math.random() < .5 ? 0 : (Math.random() < .5 ? 1 : -1);
                int changeY = Math.random() < .5 ? 0 : (Math.random() < .5 ? 1 : -1);
                Position destination = new Position(position, changeX, changeY);
                // todo HIGH use InteractionHandler here
                Element elementAtDestination = levelListener.look(destination);
                if (elementAtDestination == null) {
                    position = destination;
                }
                // no else block here because creatures will not idly attack/open doors/etc.
            }
        }
    }
    
    public void interact(Element subjectElement) {
        if (subjectElement instanceof Creature) {
            Creature subject = (Creature) subjectElement;
            subject.doInteraction();
        }
    }

    /**
     * Some creatures can be interacted with, eg Neutral, and should have an 
     * implementation og this method.
     */
    protected abstract void doInteraction();

    public void attack(Element subjectElement) {
        if (subjectElement instanceof Creature) {
            Creature subject = (Creature) subjectElement;
            int attack = attack();
            subject.defend(attack);
            // todo impl. counter-attack
        }
    }
    
    @Override
    public Image getImage() {
        return image;
    }

    private int calculateDefense(String equipmentString) {
        int defense = 0;
        if (equipmentString.contains("a")) {
            defense++;
        }
        if (equipmentString.contains("h")) {
            defense++;
        }
        return defense;
    }
    
    protected void initAttackDefense() {
        attack = equipmentString.contains("s") ? 2 : 1;
        defense = calculateDefense(equipmentString);
    }

    public void defend(int attack) {
        if (defense < attack) {
            setDeadImage();
            dead = true;
        }
    }

    protected int attack() {
        return attack;
    }
    
    protected abstract void setDeadImage();


}
