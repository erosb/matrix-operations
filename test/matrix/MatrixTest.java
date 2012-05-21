/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author crystal
 */
public class MatrixTest {

    private PartialMatrix m;

    public MatrixTest() {
    }

    @Before
    public void setUp() {
        double[][] data = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9}
        };
        m = new PartialMatrix(PrimitiveMatrix.create(data), 0, 0, 2, 2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetSet() {
        assertEquals(5, m.get(1, 1), 0);
        assertEquals(1, m.get(0, 0), 0);
        m.set(0, 0, 2);
        assertEquals(2, m.get(0, 0), 0);
    }

    @Test
    public void testGetWidthHeight() {
        assertEquals(m.getWidth(), 3, 0);
        assertEquals(m.getHeight(), 3, 0);
    }
    @Test
    public void testGetSubmatrices() {
        List<Matrix> subs = m.getSubmatrices(1, 1);
        assertEquals(4, subs.size(), 0);
        assertEquals(1, subs.get(0).getWidth(), 0);
        assertEquals(1, subs.get(1).getWidth(), 0);
        assertEquals(1, subs.get(2).getWidth(), 0);
        assertEquals(1, subs.get(3).getWidth(), 0);
        assertEquals(1, subs.get(0).get(0, 0), 0);
        assertEquals(7, subs.get(1).get(0, 0), 0);
        assertEquals(3, subs.get(2).get(0, 0), 0);
        assertEquals(9, subs.get(3).get(0, 0), 0);
        subs = m.getSubmatrices(1, 0);
        //System.out.println(subs);
        assertEquals(2, subs.size(), 0);
        assertEquals(9, subs.get(1).get(0, 1), 0);
        assertEquals(4, subs.get(0).get(0, 0), 0);
        subs = ((PartialMatrix)subs.get(0)).getSubmatrices(0, 0);
        assertEquals(0, subs.size(), 0);
        subs = m.getSubmatrices(0, 0);
        assertEquals(1, subs.size(), 0);
        assertEquals(5, subs.get(0).get(0, 0), 0);
        assertEquals(9, subs.get(0).get(1, 1), 0);
        assertEquals(6, subs.get(0).get(1, 0), 0);
    }

    

}