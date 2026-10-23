package zadanie2;

public class SvkNodesArea {
    private final String id;
    private final double minLat;
    private final double maxLat;
    private final double minLon;
    private final double maxLon;
    public SvkNodesArea(String id, double minLat, double maxLat, double minLon, double maxLon) {
        this.id = id;
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
    }

    public String getId() {
        return this.id;
    }
    public double getMinLat() {
        return this.minLat;
    }
    public double getMaxLat() {
        return this.maxLat;
    }
    public double getMinLon() {
        return this.minLon;
    }
    public double getMaxLon() {
        return this.maxLon;
    }
}
