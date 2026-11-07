package dataStructures.common;

/**
 * Interface contains comparing method.
 *
 * @author Juraj Nižnanský
 * */
public interface Comparable {
    /**
     * Compares this with other.
     *
     * @param other the object to be compared.
     * @return -1 this is less than, 0 this is equal to or 1 this is greater than other.
     * */
    int compare(Comparable other);
}
