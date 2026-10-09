package tree.quadTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuadNode<T> {
    // Data
    private final List<QuadNode<T>> _sons;
    private List<T> _values;

    // Subregion Bounds
    private final IQuadKey _xMin;
    private final IQuadKey _xMax;
    private final IQuadKey _yMin;
    private final IQuadKey _yMax;


    public QuadNode(IQuadKey xMin, IQuadKey xMax, IQuadKey yMin, IQuadKey yMax) {
        // Data
        this._sons = new ArrayList<>(Collections.nCopies(4, null));
        this._values = new LinkedList<>();

        // Subregion Bounds
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
    }
}
