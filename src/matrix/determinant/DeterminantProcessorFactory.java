/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix.determinant;

import matrix.*;

/**
 *
 * @author crystal
 */
public class DeterminantProcessorFactory {

    public static DeterminantProcessor getDeterminantProcessor(Matrix m) {
        if ( ! m.isQuadratic())
            throw new MatrixException("only quadratic matrices have determinant");

        int size = m.getWidth();
        if (size == 2 || size == 3)
            return DeterminantProcessorSarus.INSTANCE;

        return DeterminantProcessorExposition.INSTANCE;
    }

}
