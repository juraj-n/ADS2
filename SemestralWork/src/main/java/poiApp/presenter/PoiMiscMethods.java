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

/**
 * Class represents various helper methods used in presenter layer.
 *
 * @author Juraj Nižnanský
 * */
public class PoiMiscMethods {
    /**
     * Calculates the circle distance between two geograpich points using Haversine formula.
     *
     * @param latEntered latitude of the first point.
     * @param lonEntered longitude of the first point.
     * @param latPoi latitude of the second point (Poi).
     * @param lonPoi longitude of the second point (Poi).
     * @return the distance between the two points in meters.
     * */
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
     *
     * @return List<String> of CSV file names inside data/S1T3 folder.
     * */
    public List<String> listCsvFiles() {
        File folder = new File("saved_pois/");
        String[] names = folder.list((dir, name) -> name.endsWith(".csv"));

        return names == null ? List.of() : Arrays.asList(names);
    }
    /**
     * Loads CSV file into model. The file must be inside data/S1T3 folder.
     *
     * @param fileName the name of the file to be loaded.
     * @param model the model for data to be loaded into.
     * */
    public void loadFileToModel(String fileName, PoiModel model) {
        if (fileName != null) {
            // id;name;description;lat;lon;address;created_at;updated_at
            try (BufferedReader br = new BufferedReader(new FileReader("saved_pois/" + fileName))) {
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
