package old.adventurespill;

public class Player {
    private ItemList inventory;
    private int hp;

    public Player(String name) {
        inventory = new ItemList();
        hp = 100;
    }

    public ItemList inventory() {
        return inventory;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void addHp(int hp) {
        this.hp += hp;
    }

    public void subHp(int hp) {
        this.hp -= hp;
    }

    public int getHp() {
        return hp;
    }

    public int getArmorValue() {
        return inventory.getTotalProtection();
    }
}
