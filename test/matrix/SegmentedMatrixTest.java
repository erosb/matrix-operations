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
public class SegmentedMatrixTest {

    private SegmentedMatrix m;

    private double[][] sub00 = {
            {1,3},
            {2,4}
        };

    private double[][] sub10 = {
            {5,6},
            {7,8},
            {9,10}
        };

    private double[][] sub01 = {
            {11,12,13},
            {14,15,16}
        };

    private double[][] sub11 = {
            {17,18,19},
            {20,21,22},
            {23,24,25}
        };

    @Before
    public void setUp() {
        m = new SegmentedMatrix();
        m.setSegment(0, 0, PrimitiveMatrix.create(sub00));
        m.setSegment(0, 1, PrimitiveMatrix.create(sub01));
        m.setSegment(1, 0, PrimitiveMatrix.create(sub10));
        m.setSegment(1, 1, PrimitiveMatrix.create(sub11));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGet() {
        assertEquals(1, m.get(0, 0), 0);
        assertEquals(2, m.get(1, 0), 0);
        assertEquals(5, m.get(2, 0), 0);
        assertEquals(17, m.get(2, 2), 0);
        assertEquals(25, m.get(4, 4), 0);
        assertEquals(24, m.get(4, 3), 0);

        assertEquals(20, m.get(3, 2), 0);
        assertEquals(8, m.get(3, 1), 0);
        assertEquals(20, m.get(3, 2), 0);
        assertEquals(21, m.get(3, 3), 0);
        assertEquals(20, m.get(3, 2), 0);
        assertEquals(17, m.get(2, 2), 0);
        assertEquals(20, m.get(3, 2), 0);
        assertEquals(23, m.get(4, 2), 0);
    }

    @Test
    public void testSet() {
        // m.set(2, 4, 10);
        //assertEquals(10, sub11[0][2], 0);
    }

    @Test
    public void testGetWidthHeight() {
        assertEquals(5, m.getWidth());
        assertEquals(5, m.getHeight());
    }

    

}