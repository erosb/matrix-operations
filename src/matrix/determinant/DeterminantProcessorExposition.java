/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix.determinant;

import matrix.*;
/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class DeterminantProcessorExposition implements DeterminantProcessor {

    public static DeterminantProcessorExposition INSTANCE = new DeterminantProcessorExposition();

    private DeterminantProcessorExposition() {
        
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
            return exposeByColumn(m, maxZeroCountColumn);
        } else {
            return exposeByRow(m, maxZeroCountRow);
        }

    }

    private double exposeByColumn(Matrix m, int maxZeroCountColumn) {
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

    private double exposeByRow(Matrix m, int maxZeroCountRow) {
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
