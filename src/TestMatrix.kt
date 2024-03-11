import org.junit.Test
import org.junit.Assert.*
import org.testng.annotations.BeforeTest

/**
 * A class to test the functionality of the methods in the matrix class.
 */
class TestMatrix {

    /**
     * Initialize an instance of the matrix class to use in tests
     */
    @get:BeforeTest
    val matrix = Matrix(2, 2, 0)

    /**
     * Test that set element works as expected and sets the correct element at the correct
     * spot to the designated value.
     */
    @Test
    fun testSetElement() {
        matrix.setElement(0, 0, 1)
        assertEquals("Expect first value to be one after setting the element", matrix.getThis()[0][0],1)
    }

    /**
     * Test whether getElement retrieves the correct element in the matrix attribute of the Matrix class.
     */
    @Test
    fun testGetElement() {
        assertEquals("Expect getting the first element in the matrix to equal the first element in the matrix", matrix.getElement(0,0),matrix.getThis()[0][0])
    }

    /**
     * See whether the tradMultiplication method
     * correctly computes the product between the matrix attribute of the
     * Matrix class and a second matrix. Test that the method works
     * when the matrices are both square matrices and when the second matrix in the product
     * is not square.
     */
    @Test
    fun testTradMultiplication() {
        matrix.setElement(0, 0, 1)
        matrix.setElement(0, 1, 2)
        matrix.setElement(1, 0, 3)
        matrix.setElement(1, 1, 4)
        val matrix2 = mutableListOf(mutableListOf(2, 3), mutableListOf(8, 9))
        val expectedResult1 = mutableListOf(mutableListOf(18, 21), mutableListOf(38,45))
        val matrix3 = mutableListOf(mutableListOf(5), mutableListOf(9))
        val expectedResult2 = mutableListOf(mutableListOf(23), mutableListOf(51))
        assertEquals("Expect traditional matrix multiplication to produce the correct result", expectedResult1, matrix.tradMultiplication(matrix2))
        assertEquals("Expect traditional matrix multiplication to work with matrices with compatible but different shapes", expectedResult2, matrix.tradMultiplication(matrix3))

    }

