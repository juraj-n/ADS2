package poiApp.model;

import java.time.LocalDateTime;

/**
 * Class represents the POI with its location and other data.
 *
 * @author Juraj Nižnanský
 * */
public class PoiDto {
    private final String id;
    private final String name, description, address;
    private final double latitude, longitude;
    private final LocalDateTime createdAt, updatedAt;
    public PoiDto(
            String id,
            String name, String description, String address,
            double latitude, double longitude,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getAddress() {
        return this.address;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
