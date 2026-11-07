package poiApp.model;

import poiApp.model.adapter.TreeAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class represents Model layer for managing POI storage.
 *
 * @author Juraj Nižnanský
 * */
public class PoiModel {
    private final TreeAdapter<Poi> tree;
    public PoiModel(TreeAdapter<Poi> tree) {
        this.tree = tree;
    }

    /**
     * Converts Poi into PoiDto that is used in views.
     *
     * @param poi the Poi to be converted.
     * @return PoiDto converted Poi.
     * */
    private PoiDto convertToDto(Poi poi) {
        return new PoiDto(
                poi.getId(),
                poi.getName(),
                poi.getDescription(),
                poi.getAddress(),
                poi.getLatitude(),
                poi.getLongitude(),
                poi.getCreatedAt(),
                poi.getUpdatedAt()
        );
    }
    /**
     * Removes diacritics and makes string lower cased.
     *
     * @param s string to be normalized.
     * @return String lower cased and without diacritics.
     * */
    private String removeDiacriticsAndLowerCase(String s) {
        if (s == null)
            return null;

        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }

    /**
     * Inserts Poi into tree.
     *
     * @param value Poi to be inserted.
     * @return true if the operation was successfull.
     * */
    public boolean insert(Poi value) {
        double[] keys = new double[]{value.getLatitude(), value.getLongitude()};
        return tree.insert(keys, value);
    }
    /**
     * Finds all Pois in rectangular area that contain name and converts them to PoiDtos for GUI.
     *
     * @param minKeys minimal values of area.
     * @param maxKeys maximal values of area.
     * @param name to check if found Pois contains it.
     * @return List<PoiDto> found list of PoiDtos.
     * */
    public List<PoiDto> findName(double[] minKeys, double[] maxKeys, String name) {
        return tree.find(minKeys, maxKeys).stream()
                .filter(e ->
                    !e.isDeleted() &&
                    removeDiacriticsAndLowerCase(e.getName()).contains(removeDiacriticsAndLowerCase(name))
                )
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Finds all Pois in rectagular area and converts them to PoiDtos for GUI.
     *
     * @param minKeys minimal values of area.
     * @param maxKeys maximal values of area.
     * @return List<PoiDtos> found list of PoiDtos.
     * */
    public List<PoiDto> findDtos(double[] minKeys, double[] maxKeys) {
        return tree.find(minKeys, maxKeys).stream()
                .filter(e -> !e.isDeleted())
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Finds all Pois in the position of keys and converts them to PoiDtos for GUI.
     *
     * @param keys position
     * @return List<PoiDto> found list of PoiDtos.
     * */
    public List<PoiDto> findDto(double[] keys) {
        return tree.find(keys).stream()
                .filter(e -> !e.isDeleted())
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Finds all Pois stored in the tree. Used when saving Pois to file.
     *
     * @return List<Poi> of all Pois stored.
     * */
    public List<Poi> findAll() {
        double[] minKeys = new double[]{-90, -180};
        double[] maxKeys = new double[]{90, 180};

        return tree.find(minKeys, maxKeys);
    }
    /**
     * Marks Poi as deleted based on coresponding PoiDto.
     *
     * @param dto based on which the Poi is marked deleted.
     * */
    public void softDelete(PoiDto dto) {
        double[] keys = {dto.getLatitude(), dto.getLongitude()};
        List<Poi> pois = tree.find(keys);
        for (var p : pois) {
            if (p.getId().equals(dto.getId()) && !p.isDeleted()) {
                p.setDeleted(true);
            }
        }
    }
}
