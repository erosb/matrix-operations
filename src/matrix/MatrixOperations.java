package matrix;

/**
 * Utility class for some matrix operations.
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class MatrixOperations {

    /**
     * Removes a row and a column from a matrix. The resulting matrix's width
     * and height are the initial matrix's width - 1 and height - 1.
     *
     * No cloning will be preformed, the method will create a segmented matrix
     * consisting of 1 - 4 partial matrices based on the initial matrix.
     *
     * In general cases 4 partial matrices will be created. If at least one
     * index (row or col) marks the border of the initial matrix then only
     * 2 partial matrices will be created. If both row and col marks borders
     * of the initial matrix then the resulting segmented matrix will consist
     * of only one partial matrix.
     *
     * @param m the initial matrix
     * @param col the column index to be removed
     * @param row the row index to be removed
     * @return
     */
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

    /**
     * Returns the inverse of the matrix.
     *
     * The initial matrix is cloned, then a SegmentedMatrix is created, and
     * the cloned matrix and the same size unit matrix is added to it. The inversion
     * is performed on the SegmentedMatrix is performed, then the first segment
     * will be returned (which is actually the inverse of the initial matrix).
     *
     * @see http://www.purplemath.com/modules/mtrxinvr.htm
     * @param m
     * @return
     * @throws MatrixException if m is not quadratic.
     */
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

    /**
     * Performs the inversion algorithm on the matrix m. The initial matrix's
     * width must be its height * 2, and its second half must be a unit matrix.
     * 
     * @param m the matrix to be inverted
     */
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

    /**
     * Constructs a unit matrix with the given size. The resulting matrix
     * is quadratic, the item values in its diagonal are 1, and 0-s everywhere else.
     *
     * @param size the size (width and height) of the resulting unit matrix
     * @return
     */
    public static Matrix getUnitMatrix(int size) {
        PrimitiveMatrix rval = new PrimitiveMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                rval.set(i, j, i == j ? 1 : 0);
            }
        }
        return rval;
    }

    /**
     * Performs a deep copy of a matrix to a new PrimitiveMatrix instance.
     *
     * Iterates on the items of m and copies all items to a new matrix.
     *
     * @param m
     * @return a PrimitiveMatrix
     */
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

    /**
     * Returns the condition number of a matrix.
     *
     * @see http://en.wikipedia.org/wiki/Condition_number#Matrices
     * @param m
     * @return
     */
    public static double getCondition(Matrix m) {
        return getMaxColumnSum(m) * getMaxColumnSum(MatrixOperations.getInverted(m));
    }

    /**
     * Returns the highest column value sum.
     *
     * Performs a separate addition on each column's values then returns
     * the highest sum.
     *
     * @param m
     * @return
     */
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
