package old.adventurespill;

public class ObstacleList {
    private Obstacle hode;

    public ObstacleList() {
        hode = null;
    }

    public void add(Obstacle o) {
        if (hode == null) {
            hode = o;
            return;
        } else if (hode.neste == null) {
            hode.neste = o;
            return;
        }
        Obstacle hjelp = hode;
        while (hjelp.neste != null)
            hjelp = hjelp.neste;
        hjelp.neste = o;
    }

    public Obstacle findObstacleByName(String name) {
        if (hode == null) return null;
        Obstacle hjelp = hode;
        while (hjelp != null && !hjelp.getName().equalsIgnoreCase(name))
            hjelp = hjelp.neste;
        return hjelp;
    }

    public String toString() {
        String utdata = "";
        Obstacle hjelp = hode;
        while (hjelp != null) {
            utdata += hjelp.toString();
            hjelp = hjelp.neste;
        }
        return utdata;

    }


}
