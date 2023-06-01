import java.io.*;

public class MatrixMultiplication {
    public static void main(String[] args) {
        int[][] matrix1 = readMatrixFromFile("matrix1.txt");
        int[][] matrix2 = readMatrixFromFile("matrix2.txt");

        if (matrix1 == null || matrix2 == null) {
            System.out.println("Error reading matrices from files.");
            return;
        }

        int[][] result = multiplyMatrices(matrix1, matrix2);

        if (result == null) {
            System.out.println("Err	or multiplying matrices.");
            return;
        }

        writeMatrixToFile(result, "output.txt");
        System.out.println("Matrix multiplication completed successfully.");
    }

    // Function to read a matrix from a text file
    public static int[][] readMatrixFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // Read the number of rows
            String numRowsStr = reader.readLine();
            if (numRowsStr == null) {
                System.out.println("Error: File is empty.");
                return null;
            }
            int numRows = parsePositiveInt(numRowsStr);
            if (numRows <= 0) {
                System.out.println("Error: Invalid number of rows.");
                return null;
            }

            // Read the number of columns
            String numColsStr = reader.readLine();
            if (numColsStr == null) {
                System.out.println("Error: Incomplete matrix structure.");
                return null;
            }
            int numCols = parsePositiveInt(numColsStr);
            if (numCols <= 0) {
                System.out.println("Error: Invalid number of columns.");
                return null;
            }

            // Create the matrix
            int[][] matrix = new int[numRows][numCols];

            // Read the matrix elements
            for (int i = 0; i < numRows; i++) {
                String line = reader.readLine();
                if (line == null) {
                    System.out.println("Error: Incomplete matrix format.");
                    return null;
                }
                String[] values = line.split(" ");
                if (values.length != numCols) {
                    System.out.println("Error: Invalid number of elements in a row.");
                    return null;
                }
                for (int j = 0; j < numCols; j++) {
                    matrix[i][j] = parseInteger(values[j]);
                }
            }

            reader.close();
            return matrix;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error: Failed to read file.");
        }

        return null;
    }

    // Function to multiply two matrices
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int m1Rows = matrix1.length;
        int m1Cols = matrix1[0].length;
        int m2Rows = matrix2.length;
        int m2Cols = matrix2[0].length;

        if (m1Cols != m2Rows) {
            System.out.println("Error: Matrices cannot be multiplied.");
            return null;
        }

        int[][] result = new int[m1Rows][m2Cols];

        for (int i = 0; i < m1Rows; i++) {
            for (int j = 0; j < m2Cols; j++) {
                for (int k = 0; k < m1Cols; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    // Function to write a matrix to a text file
    public static void writeMatrixToFile(int[][] matrix, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            int numRows = matrix.length;
            int numCols = matrix[0].length;

            writer.write(parsePositiveInt(Integer.toString(numRows)) + "\n");
            writer.write(parsePositiveInt(Integer.toString(numCols)) + "\n");

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    writer.write(parseInteger(Integer.toString(matrix[i][j])) + " ");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int parsePositiveInt(String str) {
        try {
            long num = Long.parseLong(str);
            if (num <= 0 || num > Integer.MAX_VALUE) {
                return -1;  // Invalid negative, zero, or exceeding integer limit
            }
            return (int) num;
        } catch (NumberFormatException e) {
            return -1;  // Invalid non-integer format
        }
    }

    private static int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;  // Invalid non-integer format
        }
    }

}
