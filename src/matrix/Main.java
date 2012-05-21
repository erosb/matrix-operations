/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import matrix.determinant.DeterminantProcessorFactory;

/**
 *
 * @author crystal
 */
public class Main {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
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

    private static void printUsage() {
        System.out.println("1: inverse (Gauss-Jordan)\n"
                + "2: determinant\n"
                + "3: condition number\n"
                + "4: exit\n\n");
    }

    private static void inverse() throws IOException {
        Matrix m = readMatrix();
        System.out.println(MatrixOperations.getInverted(m));
    }

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

    private static void determinant() throws IOException {
        Matrix m = readMatrix();
        System.out.println(DeterminantProcessorFactory.getDeterminantProcessor(m)
                .getDeterminant(m));
    }

    private static void condition() throws IOException {
        Matrix m = readMatrix();
        System.out.println(MatrixOperations.getCondition(m));
    }

}
