/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.util.ArrayList;
import java.util.List;

public interface Matrix extends Cloneable {

    public boolean isQuadratic();

    public int getWidth();

    public int getHeight();

    public double get(int i, int j);

    public void set(int i, int j, double val);

    public Matrix clone();
    

}
