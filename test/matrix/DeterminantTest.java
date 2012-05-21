/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import matrix.determinant.DeterminantProcessorFactory;
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
public class DeterminantTest {

    @Test
    public void testSarus() {
        Matrix m = PrimitiveMatrix.create(new double[][]{
            {1, 3},
            {2, 4}
        });
        double det = DeterminantProcessorFactory
                .getDeterminantProcessor(m)
                .getDeterminant(m);
        assertEquals(-2, det, 0);

        m = PrimitiveMatrix.create(new double[][]{
            {3, 1, 5},
            {2, 1, 2},
            {4, 1, 3}
        });
        det = DeterminantProcessorFactory
                .getDeterminantProcessor(m)
                .getDeterminant(m);
        assertEquals(-5, det, 0);
    }

    @Test
    public void testExposition() {
        Matrix m = PrimitiveMatrix.create(new double[][]{
            {1, 0, 1, 0},
            {3, 1, 0, 2},
            {4, 4, 3, 3},
            {1, 2, 1, 0},
        });
        double det = DeterminantProcessorFactory
                .getDeterminantProcessor(m)
                .getDeterminant(m);
        assertEquals(-14, det, 0);
    }

}