/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class MatrixAccessException extends RuntimeException {

    public MatrixAccessException(Throwable cause) {
        super(cause);
    }

    public MatrixAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixAccessException(String message) {
        super(message);
    }

    public MatrixAccessException() {
    }

}
