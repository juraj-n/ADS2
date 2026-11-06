package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiDto;
import poiApp.model.PoiModel;
import poiApp.model.adapter.KDTreeAdapter;
import poiApp.model.adapter.QuadKDTreeAdapter;
import poiApp.model.adapter.QuadTreeAdapter;
import poiApp.model.adapter.TreeAdapter;
import poiApp.view.dialogs.*;
import poiApp.view.views.MainView;
import poiApp.view.views.SelectFileView;
import poiApp.view.views.SelectTreeView;
import poiApp.view.PoiAppWindow;

import javax.swing.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PoiAppPresenter {
    private PoiModel model;
    private final PoiAppWindow window;
    private List<PoiDto> currentDtos;
    public PoiAppPresenter() {
        this.window = new PoiAppWindow();
        this.showSelectTreeView();
    }

    // Other
    private void setView(JPanel panel) {
        this.window.setContent(panel);
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 5.
     * */
    private double haversine(double latEntered, double lonEntered, double latPoi, double lonPoi) {
        double EARTH_RADIUS_M = 6_371_000.0;

        double dLat = Math.toRadians(latEntered - latPoi);
        double dLon = Math.toRadians(lonEntered - lonPoi);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latEntered)) * Math.cos(Math.toRadians(latPoi))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * EARTH_RADIUS_M * Math.asin(Math.sqrt(a));
    }


    // Main View
    private void showMainView() {
        MainView view = new MainView();
        this.setView(view.getView());

        if (this.currentDtos != null)
            view.setPois(this.currentDtos, this::onDeleteDtoSelected, this::onEditDtoSelected);

        // Sidebar action listeners
        view.getGenerateButton().addActionListener(e -> this.onGenerateSelected());
        view.getShowAllButton().addActionListener(e -> this.onShowAllSelected());
        view.getSaveButton().addActionListener(e -> this.onSaveSelected());

        view.getSeachPosButton().addActionListener(e -> this.onSearchPosSelected());
        view.getSearchRadiusButton().addActionListener(e -> this.onSearchRadiusSelected());
        view.getCreateNewButton().addActionListener(e -> this.onCreatePoiSelected());
        view.getSearchNameButton().addActionListener(e -> this.onSearchNameSelected());
    }
    private void onDeleteDtoSelected(PoiDto dto) {
        this.currentDtos.remove(dto);

        model.softDelete(dto);
        this.showMainView();
    }
    private void onEditDtoSelected(PoiDto dto) {
        CreateOrEditPoiDialog dialog = new CreateOrEditPoiDialog(this.window.getFrame(), "Upraviť POI",dto);
        dialog.getSubmitBtn().addActionListener(e -> this.onEditFormSubmitted(
                dialog,
                dto,
                dialog.getName(),
                dialog.getDescription(),
                dialog.getAddress(),
                dialog.getLatitude(),
                dialog.getLongitude()
        ));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onEditFormSubmitted(CreateOrEditPoiDialog dialog, PoiDto dto, String name, String description, String address, String lat, String lon) {
        if (name == null || name.isBlank()) {
            dialog.showError("Názov nesmie byť prázdny!");
            return;
        }
        if (name.length() > 15) {
            dialog.showError("Názov nesmie obsahovať viac ako 15 znakov!");
            return;
        }
        if (description == null || description.isBlank()) {
            dialog.showError("Popis nesmie byť prázdny");
            return;
        }
        if (description.length() > 50) {
            dialog.showError("Popis nesmie obsahovať viac ako 50 znakov!");
            return;
        }
        if (address == null || address.isBlank()) address = "N/A";
        if (address.length() > 20) {
            dialog.showError("Adresa nesmie obsahovať viac ako 20 znakov!");
            return;
        }
        if ((lat.length() - lat.indexOf(".") - 1) > 6){
            dialog.showError("Maximálny počet desatinných miest je 6!");
            return;
        }
        if ((lon.length() - lon.indexOf(".") - 1) > 6) {
            dialog.showError("Maximálny počet desatinných miest je 6!");
            return;
        }
        double latitude, longitude;
        try {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lon);
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]!");
            return;
        }
        if (latitude < -90 || latitude > 90) {
            dialog.showError("Geo. šírka musí byť v rozmedzí <-90, 90>!");
            return;
        }
        if (longitude < -180 || longitude > 180) {
            dialog.showError("Geo. dĺžka musí byť v rozmedzí <-180, 180>!");
            return;
        }

        if (latitude != dto.getLatitude() || longitude != dto.getLongitude()) {
            model.softDelete(dto);
            Poi updated = new Poi()
                    .setId(dto.getId())
                    .setName(name)
                    .setDescription(description)
                    .setAddress(address)
                    .setLatitude(latitude)
                    .setLongitude(longitude)
                    .setCreatedAt(dto.getCreatedAt())
                    .setUpdatedAt(LocalDateTime.now());
            model.insert(updated);
        }
        PoiDto updated = new PoiDto(dto.getId(), name, description, address, latitude, longitude, dto.getCreatedAt(), LocalDateTime.now());
        int index = this.currentDtos.indexOf(dto);
        this.currentDtos.set(index, updated);

        dialog.close();
        this.showMainView();
    }
    private void onGenerateSelected() {
        GenerateDialog dialog = new GenerateDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onGenerateFormSubmitted(dialog, dialog.getCount()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onGenerateFormSubmitted(GenerateDialog dialog, String count) {
        try {
            int num = Integer.parseInt(count);
            String[] descs = {"Prameň", "Národný park", "Vodné dielo", "Pohorie", "Gejzír"};
            String[] adds = {"Veľká Fatra", "Malá Fatra", "Vysoké Tatry", "Nízke Tatry", "Nedá sa určiť"};
            Random generator = new Random();
            for (int i = 0; i < num; i++) {
                String id = String.valueOf(UUID.randomUUID());
                String name = i + ".Úkaz";
                String description = descs[generator.nextInt(descs.length)];

                long lat = generator.nextLong(-90_000_000L, 90_000_000L);
                double latitude = lat / 1_000_000.0;
                long lon = generator.nextLong(-180_000_000L, 180_000_000L);
                double longitude = lon / 1_000_000.0;

                String address = adds[generator.nextInt(adds.length)];
                LocalDateTime createdAt = LocalDateTime.now();
                LocalDateTime updatedAt = LocalDateTime.now();

                Poi poi = new Poi()
                        .setId(id)
                        .setName(name)
                        .setDescription(description)
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .setAddress(address)
                        .setCreatedAt(createdAt)
                        .setUpdatedAt(updatedAt);

                model.insert(poi);
            }
        } catch (NumberFormatException nfex) {
            dialog.showError("Počet hodnôt musí byť číslo!");
        }
        dialog.close();
    }
    private void onShowAllSelected() {
        this.currentDtos = model.findDtos(new double[]{-90.0, -180.0}, new double[]{90.0, 180.0});
        this.showMainView();
    }
    private void onSaveSelected() {
        SaveDialog dialog = new SaveDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSaveFormSubmitted(dialog, dialog.getName()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onSaveFormSubmitted(SaveDialog dialog, String fileName) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        if (fileName == null || fileName.isBlank()) {
            dialog.showError("Názov súboru nesmie byť prázdny!");
            return;
        }
        if (fileName.length() > 30) {
            dialog.showError("Názov nesmie obsahovať viac ako 30 znakov!");
            return;
        }
        fileName = fileName + "-" + dateTime;

        List<Poi> all = model.findAll();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/S1T3/" + fileName + ".csv"))) {
            bw.write("id;name;description;lat;lon;address;created_at;updated_at");
            bw.newLine();
            for (var poi : all) {
                if (!poi.isDeleted()) {
                    bw.write(poi.getId() != null ? poi.getId() : "");
                    bw.write(";");
                    bw.write(poi.getName() != null ? poi.getName() : "");
                    bw.write(";");
                    bw.write(poi.getDescription() != null ? poi.getDescription() : "");
                    bw.write(";");
                    bw.write(String.valueOf(poi.getLatitude()));
                    bw.write(";");
                    bw.write(String.valueOf(poi.getLongitude()));
                    bw.write(";");
                    bw.write(poi.getAddress() != null ? poi.getAddress() : "");
                    bw.write(";");
                    bw.write(poi.getCreatedAt() != null ? poi.getCreatedAt().toString() : "");
                    bw.write(";");
                    bw.write(poi.getUpdatedAt() != null ? poi.getUpdatedAt().toString() : "");

                    bw.newLine();
                }
            }
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        dialog.close();
    }
    private void onSearchPosSelected() {
        SearchPosDialog dialog = new SearchPosDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchPosFormSubmitted(
                dialog,
                dialog.getLatitude(),
                dialog.getLongitude()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onSearchPosFormSubmitted(SearchPosDialog dialog, String latitude, String longitude) {
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            this.currentDtos = model.findDto(new double[]{lat, lon});
            dialog.close();
            this.showMainView();
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]");
        }
    }
    private void onSearchRadiusSelected() {
        SearchRadiusDialog dialog = new SearchRadiusDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchRadiusFormSubmitted(
                dialog,
                dialog.getLatitude(),
                dialog.getLongitude(),
                dialog.getRadius()
        ));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 5.
     * */
    private void onSearchRadiusFormSubmitted(SearchRadiusDialog dialog, String lat, String lon, String radius) {
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            double r = Double.parseDouble(radius);

            double deltaLat = Math.toDegrees(r / 6_371_000.0);
            double deltaLon = Math.toDegrees(r / (6_371_000.0 * Math.cos(Math.toRadians(latitude))));

            double latMin = latitude - deltaLat;
            double latMax = latitude + deltaLat;
            double lonMin = longitude - deltaLon;
            double lonMax = longitude + deltaLon;

            boolean pole = latMax >= 90 || latMin <= -90;
            latMin = Math.max(latMin, -90);
            latMax = Math.min(latMax, 90);
            if (pole) {
                lonMin = -180;
                lonMax = 180;
            }

            List<PoiDto> poisInSquare = new ArrayList<>();
            if (lonMin < -180) {
                poisInSquare.addAll(model.findDtos(new double[]{latMin, lonMin + 360}, new double[]{latMax, 180}));
                poisInSquare.addAll(model.findDtos(new double[]{latMin, -180}, new double[]{latMax, lonMax}));
            } else if (lonMax > 180) {
                poisInSquare.addAll(model.findDtos(new double[]{latMin, lonMin}, new double[]{latMax, 180}));
                poisInSquare.addAll(model.findDtos(new double[]{latMin, -180}, new double[]{latMax, lonMax - 360}));
            } else {
                poisInSquare.addAll(model.findDtos(new double[]{latMin, lonMin}, new double[]{latMax, lonMax}));
            }

            this.currentDtos = new ArrayList<>();
            for (var poi : poisInSquare) {
                if (haversine(latitude, longitude, poi.getLatitude(), poi.getLongitude()) <= r) {
                    this.currentDtos.add(poi);
                }
            }

            dialog.close();
            this.showMainView();
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]!");
        }
    }
    private void onCreatePoiSelected() {
        CreateOrEditPoiDialog dialog = new CreateOrEditPoiDialog(window.getFrame(), "Vytvoriť Nový POI",null);
        dialog.getSubmitBtn().addActionListener(e -> this.onCreateFromSubmitted(
                dialog,
                dialog.getName(),
                dialog.getDescription(),
                dialog.getAddress(),
                dialog.getLatitude(),
                dialog.getLongitude()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onCreateFromSubmitted(CreateOrEditPoiDialog dialog, String name, String description, String address, String lat, String lon) {
        if (name == null || name.isBlank()) {
            dialog.showError("Názov nesmie byť prázdny!");
            return;
        }
        if (name.length() > 15) {
            dialog.showError("Názov nesmie obsahovať viac ako 15 znakov!");
            return;
        }
        if (description == null || description.isBlank()) {
            dialog.showError("Popis nesmie byť prázdny");
            return;
        }
        if (description.length() > 50) {
            dialog.showError("Popis nesmie obsahovať viac ako 50 znakov!");
            return;
        }
        if (address == null || address.isBlank()) address = "N/A";
        if (address.length() > 20) {
            dialog.showError("Adresa nesmie obsahovať viac ako 20 znakov!");
            return;
        }
        if ((lat.length() - lat.indexOf(".") - 1) > 6){
            dialog.showError("Maximálny počet desatinných miest je 6!");
            return;
        }
        if ((lon.length() - lon.indexOf(".") - 1) > 6) {
            dialog.showError("Maximálny počet desatinných miest je 6!");
            return;
        }
        double latitude, longitude;
        try {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lon);
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]!");
            return;
        }
        if (latitude < -90 || latitude > 90) {
            dialog.showError("Geo. šírka musí byť v rozmedzí <-90, 90>!");
            return;
        }
        if (longitude < -180 || longitude > 180) {
            dialog.showError("Geo. dĺžka musí byť v rozmedzí <-180, 180>!");
            return;
        }


        String uuid = String.valueOf(UUID.randomUUID());

        Poi poi = new Poi()
                .setId(uuid)
                .setName(name)
                .setDescription(description)
                .setAddress(address)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        model.insert(poi);

        dialog.close();
    }
    private void onSearchNameSelected() {
        SearchNameDialog dialog = new SearchNameDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchNameFormSubmitted(
                dialog,
                dialog.getName(),
                dialog.getMinLatitude(),
                dialog.getMaxLatitude(),
                dialog.getMinLongitude(),
                dialog.getMaxLongitude()
        ));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onSearchNameFormSubmitted(SearchNameDialog dialog, String name, String minLat, String maxLat, String minLon, String maxLon) {
        if (name == null || name.isBlank()) {
            dialog.showError("Názov nesmie byť prázdny!");
            return;
        }
        double minLatitude, maxLatitude, minLongitude, maxLongitude;
        try {
            minLatitude = Double.parseDouble(minLat);
            maxLatitude = Double.parseDouble(maxLat);
            minLongitude = Double.parseDouble(minLon);
            maxLongitude = Double.parseDouble(maxLon);
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]!");
            return;
        }

        double[] minKeys = {minLatitude, minLongitude};
        double[] maxKeys = {maxLatitude, maxLongitude};
        this.currentDtos = model.findName(minKeys, maxKeys, name.trim());
        dialog.close();
        this.showMainView();
    }


    // Select Tree View
    private void showSelectTreeView() {
        SelectTreeView view = new SelectTreeView();
        this.setView(view.getView());

        view.getKdButton().addActionListener(e -> this.onTreeSelected(new KDTreeAdapter()));
        view.getQuadButton().addActionListener(e -> this.onTreeSelected(new QuadTreeAdapter()));
        view.getQuadKDButton().addActionListener(e -> this.onTreeSelected(new QuadKDTreeAdapter()));
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 3.
     * */
    private void onTreeSelected(TreeAdapter<Poi> tree) {
        this.model = new PoiModel(tree);
        boolean emptyDataFolder = true;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("data/S1T3/"), "*.csv")) {
            emptyDataFolder = !stream.iterator().hasNext();
        } catch (IOException ignored) {}

        if (emptyDataFolder) {
            this.showMainView();
        } else {
            this.showSelectFileView();
        }
    }



    // Select File View
    private void showSelectFileView() {
        SelectFileView view = new SelectFileView();
        this.setView(view.getView());

        view.setFiles(this.loadCsvFiles(), this::onFileSelected);
        view.getNewButton().addActionListener(e -> this.onFileSelected(null));
    }
    private void onFileSelected(String fileToLoad) {
        if (fileToLoad != null) {
            // id;name;description;lat;lon;address;created_at;updated_at
            try (BufferedReader br = new BufferedReader(new FileReader("data/S1T3/" + fileToLoad))) {
                String line;
                br.readLine(); // Skips header
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(";", -1);
                    if (split.length != 8)
                        continue;

                    String id = split[0];
                    String name = split[1];
                    String description = split[2];
                    double lat = Double.parseDouble(split[3]);
                    double lon = Double.parseDouble(split[4]);
                    String address = split[5];
                    LocalDateTime createdAt = LocalDateTime.parse(split[6]);
                    LocalDateTime updatedAt = LocalDateTime.parse(split[7]);

                    String uuid = String.valueOf(UUID.randomUUID());

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
        this.showMainView();
    }
    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 4.
     * */
    private List<String> loadCsvFiles() {
        File folder = new File("data/S1T3");
        String[] names = folder.list((dir, name) -> name.endsWith(".csv"));

        return names == null ? List.of() : Arrays.asList(names);
    }
}
