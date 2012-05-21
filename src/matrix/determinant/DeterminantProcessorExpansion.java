package matrix.determinant;

import matrix.*;
/**
 * A matrix determinant calculator implementing expansion by minors.
 *
 * @see http://www.intmath.com/matrices-determinants/1-determinants.php
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class DeterminantProcessorExpansion implements DeterminantProcessor {

    /**
     * Singleton instance.
     */
    public static DeterminantProcessorExpansion INSTANCE = new DeterminantProcessorExpansion();

    /**
     * The singleton instance must be used, the constructor is made private to
     * forbid further instantiations.
     */
    private DeterminantProcessorExpansion() {
        
    }

    public double getDeterminant(Matrix m) {
        
        int[] zeroCountPerColumns = new int[m.getWidth()];
        int[] zeroCountPerRows = new int[m.getHeight()];
        int maxZeroCountColumn = 0;
        int maxZeroCountRow = 0;
        for(int i = 0; i < m.getWidth(); ++i) {
            for (int j = 0; j < m.getHeight(); ++j) {
                double val = m.get(i, j);
                if (val == 0) {
                    zeroCountPerColumns[i]++;
                    zeroCountPerRows[j]++;
                    if (zeroCountPerColumns[i] > zeroCountPerColumns[maxZeroCountColumn]) {
                        maxZeroCountColumn = i;
                    }
                    if (zeroCountPerRows[i] > zeroCountPerRows[maxZeroCountRow]) {
                        maxZeroCountRow = i;
                    }
                }
            }
        }
        if (maxZeroCountColumn > maxZeroCountRow) {
            return expandByColumn(m, maxZeroCountColumn);
        } else {
            return expandByRow(m, maxZeroCountRow);
        }

    }

    private double expandByColumn(Matrix m, int maxZeroCountColumn) {
        double rval = 0;
        int i = maxZeroCountColumn;
        for (int j = 0; j < m.getHeight(); ++j) {
            double val = m.get(i, j);
            if (val == 0)
                continue;
            Matrix submatrix = MatrixOperations.removeRowCol(m, i, j);
            rval += Math.pow(-1, i + j) * val * DeterminantProcessorFactory.getDeterminantProcessor(submatrix)
                    .getDeterminant(submatrix);
        }
        return rval;
    }

    private double expandByRow(Matrix m, int maxZeroCountRow) {
        double rval = 0;
        int j = maxZeroCountRow;
        for (int i = 0; i < m.getWidth(); ++i) {
            double val = m.get(i, j);
            if (val == 0)
                continue;
            Matrix submatrix = MatrixOperations.removeRowCol(m, i, j);
            rval += Math.pow(-1, i + j) * val * DeterminantProcessorFactory.getDeterminantProcessor(submatrix)
                    .getDeterminant(submatrix);
        }
        return rval;
    }

}
