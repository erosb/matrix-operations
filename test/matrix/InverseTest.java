/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class InverseTest {

    public InverseTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetInverse() {
        PrimitiveMatrix m = PrimitiveMatrix.create(new double[][]{
            {1, 2, 1},
            {2, 6, 1},
            {3, 11, 1}
        });
        Matrix inv = MatrixOperations.getInverted(m);
        double[][] expected = {
            {-5, 9, -4},
            {1, -2, 1},
            {4, -5, 2}
        };
        //System.out.println(inv);
        for (int i = 0; i <m.getWidth(); ++i) {
            for (int j = 0; j < m.getHeight(); ++j) {
                assertEquals(expected[i][j], inv.get(i, j), 0);
            }
        }

        m = PrimitiveMatrix.create(new double[][]{
            {1, 1, 2},
            {2, 3, 4},
            {3, -2, 7}
        });

        inv = MatrixOperations.getInverted(m);

        double[][] expected2 = {
            {29, -11, -2},
            {-2, 1, 0},
            {-13, 5, 1}
        };

        for (int i = 0; i <m.getWidth(); ++i) {
            for (int j = 0; j < m.getHeight(); ++j) {
                assertEquals(expected2[i][j], inv.get(i, j), 0);
            }
        }
    }

}