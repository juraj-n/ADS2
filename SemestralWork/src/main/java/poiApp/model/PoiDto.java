package poiApp.model;

import javax.lang.model.util.Elements;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getInfo() {
        String placeholder = "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        String name = this.centerString(this.name != null ? this.name : placeholder, 15);
        String desc = this.centerString(this.description != null ? this.description : placeholder, 50);
        String address = this.centerString(this.address != null ? this.address : placeholder, 20);

        String lat = String.format("%10.6f", this.latitude);
        String lon = String.format("%10.6f", this.longitude);

        String createdStr = this.createdAt != null ? this.createdAt.format(formatter) : placeholder;
        String updatedStr = this.updatedAt != null ? this.updatedAt.format(formatter) : placeholder;
        String created = this.centerString(createdStr, 19);
        String updated = this.centerString(updatedStr, 19);

        return String.format("%s | %s | %s | %s | %s | %s | %s", name, desc, address, lat, lon, created, updated);
    }
    private String centerString(String text, int width) {
        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
