package net.andersand.andventure.model.elements;

import net.andersand.andventure.engine.Const;
import net.andersand.andventure.engine.Util;
import net.andersand.andventure.model.interactions.CreatureLevelInteraction;
import net.andersand.andventure.model.Inventory;
import net.andersand.andventure.model.Position;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Creatures move independently and may perform actions
 * 
 * @author asn
 */
public abstract class Creature extends Element implements Passable  {
    
    protected Position target;
    protected CreatureLevelInteraction creatureLevelInteraction;
    protected Image bodyImage;
    protected Inventory inventory;
    protected int attack;
    protected int defense;
    protected boolean dead;
    protected Weapon equippedWeapon;

    protected Creature() {
        inventory = new Inventory();
        bodyImage = Util.loadElementImage(getBodyImage());
    }

    @Override
    protected void preDraw() {
    }

    @Override
    protected void postDraw() {
        for (Wearable wearable : inventory.getWearables()) {
            draw(wearable.getImage());
        }
        if (equippedWeapon != null) {
            draw(equippedWeapon.getImage());
        }
    }

    public void setCreatureLevelInteraction(CreatureLevelInteraction creatureLevelInteraction) {
        this.creatureLevelInteraction = creatureLevelInteraction;
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
     * 1. Does the creature have a target? 
     *    --> Move towards target position
     *    Targets are typically set when
     *    - Player is spotted
     *    - Creature is patrolling on a route
     * 2. Is the creature just idling? 
     *    --> Move randomly or stay still depending on Chance to move
     *    - If creature is anchored to a position (ie guarding) it will not stray far
     */
    public void move() {
        if (dead || preventMove()) {
            return;
        }
        if (target == null) {
            idle();
        }
        else {
            moveTowardsTarget();
        }
    }

    protected void moveTowardsTarget() {
        // assumes target is defined
        int changeX = position.getX() > target.getX() ? 1 : (position.getX() == target.getX() ? 0 : -1);
        int changeY = position.getY() > target.getY() ? 1 : (position.getY() == target.getY() ? 0 : -1);
    }

    protected void idle() {
        boolean willAttemptMove = Math.random() < Const.AI_IDLE_CHANCE_TO_MOVE;
        if (willAttemptMove) {
            int changeX = Math.random() < .5 ? 0 : (Math.random() < .5 ? 1 : -1);
            int changeY = Math.random() < .5 ? 0 : (Math.random() < .5 ? 1 : -1);
            Position destination = new Position(position, changeX, changeY);
            // todo MID use InteractionHandler here?
            Element elementAtDestination = creatureLevelInteraction.look(destination);
            if (elementAtDestination == null) {
                position = destination;
            }
            else if (this instanceof Foe) {
                // todo MID refactor: replace instanceof checking with abstract method call.
                // todo LOW decide what to go for: should creatures idly open doors?
                //         right now they just move through doors if they have been opened by someone
                if (elementAtDestination instanceof Door) {
                    Door door = (Door) elementAtDestination;
                    if (door.isPassableNow()) {
                        position = destination;
                    }
                }
            }
        }
    }

    protected abstract boolean preventMove();

    protected abstract String getDeadImage();

    protected abstract String getBodyImage();
    
    public void attack(Element subjectElement) {
        if (subjectElement instanceof Creature) {
            Creature subject = (Creature) subjectElement;
            subject.defend(attack);
            // todo MID impl. counter-attack
        }
    }

    @Override
    public Image getImage() throws SlickException {
        return bodyImage;
    }

    protected void calculateDefense() {
        defense = 0;
        for (Wearable w : inventory.getWearables()) {
            defense += w.getDefenseValue();
        }
    }

    private void calculateAttack() {
        attack = 1;
        if (equippedWeapon != null) {
            attack += equippedWeapon.getAttackValue();
        }
    }

    public void defend(int attack) {
        if (defense < attack) {
            bodyImage = Util.loadElementImage(getDeadImage());
            dead = true;
        }
    }
    
    public boolean isDead() {
        return dead;
    }

    public void equip(String objectString) {
        inventory.addObjectsFromString(objectString);
        if (equippedWeapon == null) {
            equippedWeapon = inventory.getBestWeapon();
        }
        calculateAttack();
        calculateDefense();
    }

    @Override
    public boolean isPassableNow() {
        return dead; // allow walking over dead creatures
    }

}
