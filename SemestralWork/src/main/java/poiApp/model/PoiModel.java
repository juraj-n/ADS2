package poiApp.model;

import poiApp.model.adapter.TreeAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoiModel {
    private final TreeAdapter<Poi> tree;
    public PoiModel(TreeAdapter<Poi> tree) {
        this.tree = tree;
    }

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
    private String removeDiacriticsAndLowerCase(String s) {
        if (s == null)
            return null;

        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }

    public boolean insert(Poi value) {
        double[] keys = new double[]{value.getLatitude(), value.getLongitude()};
        return tree.insert(keys, value);
    }
    public List<PoiDto> findName(double[] minKeys, double[] maxKeys, String name) {
        return tree.find(minKeys, maxKeys).stream()
                .filter(e ->
                    !e.isDeleted() &&
                    removeDiacriticsAndLowerCase(e.getName()).contains(removeDiacriticsAndLowerCase(name))
                )
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public List<PoiDto> findDtos(double[] minKeys, double[] maxKeys) {
        return tree.find(minKeys, maxKeys).stream()
                .filter(e -> !e.isDeleted())
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public List<PoiDto> findDto(double[] keys) {
        return tree.find(keys).stream()
                .filter(e -> !e.isDeleted())
                .map(this::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public List<Poi> findAll() {
        double[] minKeys = new double[]{-90, -180};
        double[] maxKeys = new double[]{90, 180};

        return tree.find(minKeys, maxKeys);
    }
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
