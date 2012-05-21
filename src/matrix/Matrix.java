package matrix;


/**
 * Interface defining the required matrix methods regardless to the
 * underlying matrix representation.
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public interface Matrix extends Cloneable {

    /**
     * Determines if the matrix is quadratic or not (ie. its width
     * and height values are equal).
     *
     * @return
     */
    public boolean isQuadratic();

    /**
     * Queries the width (number of columns) of the matrix.
     *
     * @return
     */
    public int getWidth();

    /**
     * Queries the height (number of rows) of the matrix.
     *
     * @return
     */
    public int getHeight();

    /**
     * Returns the matrix item at position (i, j).
     *
     * @param i
     * @param j
     * @return
     */
    public double get(int i, int j);

    /**
     * Sets the matrix item value at position (i, j).
     * 
     * @param i
     * @param j
     * @param val
     */
    public void set(int i, int j, double val);

    /**
     * Creates a deep copy of the matrix.
     * 
     * @return
     */
    public Matrix clone();

}
