/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class MatrixSegmentException extends RuntimeException {

    public MatrixSegmentException(Throwable cause) {
        super(cause);
    }

    public MatrixSegmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixSegmentException(String message) {
        super(message);
    }

    public MatrixSegmentException() {
    }

    

}
