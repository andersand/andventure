package old.adventurespill;

public class Sector {
    private int x, y;
    private Obstacle type;

    /**
     * Certain sectors (doors etc) may be locked.
     * Locked sectors are not passable, but thats handled in the Brett class
     * (verifySector)
     */
    private boolean locked;

    /**
     * Some doors and chests needs to be unlocked by a key matching this number
     */
    private String key_value;

    /**
     * List of items for all sector variants
     */
    private ItemList items;

    /**
     * Sector names are useful when you want output
     */
    private String name;

    /**
     * NPC datafields
     */
    private String NPC_message;
    private String NPC_name;
    private boolean NPC_female;

    private static int antall = 0;
    private int index;
    Sector neste;

    /**
     * sectors are 15x15 so neither x nor y can be greater than 20
     * as long as the map is 300X300 (15 divisible mapsize)
     *
     * @param x    int
     * @param y    int
     * @param type Obstacle
     */
    public Sector(int x, int y, Obstacle type) {
        this.x = x * 15;
        this.y = y * 15;
        this.type = type;
        locked = false;
        key_value = "";
        name = "";
        items = new ItemList();
        index = antall++;
        neste = null;
    }

    public Sector(int x, int y, String name, Obstacle type) {
        this.x = x * 15;
        this.y = y * 15;
        this.type = type;
        locked = false;
        key_value = "";
        this.name = name;
        items = new ItemList();
        index = antall++;
        neste = null;
    }

    /**
     * Locks the sector with given keycode
     *
     * @param keyID String
     */
    public void setLocked(String keyID) {
        locked = true;
        setKeyVal(keyID);
    }

    /**
     * Unlocks the sector
     */
    public void setUnlocked() {
        locked = false;
    }

    public boolean locked() {
        return locked;
    }

    public void setKeyVal(String keyval) {
        key_value = keyval;
    }

    public String getKeyVal() {
        return key_value;
    }

    public void setNPC(String name, boolean female, String msg) {
        NPC_name = name;
        NPC_female = female;
        NPC_message = msg;
    }

    public void setNPCMsg(String msg) {
        NPC_message = msg;
    }

    public String getNPCMsg() {
        return NPC_message;
    }

    public void setNPCName(String name) {
        NPC_name = name;
    }

    public String getNPCName() {
        return NPC_name;
    }

    public void setNPCfemale(boolean female) {
        NPC_female = female;
    }

    public boolean getNPCfemale() {
        return NPC_female;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Obstacle type) {
        this.type = type;
    }

    public ItemList getItems() {
        return items;
    }

    /**
     * used when changing the sector
     *
     * @param i int
     */
    public void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getX2() {
        if (x > 0)
            return x / 15;
        else
            return 0;
    }

    public int getY() {
        return y;
    }

    public int getY2() {
        if (y > 0)
            return y / 15;
        else
            return 0;
    }

    public Obstacle getType() {
        return type;
    }

    public String toString() {
        return "\nX: " + getX() + " Y: " + getY() + " Type: " +
                type.getName() + " Locked: " + locked + " Index: " + index;
    }

}
