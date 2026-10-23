package zadanie2;

public class SvkNode {
    private final String id;
    private final double latitude;
    private final double longitude;
    public SvkNode(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return this.id;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
}
