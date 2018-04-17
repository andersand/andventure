package old.adventurespill;

/**
 * <p>Description: Obstacles are generally mapelements that cannot be carried</p>
 *
 * @author Anders Nordbø
 * @version 1.0
 */
public class Obstacle {
    Obstacle neste;
    private String name;
    private String ID;
    /**
     * Negative numbers = How much damage is inflicted when encountered<br>
     * Positive numbers = How much healing is done to character when encountered.
     */
    private int damage;
    /**
     * How much damage can this obstacle endure (0=unbreakable)
     */

    private int health;
    private boolean passable;

    public Obstacle(String name, int damage, int health, boolean passable) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.passable = passable;

        neste = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void setPassable(boolean status) {
        passable = status;
    }

    public boolean passable() {
        return passable;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public String toString() {
        return "\nName: " + name +
                " DMG: " + damage +
                " HP: " + health +
                " Passable: " + passable;
    }
}
