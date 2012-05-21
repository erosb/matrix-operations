/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *
 * @author crystal
 */
public class PrimitiveMatrix implements Matrix {

    private double[][] data;

    private int width;

    private int height;

    public static PrimitiveMatrix fastCreate(double[][] data) {
        return new PrimitiveMatrix(data);
    }

    public static PrimitiveMatrix create(double[][] data) {
        if (data.length == 0) {
            return new PrimitiveMatrix(data);
        }
        int len = data[0].length;
        for (int i = 1; i < data.length; ++i) {
            if (data[i].length != len)
                throw new MatrixException("the matrix can not be created because "
                        + "not all rows are the same length: " + data);
        }

        return new PrimitiveMatrix(data);
    }

    public PrimitiveMatrix(int i, int j) {
        data = new double[i][j];
        width = i;
        height = j;
    }

    private PrimitiveMatrix(double[][] data)  {
        this.data = data;
        width = data.length;
        height = data.length == 0 ? 0 : data[0].length;
    }

    public double get(int i, int j) {
        try {
            return data[i][j];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new MatrixAccessException("the size of the matrix is ("
                    + width + ", "+height
                    +") so item ("+i+", "+j+") can not be accessed;", ex);
        }
    }

    public void set(int i, int j, double val) {
        try {
            data[i][j] = val;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new MatrixAccessException("the size of the matrix is ("
                    + width + ", "+height
                    +") so item ("+i+", "+j+") can not be accessed;", ex);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isQuadratic() {
        return width == height;
    }

    public Matrix clone() {
        double[][] data = new double[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                data[i][j] = this.data[i][j];
            }
        }
        return new PrimitiveMatrix(data);
    }

    public String toString() {
        StringBuilder rval = new StringBuilder("\n");
        for (int j = 0; j < height; ++j) {
            rval.append("[");
            for (int i = 0; i < width; ++i) {
                rval.append(data[i][j]).append(" ");
            }
            rval.append("]\n");
        }
        return rval.toString();
    }



}
