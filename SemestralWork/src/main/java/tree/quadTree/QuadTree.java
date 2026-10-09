package tree.quadTree;

public class QuadTree<T> {
    private QuadNode<T> _root;
    // Bounds
    private final IQuadKey _xMin;
    private final IQuadKey _xMax;
    private final IQuadKey _yMin;
    private final IQuadKey _yMax;


    public QuadTree(IQuadKey xMin, IQuadKey xMax, IQuadKey yMin, IQuadKey yMax) {
        this._root = new QuadNode<>(xMin, xMax, yMin, yMax);

        // Bounds
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
    }

    public boolean insert(T item) {
        return false;
    }

    public T find(T item) {
        return null;
    }
}
