package poiApp.model;

import poiApp.model.adapter.TreeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoiModel {
    private final TreeAdapter<Poi> tree;
    public PoiModel(TreeAdapter<Poi> tree) {
        this.tree = tree;
    }

    private PoiDto getPoiDtos(Poi poi) {
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

    public boolean insert(Poi value) {
        double[] keys = new double[]{value.getLatitude(), value.getLongitude()};
        return tree.insert(keys, value);
    }
    public List<PoiDto> findDtos(double[] minKeys, double[] maxKeys) {
        return tree.find(minKeys, maxKeys).stream()
                .map(this::getPoiDtos)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public List<PoiDto> findDto(double[] keys) {
        return tree.find(keys).stream()
                .map(this::getPoiDtos)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public List<Poi> findAll() {
        double[] minKeys = new double[]{-90, -180};
        double[] maxKeys = new double[]{90, 180};

        return tree.find(minKeys, maxKeys);
    }
}
