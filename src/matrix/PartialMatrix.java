/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.util.*;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class PartialMatrix implements Matrix {


    private Matrix data;

    private int top;

    private int left;

    private int bottom;

    private int right;

    public PartialMatrix(Matrix data, int top, int left, int bottom, int right) {
        this.data = data;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public boolean isQuadratic() {
        return (right - left) == (bottom - top);
    }



    public double get(int i, int j) {
        i += left;
        j += top;
        checkInBounds(i, j);
        return data.get(i, j);
    }

    public void set(int i, int j, double val) {
        i += left;
        j += top;
        checkInBounds(i, j);
        data.set(i, j, val);
    }



    private void checkInBounds(int i, int j) {
        if (i < left || i > right || j < top || j > bottom)
            throw new MatrixAccessException("item ("+i+","+j+") is out of bounds");
    }

    public List<Matrix> getSubmatrices(int divI, int divJ) {
        divI += left;
        divJ += top;
        List<Matrix> rval = new ArrayList<Matrix>();
        if (left < divI) {
            if (top < divJ) {
                rval.add(new PartialMatrix(data, top, left, divJ - 1, divI - 1));
            }
            if (divJ < bottom) {
                rval.add(new PartialMatrix(data, divJ + 1, left, bottom, divI - 1));
            }
        }
        if (divI < right) {
            if (top < divJ) {
                rval.add(new PartialMatrix(data, top, divI + 1, divJ - 1, right));
            }
            if (divJ < bottom) {
                rval.add(new PartialMatrix(data, divJ + 1, divI + 1, bottom, right));
            }
        }
        return rval;
    }

    public int getWidth() {
        return right - left + 1;
    }

    public int getHeight() {
        return bottom - top + 1;
    }

    public Matrix clone() {
        return MatrixOperations.clone(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for (int j = 0; j < getHeight(); ++j) {
            str.append("[");
            for (int i = 0; i < getWidth(); ++i) {
                str.append(get(i, j)).append(" ");
            }
            str.append("]\n");
        }
        return str.toString();
    }

}
