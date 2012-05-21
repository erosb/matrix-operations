package matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import matrix.determinant.DeterminantProcessorFactory;

/**
 *
 * @author Bence Erős <crystal@cyclonephp.org>
 */
public class Main {

    /**
     * Reader for standard input.
     */
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Main method of the program. In an infinite loop
     * it reads a command from standard input then reads in a matrix and
     * executes the given operation on it, then prints the resulting matrix
     * to the standard output.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        printUsage();
        for(;;) {
            int task;
            try {
                task = Integer.parseInt(in.readLine());
            } catch (NumberFormatException ex) {
                printUsage();
                continue;
            }
            if (task == 1) {
                inverse();
            }else if (task == 2) {
                determinant();
            } else if (task == 3) {
                condition();
            } else if (task == 4) {
                break;
            } else {
                printUsage();
            }


        }
        

    }

    /**
     * Prints usage information to standard output.
     */
    private static void printUsage() {
        System.out.println("1: inverse (Gauss-Jordan)\n"
                + "2: determinant\n"
                + "3: condition number\n"
                + "4: exit\n\n");
    }

    /**
     * Reads a matrix from standard input then writes its inverse
     * to standard output.
     *
     * @uses MatrixOperations.getInverted()
     * @uses readMatrix()
     * @throws IOException
     */
    private static void inverse() throws IOException {
        Matrix m = readMatrix();
        System.out.println(MatrixOperations.getInverted(m));
    }

    /**
     * Reads a matrix from the standard input.
     * The first line must contain the size of the matrix
     * (both width and height therefore this method only reads
     * quadratic matrices).
     *
     * Every further lines must contain a line of the matrix, items
     * should be separated with a space, and items must be integers.
     *
     * @return the read matrix
     * @usedby inverse()
     * @usedby condition()
     * @usedby determinant()
     * @throws IOException
     */
    private static Matrix readMatrix() throws IOException {
        int size = 0;
        do {
            try {
                size = Integer.parseInt(in.readLine());
            } catch (NumberFormatException ex) {
                continue;
            }
        } while (false);
        double[][] matrix = new double[size][size];
        for (int j = 0; j < size; ++j) {
            String[] parts = in.readLine().split(" ");
            if (parts.length != size) {
                System.out.println(size + " numbers must be passed\n");
                j--;
                continue;
            }
            for (int i = 0; i < size; ++i) {
                matrix[i][j] = Double.parseDouble(parts[i]);
            }
        }
        return PrimitiveMatrix.create(matrix);
    }

    /**
     * Reads matrix from standard input then writes its determinant
     * to standard output.
     *
     * @uses readMatrix()
     * @uses matrix.determinant.DeterminantProcessorFactory
     * @uses matrix.determinant.DeterminantProcessor.getDeterminant()
     * @throws IOException
     */
    private static void determinant() throws IOException {
        Matrix m = readMatrix();
        System.out.println(DeterminantProcessorFactory.getDeterminantProcessor(m)
                .getDeterminant(m));
    }

    /**
     * Reads matrix from standard input then writes its condition number
     * to standard output.
     *
     * @uses readMatrix()
     * @uses MatrixOperations.getCondition()
     * @throws IOException
     */
    private static void condition() throws IOException {
        Matrix m = readMatrix();
        System.out.println(MatrixOperations.getCondition(m));
    }

}
