package old.adventurespill;

/**
 * <p>Description: Items are generally game-elements
 * that can be carried and/or worn</p>
 *
 * @author Anders Nordbø
 * @version 1.0
 */
public class Item {
    Item neste;
    private String name;
    private String type;
    private String filename;
    private String ID;
    /**
     * 0 - not worn at the moment
     * 1 - head<br>
     * 2 - torso<br>
     * 3 - legs<br>
     * 4 - right arm<br>
     * 5 - left arm<br>
     */
    private int worn;
    private boolean wearable;
    /**
     * maximum damage capbility
     */
    private int damage;
    /**
     * health for the item
     */
    private int health;
    /**
     * how much the item will protect the user
     */
    private int protection;

    private int index;

    public Item(String filename, String ID, String name, String type, boolean wearable,
                int worn, int health, int damage, int protection) {
        this.filename = filename;
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.wearable = wearable;
        this.worn = worn;
        this.health = health;
        this.damage = damage;
        this.protection = protection;
        neste = null;
    }

    public Item(String filename, String ID, String type, boolean wearable,
                int worn, int health, int damage, int protection) {
        this.filename = filename;
        this.ID = ID;
        this.name = "";
        this.type = type;
        this.wearable = wearable;
        this.worn = worn;
        this.health = health;
        this.damage = damage;
        this.protection = protection;
        neste = null;
    }

    public Item(String filename, String ID, String name, String type) {
        this.filename = filename;
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.wearable = false;
        this.worn = 0;
        this.health = 0;
        this.damage = 0;
        this.protection = 0;
        neste = null;
    }

    public void makeWearable(int worn, int health, int damage, int protection) {
        this.wearable = true;
        this.worn = worn;
        this.health = health;
        this.damage = damage;
        this.protection = protection;
    }

    public boolean wearable() {
        return wearable;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int i) {
        index = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfilename() {
        return filename;
    }

    public void setfilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWorn() {
        return worn;
    }

    public int getHealth() {
        return health;
    }

    public int getProtection() {
        return protection;
    }

    public int getDamage() {
        return damage;
    }

    public void setWorn(int w) {
        worn = w;
    }

    public void setHealth(int h) {
        health = h;
    }

    public void subHealth(int s) {
        health -= s;
    }

    public void addHealth(int a) {
        health += a;
    }

    public void setDamage(int d) {
        damage = d;
    }

    public void setProtection(int p) {
        protection = p;
    }

    public String toString() {
        return "\nName: " + name + " Type: " + type + " filename: " + filename;
    }
}
