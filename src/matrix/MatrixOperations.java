/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class MatrixOperations {

    public static SegmentedMatrix removeRowCol(Matrix m, int col, int row) {
        SegmentedMatrix rval = new SegmentedMatrix();
        byte segmentI = 0;
        byte segmentJ = 0;
        boolean iShiftNeeded = false;
        boolean jShiftNeeded = false;
        if (0 < col) {
            if (0 < row) {
                rval.setSegment(segmentI, segmentJ
                        , new PartialMatrix(m, 0, 0, row - 1, col - 1));
                ++segmentJ;
            }
            if (row < m.getHeight() - 1) {
                rval.setSegment(segmentI, segmentJ
                        , new PartialMatrix(m, row + 1, 0, m.getHeight() - 1, col - 1));
            }
            ++segmentI;
            segmentJ = 0;
        }


        if (col < m.getWidth() - 1) {
            if (0 < row) {
                rval.setSegment(segmentI, segmentJ
                        , new PartialMatrix(m, 0, col + 1, row - 1, m.getWidth() - 1));
                ++segmentJ;
            }
            if (row < m.getHeight() - 1) {
                rval.setSegment(segmentI, segmentJ
                        , new PartialMatrix(m, row + 1, col + 1
                            , m.getHeight() - 1, m.getWidth() - 1));
            }
        }
        return rval;
    }

    public static Matrix getInverted(Matrix m) {
        if ( ! m.isQuadratic())
            throw new MatrixException("non-quadratic matrices can not be inverted");
        Matrix inv = m.clone();
        SegmentedMatrix segMatrix = new SegmentedMatrix((short)2, (short)1);
        segMatrix.setSegment(0, 0, inv);
        segMatrix.setSegment(1, 0, getUnitMatrix(m.getWidth()));
//        System.out.println(segMatrix);
        invert(segMatrix);
        return segMatrix.getSegment(1, 0);
    }

    protected static void invert(Matrix m) {
        int size = m.getHeight();
        for (int col = 0; col < size; ++col) {
            double nuller = m.get(col, col);
            for (int row = col + 1; row < size; ++row) {
                double mul = m.get(col, row) / nuller;
                for (int i = 0; i < m.getWidth(); ++i) {
                    double curr = m.get(i, row);
                    //System.out.println("setting ["+i+","+row+"] to "+(curr - m.get(i, col) * mul)+ "---"+m.get(i, col));
                    m.set(i, row, curr - m.get(i, col) * mul);
                }
            }
        }
        //System.out.println(m);
        for (int col = size - 1; col >= 0; --col) {
            double nuller = m.get(col, col);
            if (nuller != 0) {
            for (int row = col - 1; row >= 0; --row) {
                double mul = m.get(col, row) / nuller;
                for (int i = 0; i < m.getWidth(); ++i) {
                    double curr = m.get(i, row);
                    //System.out.println("--setting ["+i+","+row+"] to "+(curr - nuller * mul));
                    m.set(i, row, curr - m.get(i, col) * mul);
                }
            }
            }
        }
//        System.out.println(m);
        for (int i = 0; i < size; ++i) {
            double div = m.get(i, i);
            if (div != 1) {
                for (int j = 0; j < m.getWidth(); ++j) {
                    //System.out.println("setting-- ["+j+","+i+"] to "+(m.get(j, i)));
                    m.set(j, i, m.get(j, i) / div);
                }
            }
        }
    }

    public static Matrix getUnitMatrix(int size) {
        PrimitiveMatrix rval = new PrimitiveMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                rval.set(i, j, i == j ? 1 : 0);
            }
        }
        return rval;
    }

    public static Matrix clone(Matrix m) {
        int width = m.getWidth();
        int height = m.getHeight();
        PrimitiveMatrix rval = new PrimitiveMatrix(m.getWidth(), m.getHeight());
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                rval.set(i, j, m.get(i, j));
            }
        }
        return rval;
    }

    public static double getCondition(Matrix m) {
        return getMaxColumnSum(m) * getMaxColumnSum(MatrixOperations.getInverted(m));
    }

    protected static double getMaxColumnSum(Matrix m) {
        double max = 0;
        double columnSum = 0;
        for (int i = 0; i < m.getWidth(); ++i) {
            columnSum = 0;
            for (int j = 0; j < m.getHeight(); ++j) {
                columnSum += Math.abs(m.get(i, j));
            }
            if (columnSum > max) {
                max = columnSum;
            }
        }
        return max;
    }

}
