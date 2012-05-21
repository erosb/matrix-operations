package matrix.determinant;

import matrix.Matrix;

/**
 * Implementations of this interface are able to calculate the determinant
 * of a matrix. The implementing classes differ in the algorithm they use
 * for calculation.
 *
 * DeterminantProcessor instances should be obtained using
 * DeterminantProcessorFactory.getDeterminant()
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public interface DeterminantProcessor {

    public double getDeterminant(Matrix matrix);

}
