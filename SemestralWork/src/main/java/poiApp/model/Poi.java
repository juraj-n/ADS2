package poiApp.model;

import java.time.LocalDateTime;

/**
 * Class represents the POI with its location and other data.
 *
 * <p> Setters return this for chaining the calls.
 *
 * @author Juraj Nižnanský
 * */
public class Poi {
    private String id;
    private String name, description, address;
    private double latitude, longitude;
    private LocalDateTime createdAt, updatedAt;
    private boolean isDeleted;

    public String getId() {
        return id;
    }
    public Poi setId(String id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public Poi setName(String name) {
        this.name = name;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public Poi setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getAddress() {
        return address;
    }
    public Poi setAddress(String address) {
        this.address = address;
        return this;
    }
    public double getLatitude() {
        return latitude;
    }
    public Poi setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }
    public double getLongitude() {
        return longitude;
    }
    public Poi setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Poi setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public Poi setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
