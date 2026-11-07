package dataStructures.common;
/**
 * Interface contains method for subtract and divide by 2 operation.
 *
 * @author Juraj Nižnanský
 * */
public interface QuadKey extends Comparable {
    /**
     * Subtracts other from this.
     *
     * @param other the object which value is subtracted.
     * @return QuadKey that represents value after subtraction.
     * */
    QuadKey subtract(QuadKey other);
    /**
     * Divide this by two.
     *
     * @return QuadKey that represents value after division.
     * */
    QuadKey half();
}
