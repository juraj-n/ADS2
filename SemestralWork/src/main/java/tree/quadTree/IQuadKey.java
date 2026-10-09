package tree.quadTree;

public interface IQuadKey {
    IQuadKey subtract(IQuadKey other);
    IQuadKey half();
}
