package matrix;

/**
 * Exception class, its instances are thrown are Matrix interface implementations'
 * get() and set() methods if the given coordinates are out of the bounds of
 * the matrix.
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
