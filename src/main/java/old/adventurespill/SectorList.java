package old.adventurespill;

public class SectorList {
    private Sector hode;

    public SectorList() {
        hode = null;
    }

    public void add(Sector s) {
        if (hode == null) {
            hode = s;
            return;
        } else if (hode.neste == null) {
            hode.neste = s;
            return;
        }
        Sector hjelp = hode;
        while (hjelp.neste != null) {
            hjelp = hjelp.neste;
        }
        hjelp.neste = s;
    }

    /**
     * Don't use this. Use change(Sector new_,Sector old)
     *
     * @param s Sector
     */
    public void remove(Sector s) {
        Sector hjelp = hode;

        while (hjelp.neste != null && hjelp.neste.equals(s)) {
            hjelp = hjelp.neste;
        }
        hjelp.neste = hjelp.neste.neste;  // the sector has been omitted from chain
    }

    /**
     * finds the sector before and after and links the new in between there
     *
     * @param new_ Sector
     * @param old  Sector
     */
    public void change(Sector new_, Sector old) {
        if (new_ == null) {
            if (Spill.DEBUG)
                System.out.println("Sector was null");
            return;
        }
        if (new_.getX() != old.getX() || new_.getY() != old.getY()) {
            if (Spill.DEBUG) {
                System.out.println(
                        "Bad sector Point on change\nNEW X=" + new_.getX() +
                                " Y=" + new_.getY() + " OLD X=" + old.getX() + " Y=" + old.getY());
            }
            return;
        }

        if (hode == null) return;
        if (hode.equals(old)) hode = new_;

        Sector hjelp = hode;
        while (hjelp.neste != null && !hjelp.neste.equals(old)) {
            hjelp = hjelp.neste;
        }
        // index must be copyed first
        new_.setIndex(old.getIndex());
        // link new sector to the tale of the old
        new_.neste = hjelp.neste.neste;
        // link new sector to the front of the old
        hjelp.neste = new_;
    }

    /**
     * Returns the sector with the given position if it exists.
     *
     * @param x int
     * @param y int
     * @return Sector
     */
    public Sector findSector(int x, int y) {
        if (hode == null)
            return null;
        Sector hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getX() == x && hjelp.getY() == y)
                return hjelp;
            hjelp = hjelp.neste;
        }
        return hjelp;
    }

    /**
     * Returns the sector with the given "sectorposition" if it exists.
     * Does the calculations for comfort since all sectors are 15 x15
     *
     * @param x int
     * @param y int
     * @return Sector
     */
    public Sector findSector2(int x, int y) {
        if (hode == null)
            return null;
        Sector hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getX() == (x * 15) && hjelp.getY() == (y * 15))
                return hjelp;
            hjelp = hjelp.neste;
        }
        return hjelp;
    }

    public Sector findSectorWithIndex(int index) {
        if (hode == null)
            return null;
        Sector hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getIndex() == index)
                return hjelp;
            hjelp = hjelp.neste;
        }
        return hjelp;
    }

    /**
     * Fills up blank (null) sectors with floor sectors for 300x300 pixel maps
     * 300px mapwidth / 15px sectorwidth = 20 sectors X
     * 300px mapheight / 15px sectorheight = 20 sectors Y
     *
     * @param floor Obstacle, must be retrieved from the ObstacleList in use
     */
    public void fillBlanks(Obstacle floor) {
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++) {
                Sector s = findSector2(i, j);
                if (s == null) // null-sector
                    add(new Sector(i, j, "floor", floor));
                else {
                    String type = s.getType().getName();
                    // dont place floors under water,walls,floors,rubble
                    if (!type.equalsIgnoreCase("wt") || !type.equalsIgnoreCase("wd") ||
                            !type.equalsIgnoreCase("ws") || !type.equalsIgnoreCase("fl") ||
                            !type.equalsIgnoreCase("rb"))
                        add(new Sector(i, j, "floor", floor));
                }
            }
    }

    public int countObstacles(String type) {
        int j = 0;
        if (hode == null)
            return 0;
        Sector hjelp = hode;
        while (hjelp != null) {
            if (hjelp.getType().getName().equalsIgnoreCase(type))
                j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int countObstacles() {
        int j = 0;
        if (hode == null)
            return 0;
        Sector hjelp = hode;
        while (hjelp != null) {
            j++;
            hjelp = hjelp.neste;
        }
        return j;
    }

    public int countItems() {
        int j = 0;
        if (hode == null)
            return 0;
        Sector hjelp = hode;
        while (hjelp != null) {
            j += hjelp.getItems().countAll();
            hjelp = hjelp.neste;
        }
        return j;
    }

    public String toString() {
        String utdata = "";
        Sector hjelp = hode;
        while (hjelp != null) {
            utdata += hjelp.toString();
            hjelp = hjelp.neste;
        }
        return utdata;

    }

}
