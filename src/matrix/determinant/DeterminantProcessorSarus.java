/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix.determinant;

import matrix.MatrixException;
import matrix.Matrix;

/**
 *
 * @author crystal
 */
public class DeterminantProcessorSarus implements DeterminantProcessor {

    public static DeterminantProcessorSarus INSTANCE = new DeterminantProcessorSarus();

    private DeterminantProcessorSarus() {
        
    }

    public double getDeterminant(Matrix matrix) {
        if ( ! matrix.isQuadratic()) {
            throw new MatrixException("matrix is not quadratic");
        }
        if (matrix.getWidth() == 2) {
            return matrix.get(0, 0) * matrix.get(1, 1)
                    - matrix.get(0, 1) * matrix.get(1, 0);
        } else if (matrix.getWidth() == 3) {
            return matrix.get(0, 0) * matrix.get(1, 1) * matrix.get(2, 2)
                    + matrix.get(1, 0) * matrix.get(2, 1) * matrix.get(0, 2)
                    + matrix.get(2, 0) *  matrix.get(0, 1) * matrix.get(1, 2)
                    - matrix.get(2, 0) * matrix.get(1, 1) * matrix.get(0, 2)
                    - matrix.get(1, 0) * matrix.get(0, 1) * matrix.get(2, 2)
                    - matrix.get(0, 0) * matrix.get(2, 1) * matrix.get(1, 2);
        } else {
            throw new MatrixException("sarus rule can only be applied on 2x2 or 3x3 matrices");
        }
    }



}
