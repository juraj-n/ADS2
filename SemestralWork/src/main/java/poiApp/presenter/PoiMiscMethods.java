package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PoiMiscMethods {
    public static double haversine(double latEntered, double lonEntered, double latPoi, double lonPoi) {
        double EARTH_RADIUS_M = 6_371_000.0;

        double dLat = Math.toRadians(latEntered - latPoi);
        double dLon = Math.toRadians(lonEntered - lonPoi);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latEntered)) * Math.cos(Math.toRadians(latPoi))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * EARTH_RADIUS_M * Math.asin(Math.sqrt(a));
    }

    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 4.
     * */
    public List<String> listCsvFiles() {
        File folder = new File("data/S1T3");
        String[] names = folder.list((dir, name) -> name.endsWith(".csv"));

        return names == null ? List.of() : Arrays.asList(names);
    }
    public void loadFileToModel(String fileName, PoiModel model) {
        if (fileName != null) {
            // id;name;description;lat;lon;address;created_at;updated_at
            try (BufferedReader br = new BufferedReader(new FileReader("data/S1T3/" + fileName))) {
                String line;
                br.readLine(); // Skips header
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(";", -1);
                    if (split.length != 8)
                        continue;

                    String uuid = split[0];
                    String name = split[1];
                    String description = split[2];
                    double lat = Double.parseDouble(split[3]);
                    double lon = Double.parseDouble(split[4]);
                    String address = split[5];
                    LocalDateTime createdAt = LocalDateTime.parse(split[6]);
                    LocalDateTime updatedAt = LocalDateTime.parse(split[7]);

                    Poi poi = new Poi()
                            .setId(uuid)
                            .setName(name)
                            .setDescription(description)
                            .setLatitude(lat)
                            .setLongitude(lon)
                            .setAddress(address)
                            .setCreatedAt(createdAt)
                            .setUpdatedAt(updatedAt);

                    model.insert(poi);
                }
            } catch (IOException ioex) {
                System.out.println(ioex.getMessage());
            }
        }
    }
}
