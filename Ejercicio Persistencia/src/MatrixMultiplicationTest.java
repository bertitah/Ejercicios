import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class MatrixMultiplicationTest {

    @Test
    public void testMatrixMultiplication() {
        // Create a temporary directory for test files
        File tempDir = new File("tempDir");
        tempDir.mkdir();

        try {
            // Create matrix1.txt
            File matrix1File = new File(tempDir, "matrix1.txt");
            BufferedWriter matrix1Writer = new BufferedWriter(new FileWriter(matrix1File));
            matrix1Writer.write("2\n");
            matrix1Writer.write("3\n");
            matrix1Writer.write("1 2 3\n");
            matrix1Writer.write("4 5 6\n");
            matrix1Writer.close();

            // Create matrix2.txt
            File matrix2File = new File(tempDir, "matrix2.txt");
            BufferedWriter matrix2Writer = new BufferedWriter(new FileWriter(matrix2File));
            matrix2Writer.write("3\n");
            matrix2Writer.write("2\n");
            matrix2Writer.write("7 8\n");
            matrix2Writer.write("9 10\n");
            matrix2Writer.write("11 12\n");
            matrix2Writer.close();

            // Run the matrix multiplication application
            int[][] matrix1 = MatrixMultiplication.readMatrixFromFile(matrix1File.getAbsolutePath());
            int[][] matrix2 = MatrixMultiplication.readMatrixFromFile(matrix2File.getAbsolutePath());
            int[][] result = MatrixMultiplication.multiplyMatrices(matrix1, matrix2);

            // Verify the multiplication result
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.length);
            Assertions.assertEquals(2, result[0].length);
            Assertions.assertEquals(58, result[0][0]);
            Assertions.assertEquals(64, result[0][1]);
            Assertions.assertEquals(139, result[1][0]);
            Assertions.assertEquals(154, result[1][1]);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Delete temporary files and directory
            deleteFile(tempDir);
            deleteFile(new File("matrix1.txt"));
            deleteFile(new File("matrix2.txt"));
        }
    }

    private void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