    /**
     * See whether the tradMultiplication method
     * correctly computes the product between two matrices. Test that the method works
     * when the matrices are both square matrices and when the second matrix in the product
     * is not square.
     */
    @Test
    fun testTradMultiplicationDiffMatrix() {
        val matrix1 = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4))
        val matrix2 = mutableListOf(mutableListOf(2, 3), mutableListOf(8, 9))
        val expectedResult1 = mutableListOf(mutableListOf(18, 21), mutableListOf(38,45))
        val matrix3 = mutableListOf(mutableListOf(5), mutableListOf(9))
        val expectedResult2 = mutableListOf(mutableListOf(23), mutableListOf(51))
        assertEquals("Expect traditional matrix multiplication to produce the correct result", expectedResult1, matrix.tradMultiplicationDiffMatrix(matrix1,matrix2))
        assertEquals("Expect traditional matrix multiplication to work with matrices with compatible but different shapes", expectedResult2, matrix.tradMultiplicationDiffMatrix(matrix1,matrix3))
    }

    /**
     * Ensure the getSubMatrix gets the right subcomponent of an original matrix.
     */
    @Test
    fun getSubMatrix() {
        val matrix1 = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4))
        //End-exclusive indexing
        val submatrix1 = matrix.getSubmatrix(matrix1, 0, 2, 0, 1)
        assertEquals("Submatrix1 should just include the first column of matrix1", mutableListOf(mutableListOf(1), mutableListOf(3)), submatrix1)
    }

    /**
     * Test whether addMatrices correctly calculates the sum
     * of two square matrices.
     */
    @Test
    fun testAddMatrices() {
        val matrix1 = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4))
        val matrix2 = mutableListOf(mutableListOf(2, 3), mutableListOf(8, 9))
        val expectedResult1 = mutableListOf(mutableListOf(3, 5), mutableListOf(11,13))
        assertEquals("addMatrices yields the correct result when adding two square matrices together",expectedResult1, matrix.addMatrices(matrix1, matrix2))
    }

    /**
     * Test whether subtractMatrices calculates the correct difference
     * between two square matrices.
     */
    @Test
    fun testSubtractMatrices() {
        val matrix1 = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4))
        val matrix2 = mutableListOf(mutableListOf(2, 3), mutableListOf(8, 9))
        val expectedResult1 = mutableListOf(mutableListOf(-1, -1), mutableListOf(-5,-5))
        assertEquals("addMatrices yields the correct result when adding two square matrices together",expectedResult1, matrix.subtractMatrices(matrix1, matrix2))
    }

    /**
     * Test whether Strassen's matrix multiplication computes the correct product
     * of both square and non-square matrices.
     */
    @Test
    fun testStrassens() {
        val matrix1 = mutableListOf(mutableListOf(1, 2), mutableListOf(3, 4))
        val matrix2 = mutableListOf(mutableListOf(2, 3), mutableListOf(8, 9))
        val expectedResult1 = mutableListOf(mutableListOf(18, 21), mutableListOf(38,45))
        val matrix3 = mutableListOf(mutableListOf(5), mutableListOf(9))
        val expectedResult2 = mutableListOf(mutableListOf(23), mutableListOf(51))
        assertEquals("Expect strassens matrix multiplication to produce the correct result", expectedResult1, matrix.strassen(matrix1,matrix2))
        assertEquals("Expect strassens matrix multiplication to work with matrices with compatible but different shapes", expectedResult2, matrix.strassen(matrix1,matrix3))
    }

    /**
     * Ensure that the Strassen's multiplication method correctly finds
     * the product of square matrices.
     */
    @Test
    fun testStrassensMultiplication() {
        val matrix1 = mutableListOf(mutableListOf(1, 2, 3, 4), mutableListOf(3, 4, 5, 6), mutableListOf(5, 6, 7, 8), mutableListOf(7, 8, 9, 10))
        val matrix2 = mutableListOf(mutableListOf(2, 3, 5, 6), mutableListOf(8, 9, 7, 10), mutableListOf(2, 6, 8, 4), mutableListOf(4, 3, 2, 1))
        val expectedResult1 = mutableListOf(mutableListOf(40, 51, 51, 42), mutableListOf(72, 93, 95,84), mutableListOf(104, 135, 139, 126), mutableListOf(136,177,183,168))
        assertEquals("Expect strassens matrix multiplication to produce the correct result", expectedResult1, matrix.strassensMultiplication(matrix1,matrix2))
    }

    /**
     * Test that max returns the max of three values.
     */
    @Test
    fun testMax() {
        assertEquals("Max returns the maximum value", 7, matrix.max(-10, 5, 7))
        assertEquals("Max returns the maximum value", 10, matrix.max(10, 5, 7))
    }

    /**
     * Ensure Needleman Wunsch performs accurate sequence alignment
     */
    @Test
    fun testNeedlemanWunsch() {
        val sequence1 = "CACATA"
        val sequence2 = "CAGCTA"
        assertEquals("Needlman Wunsch algorithm returns the correct sequence alignment", mutableListOf(mutableListOf("C", "A", "-", "C", "A", "T", "A"), mutableListOf("C", "A", "G", "C", "-", "T", "A")), matrix.needlemanWunsch(sequence1, sequence2))
    }

    /**
     * Test whether getGapPenalty computes the correct gap penalty
     * for the Needleman Wunsch sequence alignment algorithm or not.
     */
    @Test
    fun testGetGapPenalty() {
        val penalty: Int = 1
        assertEquals("Ensure gap penalty is calculated correctly", penalty, matrix.getGapPenalty(-1))
    }

    /**
     * Ensure paddedMatrix pads to the correct dimension
     */
    @Test
    fun testPadMatrix() {
        var originalMatrix = mutableListOf(mutableListOf(1, 2))
        var paddedMatrix =  mutableListOf(mutableListOf(1, 2), mutableListOf(0, 0))
        assertEquals("Ensure that padMatrix pads a matrix to the specified dimensions", paddedMatrix, matrix.padMatrix(2, originalMatrix))
        originalMatrix = mutableListOf(mutableListOf(1), mutableListOf(2))
        paddedMatrix =  mutableListOf(mutableListOf(1, 0), mutableListOf(2, 0))
        assertEquals("Ensure that padMatrix pads a matrix to the correct dimensions", paddedMatrix, matrix.padMatrix(2, originalMatrix))
    }

    /**
     * Test whether adjustSize pads non-square matrices to the correct
     * dimensionality.
     */
    @Test
    fun testAdjustSize() {
        var originalMatrix = mutableListOf(mutableListOf(1, 2))
        val paddedMatrix = mutableListOf(mutableListOf(1, 2), mutableListOf(0, 0))
        assertEquals("Ensure adjust size expands smaller dimension to size of larger dimension", paddedMatrix, matrix.adjustSize(originalMatrix))
    }

    /**
     * Test whether reshape matrix can take a padded matrix and put it back into the original matrix shape.
     */
    @Test
    fun testReshapeMatrix() {
        val paddedMatrix = mutableListOf(mutableListOf(1, 2), mutableListOf(0, 0))
        val originalMatrix = mutableListOf(mutableListOf(1, 2))
        assertEquals("Ensure paddedMatrix can be reshaped back to original size", originalMatrix, matrix.reshapeMatrix(paddedMatrix, 1, 2))
    }

    /**
     * Test whether getThis() returns the matrix attribute associated with an
     * instance of the Matrix class.
     */
    @Test
    fun testGetThis() {
        val matrixDesired = mutableListOf(mutableListOf(0, 0), mutableListOf(0, 0))
        assertEquals("Ensure the getThis method returns the matrix attribute of the Matrix class instance.", matrixDesired, matrix.getThis())
    }

}