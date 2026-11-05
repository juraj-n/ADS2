package poiApp.presenter;

import poiApp.model.Poi;
import poiApp.model.PoiDto;
import poiApp.model.PoiModel;
import poiApp.model.adapter.KDTreeAdapter;
import poiApp.model.adapter.QuadKDTreeAdapter;
import poiApp.model.adapter.QuadTreeAdapter;
import poiApp.model.adapter.TreeAdapter;
import poiApp.view.dialogs.CreatePoiDialog;
import poiApp.view.dialogs.SaveDialog;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PoiAppPresenter {
    private PoiModel model;
    private final PoiAppWindow window;
    public PoiAppPresenter() {
        this.window = new PoiAppWindow();
        this.showSelectTreeView();
    }

    // Other
    private void setView(JPanel panel) {
        this.window.setContent(panel);
    }



    // Main View
    private void showMainView(List<PoiDto> dtos) {
        MainView view = new MainView();
        this.setView(view.getView());

        view.setPois(dtos, this::onDeleteDtoSelected, this::onEditDtoSelected);
        // Sidebar action listeners
        view.getGenerateButton().addActionListener(e -> {});
        view.getShowAllButton().addActionListener(e -> this.onShowAllSelected());
        view.getSaveButton().addActionListener(e -> this.onSaveSelected());

        view.getSeachPosButton().addActionListener(e -> {});
        view.getSearchRadiusButton().addActionListener(e -> {});
        view.getCreateNewButton().addActionListener(e -> this.onCreatePoiSelected());
        view.getSearchNameButton().addActionListener(e -> {});
    }
    private void onDeleteDtoSelected(PoiDto dto) {

    }
    private void onEditDtoSelected(PoiDto dto) {

    }
    private void onShowAllSelected() {
        List<PoiDto> dtos = model.findDtos(new double[]{-90.0, -180.0}, new double[]{90.0, 180.0});
        this.showMainView(dtos);
    }
    private void onSaveSelected() {
        SaveDialog dialog = new SaveDialog(this.window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onSaveFormSubmitted(dialog, dialog.getName()));
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

    private void onCreatePoiSelected() {
        CreatePoiDialog dialog = new CreatePoiDialog(window.getFrame());
        dialog.getSubmitBtn().addActionListener(e -> this.onCreateFromSubmitted(
                dialog,
                dialog.getName(),
                dialog.getDescription(),
                dialog.getAddress(),
                dialog.getLatitude(),
                dialog.getLongitude()));
        dialog.show();
    }
    private void onCreateFromSubmitted(CreatePoiDialog dialog, String name, String description, String address, String lat, String lon) {
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
            dialog.showError("Popis nesmie obsahovať viac ako 15 znakov!");
            return;
        }
        if (address == null || address.isBlank())
            address = "N/A";
        if (address.length() > 20) {
            dialog.showError("Adresa nesmie obsahovať viac ako 20 znakov!");
            return;
        }
        double latitude, longitude;
        try {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lon);
        } catch (NumberFormatException nfex) {
            dialog.showError("Nesprávny formát [000.000000]");
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
            this.showMainView(List.of());
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
                    LocalDateTime createdAt = LocalDateTime.parse(split[6], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                    LocalDateTime updatedAt = LocalDateTime.parse(split[7], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

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
        this.showMainView(List.of());
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
