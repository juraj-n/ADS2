package poiApp.model;

import java.time.LocalDateTime;

public class Poi {
    private int id;
    private String name, description, address;
    private PoiCoordinate latitude, longitude;
    private LocalDateTime createdAt, updatedAt;
    private boolean isDeleted;
}
