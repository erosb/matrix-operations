package matrix.determinant;

import matrix.*;

/**
 * Factory for obtaining determinant calculators for matrices.
 *
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class DeterminantProcessorFactory {

    /**
     * Returns a DeterminantProcessorSarus instance if the matrix's size is 2 or 3,
     * otherwise it returns a DeterminantProcessorExpansion instance.
     * 
     * @param m the matrix which's determinant must be calculated by the resulting DeterminantProcessor instance.
     * @throws MatrixException if m is not quadratic.
     * @return
     */
    public static DeterminantProcessor getDeterminantProcessor(Matrix m) {
        if ( ! m.isQuadratic())
            throw new MatrixException("only quadratic matrices have determinant");

        int size = m.getWidth();
        if (size == 2 || size == 3)
            return DeterminantProcessorSarus.INSTANCE;

        return DeterminantProcessorExpansion.INSTANCE;
    }

}
