package old.adventurespill;

public class ItemList {
    private Item hode;

    public ItemList() {
        hode = null;
    }

    /**
     * Adds item to this itemlist.
     * Makes a copy of the new item to avoid wrong chaining.
     *
     * @param i Item
     */
    public void add(Item i) {
        Item copyed = new Item(i.getfilename(), i.getID(), i.getName(),
                i.getType(), i.wearable(), i.getWorn(),
                i.getHealth(), i.getDamage(), i.getProtection());

        if (hode == null) {
            hode = copyed;
            hode.setIndex(0);
            return;
        } else if (hode.neste == null) {
            hode.neste = copyed;
            hode.neste.setIndex(hode.getIndex() + 1);
            return;
        }
        Item hjelp = hode;
        while (hjelp.neste != null)
            hjelp = hjelp.neste;
        hjelp.neste = copyed;
        hjelp.neste.setIndex(hjelp.getIndex() + 1);
    }


    public void add(ItemList ny) {
        ItemList hjelp = new ItemList();
        hjelp.hode = hode;

        Item item = ny.hode;
        while (item != null) {
            hjelp.add(new Item(item.getfilename(), item.getID(), item.getName(),
                    item.getType(), item.wearable(), item.getWorn(),
                    item.getHealth(), item.getDamage(), item.getProtection()));
            // index blir for hver gang satt i   add(Item i)
            item = item.neste;
        }
        hode = hjelp.hode;
    }

    public Item rob(Item i) {
        Item hjelp = hode;
        Item robbed;

        if (hode == null) // empty itemlist
            return null;
        if (hode.neste == null) // only one item in list
        {
            if (hode.getID().equalsIgnoreCase(i.getID())) // and the item is the one
            {
                robbed = hode;
                hode = null;
                return robbed;
            } else return null; // and the item is not in the list
        }
        if (hode.getID().equalsIgnoreCase(i.getID())) // the first item is the one
        {
            robbed = hode;
            hode = hode.neste;
            return robbed;
        }
        // search the whole list
        while (hjelp.neste != null && !hjelp.neste.getID().equalsIgnoreCase(i.getID())) {
            hjelp = hjelp.neste;
        }
        robbed = hjelp.neste;
        if (hjelp.neste != null)
            hjelp.neste = hjelp.neste.neste;
        return robbed;
    }

    public ItemList rob(ItemList i) {
        ItemList hjelp = new ItemList();
        ItemList robbed = new ItemList();
        hjelp.hode = hode;

        Item item = i.hode;
        while (item != null) {
            hjelp.rob(new Item(item.getfilename(), item.getID(), item.getName(),
                    item.getType(), item.wearable(), item.getWorn(),
                    item.getHealth(), item.getDamage(), item.getProtection()
            ));
            robbed.add(new Item(item.getfilename(), item.getID(), item.getName(),
                    item.getType(), item.wearable(), item.getWorn(),
                    item.getHealth(), item.getDamage(), item.getProtection()
            ));
            item = item.neste;
        }
        hode = hjelp.hode;
        return robbed;
    }

    public Item findItemByID(String ID) {
        if (hode == null) return null;
        Item hjelp = hode;
        if (Spill.DEBUG_2)
            System.out.println(hjelp.getName() + " " + hjelp.getType());
        while (hjelp != null && !hjelp.getID().equalsIgnoreCase(ID)) {
            if (Spill.DEBUG_2)
                System.out.println(hjelp.getName() + " " + hjelp.getType());
            hjelp = hjelp.neste;
        }
        return hjelp;
    }

    public Item findItem(String name, String type) {
        if (hode == null) return null;
        Item hjelp = hode;
        while (hjelp != null &&
                !hjelp.getName().equalsIgnoreCase(name) &&
                !hjelp.getType().equalsIgnoreCase(type))
            hjelp = hjelp.neste;
        return hjelp;
    }

    public Item findItem(String name) {
        if (hode == null) return null;
        Item hjelp = hode;
        while (hjelp != null &&
                !hjelp.getName().equalsIgnoreCase(name))
            hjelp = hjelp.neste;
        return hjelp;
    }

    public Item findItemWithIndex(int index) {
        if (hode == null)
            return null;
        Item hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getIndex() == index)
                return hjelp;
            hjelp = hjelp.neste;
        }
        return hjelp;
    }

    public int countAll() {
        int j = 0;
        if (hode == null) return j;
        Item hjelp = hode;
        while (hjelp != null) {
            j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int countType(String type) {
        int j = 0;
        if (hode == null) return j;
        Item hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getType().equalsIgnoreCase(type))
                j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int countName(String name) {
        int j = 0;
        if (hode == null) return j;
        Item hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getName().equalsIgnoreCase(name))
                j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int countSpec(String name, String type) {
        int j = 0;
        if (hode == null) return j;
        Item hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getName().equalsIgnoreCase(name) &&
                    hjelp.getType().equalsIgnoreCase(type))
                j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int getTotalProtection() {
        if (hode == null) return 0;

        int armor = 0;
        Item hjelp = hode;
        while (hjelp != null) {
            armor += hjelp.getProtection();
        }
        return armor;
    }

    /**
     * Returns a list with only one of each item from actual list
     *
     * @return ItemList
     */
    public ItemList getDistinctList() {
        if (hode == null) return null; // empty list

        ItemList distinct = new ItemList();
        Item hjelp = hode;

        while (hjelp != null) {
            if (distinct.findItemByID(hjelp.getID()) == null)
                distinct.add(new Item(hjelp.getfilename(), hjelp.getID(), hjelp.getName(),
                        hjelp.getType(), hjelp.wearable(), hjelp.getWorn(),
                        hjelp.getHealth(), hjelp.getDamage(), hjelp.getProtection()
                ));
            hjelp = hjelp.neste;
        }
        return distinct;
    }

    public String toString() {
        String utdata = "";
        Item hjelp = hode;
        while (hjelp != null) {
            utdata += hjelp.toString();
            hjelp = hjelp.neste;
        }
        return utdata;
    }

    public String nameLine() {
        ItemList items = getDistinctList();
        if (items == null) return "";
        String utdata = "";
        Item hjelp = items.hode;
        while (hjelp != null) {
            if (!hjelp.getName().equals("")) // some have name and type (rusted dagger)
                utdata += hjelp.getName() + " " + hjelp.getType();
            else // some items have only type (as in arrow)
                utdata += hjelp.getType();
            int c = countSpec(hjelp.getName(), hjelp.getType());
            if (c > 1) utdata += "(" + c + ")";
            utdata += ", ";
            hjelp = hjelp.neste;
        }
        if (utdata.length() >= 2)
            return utdata.substring(0, utdata.length() - 2);
        else
            return "";
    }

}
