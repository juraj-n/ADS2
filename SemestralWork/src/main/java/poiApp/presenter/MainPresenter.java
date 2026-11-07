package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiDto;
import poiApp.model.PoiModel;
import poiApp.view.dialogs.*;
import poiApp.view.views.MainView;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Class represents Presenter layer of application that connects GUI to model. Used by PoiAppNavigator.
 *
 * <p> Presenter tells model which operation to execute and validates the forms.
 *
 * @author Juraj Nižnanský
 * */
public class MainPresenter {
    private final PoiAppNavigator navigator;
    private final MainView view = new MainView();
    private final PoiModel model;
    private List<PoiDto> currentDtos;

    public MainPresenter(PoiModel model, PoiAppNavigator navigator) {
        this.navigator = navigator;
        this.model = model;

        this.view.getGenerateBtn().addActionListener(e -> this.onGenerateBtn());
        this.view.getShowAllBtn().addActionListener(e -> this.onShowAllBtn());
        this.view.getSaveBtn().addActionListener(e -> this.onSaveBtn());

        this.view.getSearchPosBtn().addActionListener(e -> this.onSearchPosBtn());
        this.view.getSearchRadiusBtn().addActionListener(e -> this.onSearchRadiusBtn());
        this.view.getCreateBtn().addActionListener(e -> this.onCreateBtn());
        this.view.getSearchNameBtn().addActionListener(e -> this.onSearchNameBtn());
    }

    public JPanel getView() {
        return this.view.getView();
    }
    private void updateMainbar() {
        this.view.setPois(this.currentDtos, this::onDeleteDtoBtn, this::onEditDtoBtn);
    }

    // Mainbar Delete / Edit
    private void onDeleteDtoBtn(PoiDto dto) {
        this.currentDtos.remove(dto);

        this.model.softDelete(dto);
        this.updateMainbar();
    }
    private void onEditDtoBtn(PoiDto dto) {
        CreateOrEditPoiDialog dialog = new CreateOrEditPoiDialog(this.navigator.getFrame(), "Upraviť POI", dto);
        dialog.getSubmitBtn().addActionListener(e -> this.onEditForm(
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
    private void onEditForm(CreateOrEditPoiDialog dialog, PoiDto dto, String name, String description, String address, String lat, String lon) {
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

        this.model.softDelete(dto);
        Poi updatePoi = new Poi()
                .setId(dto.getId())
                .setName(name)
                .setDescription(description)
                .setAddress(address)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .setCreatedAt(dto.getCreatedAt())
                .setUpdatedAt(LocalDateTime.now());
        this.model.insert(updatePoi);

        PoiDto updatedDto = new PoiDto(dto.getId(), name, description, address, latitude, longitude, dto.getCreatedAt(), LocalDateTime.now());
        int index = this.currentDtos.indexOf(dto);
        this.currentDtos.set(index, updatedDto);

        dialog.close();
        this.updateMainbar();
    }


    // Topbar Buttons
    private void onGenerateBtn() {
        GenerateDialog dialog = new GenerateDialog(this.navigator.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onGenerateForm(
                dialog,
                dialog.getCount(),
                dialog.getMinLatField(),
                dialog.getMaxLatField(),
                dialog.getMinLonField(),
                dialog.getMaxLonField()
                ));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onGenerateForm(GenerateDialog dialog, String count, String minLat, String maxLat, String minLon, String maxLon) {
        double minLatitude, maxLatitude, minLongitude, maxLongitude;
        try {
            minLatitude = Double.parseDouble(minLat);
            maxLatitude = Double.parseDouble(maxLat);
            minLongitude = Double.parseDouble(minLon);
            maxLongitude = Double.parseDouble(maxLon);
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát oblasti! [123.456789]");
            return;
        }
        if (minLatitude < -90 || maxLatitude > 90) {
            dialog.showError("Geo. Šírka musí byť v rozmedzí <-90, 90>");
            return;
        }
        if (minLongitude < -180 || maxLongitude > 180) {
            dialog.showError("Geo. Dĺžka musí byť v rozmedzí <-180, 180>");
            return;
        }

        long minLatLong = Math.round(minLatitude * 1_000_000.0);
        long maxLatLong = Math.round(maxLatitude * 1_000_000.0);
        long minLonLong = Math.round(minLongitude * 1_000_000.0);
        long maxLonLong = Math.round(maxLongitude * 1_000_000.0);

        try {
            int num = Integer.parseInt(count);
            String[] descs = {"Prameň", "Národný park", "Vodné dielo", "Pohorie", "Gejzír"};
            String[] adds = {"Veľká Fatra", "Malá Fatra", "Vysoké Tatry", "Nízke Tatry", "Nedá sa určiť"};
            Random generator = new Random();

            for (int i = 0; i < num; i++) {
                String id = String.valueOf(UUID.randomUUID());
                String name = "Úkaz";
                String description = descs[generator.nextInt(descs.length)];
                long lat = generator.nextLong(minLatLong, maxLatLong);
                double latitude = lat / 1_000_000.0;
                long lon = generator.nextLong(minLonLong, maxLonLong);
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
            dialog.showError("Nesprávny formát čísla!");
            return;
        }
        dialog.close();
    }


    private void onShowAllBtn() {
        this.currentDtos = model.findDtos(new double[]{-90.0, -180.0}, new double[]{90.0, 180.0});
        this.updateMainbar();
    }


    private void onSaveBtn() {
        SaveDialog dialog = new SaveDialog(this.navigator.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSaveForm(dialog, dialog.getName()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onSaveForm(SaveDialog dialog, String fileName) {
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


    private void onSearchPosBtn() {
        SearchPosDialog dialog = new SearchPosDialog(this.navigator.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchPosForm(
                dialog,
                dialog.getLatitude(),
                dialog.getLongitude()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onSearchPosForm(SearchPosDialog dialog, String latitude, String longitude) {
        try {
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            this.currentDtos = model.findDto(new double[]{lat, lon});
            dialog.close();
            this.updateMainbar();
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]");
        }
    }


    private void onSearchRadiusBtn() {
        SearchRadiusDialog dialog = new SearchRadiusDialog(this.navigator.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchRadiusForm(
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
    private void onSearchRadiusForm(SearchRadiusDialog dialog, String lat, String lon, String radius) {
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
                if (PoiMiscMethods.haversine(latitude, longitude, poi.getLatitude(), poi.getLongitude()) <= r) {
                    this.currentDtos.add(poi);
                }
            }

            dialog.close();
            this.updateMainbar();
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [123.456789]!");
        }
    }


    private void onCreateBtn() {
        CreateOrEditPoiDialog dialog = new CreateOrEditPoiDialog(this.navigator.getFrame(), "Vytvoriť POI", null);
        dialog.getSubmitBtn().addActionListener(e -> this.onCreateForm(
                dialog,
                dialog.getName(),
                dialog.getDescription(),
                dialog.getAddress(),
                dialog.getLatitude(),
                dialog.getLongitude()));
        dialog.getCancelBtn().addActionListener(e -> dialog.close());
        dialog.show();
    }
    private void onCreateForm(CreateOrEditPoiDialog dialog, String name, String description, String address, String lat, String lon) {
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


    private void onSearchNameBtn() {
        SearchNameDialog dialog = new SearchNameDialog(this.navigator.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSearchNameForm(
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
    private void onSearchNameForm(SearchNameDialog dialog, String name, String minLat, String maxLat, String minLon, String maxLon) {
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
        this.updateMainbar();
    }
}
