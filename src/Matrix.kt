import kotlin.system.measureTimeMillis

/**
 * A matrix class that contains various matrix operations.
 */
class Matrix(private val rowDim: Int, private val colDim: Int, initValue: Int) {
    private val matrix: MutableList<MutableList<Int>> = MutableList(rowDim) { MutableList(colDim) { initValue } }
    private val initialPenalty: Int = -1
    private val additionalPenalty: Int = -1

    fun setElement(row: Int, col: Int, value: Int) {
        matrix[row][col] = value
    }

    fun getElement(row: Int, col: Int): Int {
        return matrix[row][col];
    }

    /**
     * Multiply [this] by a second matrix using traditional
     * "what you learn in class" multiplication methods.
     * @param matrix2 A matrix (represented by a (MutableList<MutableList<Int>>?) to multiply [this] by.
     * @return a MutableList<MutableList<Int>>? representing the product of multiplying the two matrices.
     */
    fun tradMultiplication(matrix2: MutableList<MutableList<Int>>?): MutableList<MutableList<Int>>? {
        if (matrix[0].size != matrix2?.size) {
            return null
        }
        val finalMatrix = mutableListOf<MutableList<Int>>()
        val rowSize = matrix.size
        val colSize = matrix2[0].size
        val commonSize = matrix[0].size

        for (row in 0 until rowSize) {
            val newRow: MutableList<Int> = MutableList(colSize) {0}
            finalMatrix.add(newRow)
        }

        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                for (commonIndex in 0 until commonSize) {
                    finalMatrix[row][col] += matrix[row][commonIndex] * matrix2[commonIndex][col]
                }
            }
        }
        return finalMatrix
    }

    /**
     * Multiply two matrices using a traditional matrix multiplication method.
     * @param matrix1 A matrix (represented by a (MutableList<MutableList<Int>>?).
     * @param matrix2 A matrix (represented by a (MutableList<MutableList<Int>>?) to multiply [matrix1] by.
     * @return the product of [matrix1] and [matrix2]
     */
    fun tradMultiplicationDiffMatrix(matrix1: MutableList<MutableList<Int>>, matrix2: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        if (matrix1[0].size != matrix2.size) {
            throw IllegalArgumentException("Incompatible matrix dimensions for multiplication")
        }
        val finalMatrix = mutableListOf<MutableList<Int>>()
        val rowSize = matrix1.size // matrix row dim
        val colSize = matrix2[0].size // matrix 2 col dim
        val commonSize = matrix1[0].size // matrix col dim

        for (row in 0 until rowSize) {
            val newRow: MutableList<Int> = MutableList(colSize) { 0 }
            finalMatrix.add(newRow)
        }

        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                for (commonIndex in 0 until commonSize) {
                    finalMatrix[row][col] += matrix1[row][commonIndex] * matrix2[commonIndex][col]
                }
            }
        }
        return finalMatrix
    }

    /**
     * Return the matrix internal to the matrix class.
     * @return the matrix attribute of this class.
     */
    fun getThis(): MutableList<MutableList<Int>> {
        return matrix
    }

    /**
     * Return a submatrix of a matrix, given a row to start in, a row to end in, a column to start
     * in and a column to end in.
     * @param matrix The matrix to get a submatrix from.
     * @param startRow The row to start retrieving values from for the submatrix from the original matrix.
     * @param endRow The row to stop retrieving values from for the sybmatrix.
     * @param startCol The column to start retrieving values from for the submatrix from the original matrix.
     * @param endCol The column to stop retrieving values from for the submatrix from the original matrix.
     */
    fun getSubmatrix(matrix: MutableList<MutableList<Int>>, startRow: Int, endRow: Int, startCol: Int, endCol: Int): MutableList<MutableList<Int>> {
        val submatrix = mutableListOf<MutableList<Int>>()
        for (row in startRow until endRow) { // Use 'until' for exclusive end
            val subRow = mutableListOf<Int>()
            for (col in startCol until endCol) { // Use 'until' for exclusive end
                subRow.add(matrix[row][col])
            }
            submatrix.add(subRow)
        }
        return submatrix
    }

    /**
     * Return the sum of two matrices.
     * @param matrix1 (MutableList<MutableList<Int>>) The first matrix in the sum.
     * @param matrix2 (MutableList<MutableList<Int>>) The second matrix in the sum.
     * @return the sum (MutableList<MutableList<Int>>) of the two matrices.
     */
    fun addMatrices(matrix1: MutableList<MutableList<Int>>, matrix2: MutableList<MutableList<Int>>):MutableList<MutableList<Int>> {
        val result = MutableList(matrix1.size) { MutableList(matrix1[0].size) { 0 } }
        for (i in matrix1.indices) {
            for (j in matrix1[i].indices) {
                result[i][j] = matrix1[i][j] + matrix2[i][j]
            }
        }
        //println("result: ${result.size} by ${result[0].size} matrix1: ${matrix1.size} by ${matrix1[0].size} matrix2:  ${matrix2.size} by ${matrix2[0].size} matrix1: ")
        return result
    }

    /**
     * Calculate and return the difference between two matrices.
     * @param matrix1 (MutableList<MutableList<Int>>) A matrix.
     * @param matrix2 (MutableList<MutableList<Int>>) The matrix that gets subtracted from [matrix1].
     * @return the difference between [matrix1] and [matrix2]
     */
    fun subtractMatrices(matrix1: MutableList<MutableList<Int>>, matrix2: MutableList<MutableList<Int>>):MutableList<MutableList<Int>> {
        val result = MutableList(matrix1.size) { MutableList(matrix1[0].size) { 0 } }
        for (i in matrix1.indices) {
            for (j in matrix1[i].indices) {
                result[i][j] = matrix1[i][j] - matrix2[i][j]
            }
        }
        return result
    }

    /**
     * Pad a matrix to a certain size
     * @param toSize The size to pad the matrix to
     * @param originalMatrix The matrix to create a padded copy of
     * @return A padded version of [originalMatrix]
     */
    fun padMatrix(toSize: Int, originalMatrix: MutableList<MutableList<Int>>): MutableList<MutableList<Int>>{
        val paddedMatrix = MutableList(toSize) { MutableList(toSize) { 0 } }

        for (i in 0 until originalMatrix.size) {
            for (j in 0 until originalMatrix[0].size) {
                paddedMatrix[i][j] = originalMatrix[i][j]
            }
        }

        return paddedMatrix
    }

    /**
     * Adjust the size of a non-square matrix to match the requirement's of strassen's algorithm
     * The requirements are that the matrix is square and that the matrix dimension is a power of 2
     * If those are not the case for the non-square matrix, pad the non-square matrix to make it square
     * where the new square dimension is the larger of the two dimensions of the original matrix. However,
     * if the larger of the two dimensions is not a power of two, the dimension is doubled to ensure the
     * dimension is a power of two.
     * @param originalMatrix The non-square matrix to be padded
     * @return a version of originalMatrix padded to a square size that is a power of two.
     */
    fun adjustSize(originalMatrix: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val maxDim = maxOf(originalMatrix.size, originalMatrix[0].size)
        val newDim = Integer.highestOneBit(maxDim).let {
            if (it == maxDim) it else it shl 1 // Double it if it's not a power of 2
        }
        return padMatrix(newDim, originalMatrix)
    }

    /**
     * Reshape [matrix] back to its original, unpadded size, if matrix multiplication
     * was done with a padded matrix
     *
     */
     fun reshapeMatrix(result: MutableList<MutableList<Int>>, rowDim: Int, colDim: Int): MutableList<MutableList<Int>> {
        val properMatrix =  MutableList(rowDim) { MutableList(colDim) { 0 } }
        for (i in 0 until properMatrix.size) {
            for (j in 0 until properMatrix[0].size) {
                properMatrix[i][j] = result[i][j]
            }
        }
        return properMatrix
     }

    /**
     * Calculate the matrix multiplication using Strassen's algorithm between two matrices,
     * which are either square or not square.
     * @param matrix1 The first matrix used in the product calculation (not necessarily square).
     * @param matrix2 The second matrix used in the product calculation (not necessarily square).
     * @return the product of the two matrices.
     */
    fun strassen(matrix1: MutableList<MutableList<Int>>, matrix2: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        if (matrix1[0].size != matrix2.size) {
            throw IllegalArgumentException("Incompatible matrix dimensions for multiplication")
        }
        var firstMatrix = matrix1
        var secondMatrix = matrix2
        val rowSize = matrix1.size
        val colSize = matrix2[0].size
        if (matrix1.size != matrix1[0].size) {
            firstMatrix = adjustSize(matrix1)
        }
        if (matrix2.size != matrix2[0].size) {
            secondMatrix = adjustSize(matrix2)
        }

        // If the first matrix is no longer compatible with the second, pad the second to match its column dim
        if (matrix1[0].size != matrix2.size) {
            secondMatrix = padMatrix(matrix1[0].size, secondMatrix)
        }

        val product = strassensMultiplication(firstMatrix, secondMatrix)

        return reshapeMatrix(product, rowSize, colSize)
    }

    /**
     * Multiply two matrices using Strassen's matrix multiplication method.
     * @param matrix1 A matrix (represented by a (MutableList<MutableList<Int>>?).
     * @param matrix2 A matrix (represented by a (MutableList<MutableList<Int>>?) to multiply [matrix1] by.
     * @return the product of [matrix1] and [matrix2]
     */
    fun strassensMultiplication(matrix1: MutableList<MutableList<Int>>, matrix2: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {

        if (matrix1.size == 1) {
            return tradMultiplicationDiffMatrix(matrix1, matrix2)
        }

        val n = matrix1.size
        // Top left of 1
        val A11 = getSubmatrix(matrix1, 0, n/2, 0, n/2)
        // Top right of 1
        val A12 = getSubmatrix(matrix1, 0, n/2, n/2, n)
        // Bottom left of 1
        val A21 = getSubmatrix(matrix1, n/2, n, 0, n/2)
        // Bottom right of 1
        val A22 = getSubmatrix(matrix1, n/2, n, n/2, n)
        // Top left of 2
        val B11 = getSubmatrix(matrix2, 0, n/2, 0, n/2)
        // Top right of 2
        val B12 = getSubmatrix(matrix2, 0, n/2, n/2, n)
        // Bottom left of 2
        val B21 = getSubmatrix(matrix2, n/2, n, 0, n/2)
        // Bottom right of 2
        val B22 = getSubmatrix(matrix2, n/2, n, n/2, n)
        // M1
        val M1 = strassensMultiplication(addMatrices(A11, A22), addMatrices(B11, B22))
        val M2 = strassensMultiplication(addMatrices(A21, A22), B11)
        val M3 = strassensMultiplication(A11,subtractMatrices(B12, B22))
        val M4 = strassensMultiplication(A22, subtractMatrices(B21, B11))
        val M5 = strassensMultiplication(addMatrices(A11, A12) ,B22)
        val M6 = strassensMultiplication(subtractMatrices(A21, A11), addMatrices(B11, B12))
        val M7 = strassensMultiplication(subtractMatrices(A12, A22), addMatrices(B21, B22))
        val part = addMatrices(M1, M4)
        val sub = subtractMatrices(part, M5)
        val I = addMatrices(sub, M7)
        val J = addMatrices(M3, M5)
        val K = addMatrices(M2, M4)
        val L = addMatrices(addMatrices(subtractMatrices(M1, M2), M3),M6)
        val finalMatrix = mutableListOf<MutableList<Int>>()
        for (i in 0 until n/2) {
            finalMatrix.add((I[i] + J[i]).toMutableList())
        }
        for (i in 0 until n/2) {
            finalMatrix.add((K[i] + L[i]).toMutableList())
        }
        return finalMatrix
    }

    /**
     * Find the maximum value out of three values.
     * @param one The first number (Int) to consider.
     * @param two The second number (Int) to consider.
     * @param three Third number (Int) to consider.
     * @return the maximum of the three values.
     */
    fun max(one: Int, two: Int, three: Int): Int {
        val numList = mutableListOf(one, two, three)
        return numList.max()
    }

    /**
     * Calculate the gap penalty given a penalty to proscribe each additional gap
     * beyond the first in the Needleman Wunsch matrix.
     * @param d The gap penalty to proscribe each additional gap.
     * @return the value of the gap penalty.
     */
    fun getGapPenalty(d: Int): Int {
        return initialPenalty + (d - 1)*additionalPenalty
    }

    /**
     * Use the Needleman Wunsch algorithm to find the sequence alignment between two
     * sequences of nucleotides.
     * @param sequence1: A string representing the first nucleotide sequence.
     * @param sequence2: A string representing the second nucleotide sequence.
     */
    fun needlemanWunsch(sequence1: String, sequence2: String): MutableList<MutableList<String>> {
        val needlemanMatrix: MutableList<MutableList<Int>> = MutableList(sequence1.length+1) {MutableList(sequence1.length+1) {0} }
        val traceBackMatrix: MutableList<MutableList<Int>> = MutableList(sequence1.length+1) {MutableList(sequence1.length+1) {0} }
        needlemanMatrix[0][0] = 0
        for (index in 1 .. sequence1.length) {
            needlemanMatrix[0][index] = getGapPenalty(index)
            needlemanMatrix[index][0] = getGapPenalty(index)
        }
        for (row in 1 until needlemanMatrix[0].size) {
            for (col in 1 until needlemanMatrix[0].size) {
                if (sequence1[row-1] == sequence2[col-1]) {
                    //println("for spot $row, $col letters ${sequence1[row-1]} ${sequence2[col-1]} are a match")
                    val one = needlemanMatrix[row][col-1]-1
                    val two = needlemanMatrix[row-1][col]-1
                    val three = needlemanMatrix[row-1][col-1] + 1
                    val max = max(one, two, three)
                    needlemanMatrix[row][col] = max
                    when (max) {
                        one -> traceBackMatrix[row][col] = 1
                        two -> traceBackMatrix[row][col] = 2
                        three -> traceBackMatrix[row][col] = 3
                    }
                    //printMatrix(needlemanMatrix)
                } else {
                    //println("for spot $row, $col for letters ${sequence1[row-1]} ${sequence2[col-1]} and comparing ${needlemanMatrix[row-1][col]-1}, ${needlemanMatrix[row][col-1]-1}, ${needlemanMatrix[row-1][col-1]-1} ")
                    //printMatrix(needlemanMatrix)
                    val one = needlemanMatrix[row][col-1]-1
                    val two = needlemanMatrix[row-1][col]-1
                    val three = needlemanMatrix[row-1][col-1]-1
                    val max = max(one, two, three)
                    needlemanMatrix[row][col] = max
                    when (max) {
                        one -> traceBackMatrix[row][col] = 1
                        two -> traceBackMatrix[row][col] = 2
                        three -> traceBackMatrix[row][col] = 3
                    }
                }
            }
        }
        //printMatrix(needlemanMatrix)
        //printMatrix(traceBackMatrix)
        val sequence1List = MutableList<String>(0) {""}
        val sequence2List = MutableList<String>(0) {""}
        var row = traceBackMatrix.size - 1
        var col = traceBackMatrix.size - 1

        while (row > 0 || col > 0) {
            if (traceBackMatrix[row][col] == 3) {
                val substr = sequence1[row-1].toString()
                val substr2 = sequence2[col-1].toString()
                sequence1List.add(substr)
                sequence2List.add(substr2)
                row--
                col--
            } else if (traceBackMatrix[row][col] == 2) {
                sequence1List.add(sequence1[row-1].toString())
                sequence2List.add("-")
                row--
            } else {
                sequence1List.add("-")
                sequence2List.add(sequence2[col-1].toString())
                col--
            }
        }
        sequence1List.reverse()
        sequence2List.reverse()
        println(sequence1List)
        println(sequence2List)
        return mutableListOf(sequence1List, sequence2List)
    }


}

/**
 * Compare the matrix muliplication methods by timing how long each takes to finish multiplying matrices of various sizes.
 */
fun main() {
    val matrix = Matrix(rowDim = 4, colDim = 4, initValue = 0)
    val matrix1 = mutableListOf(mutableListOf(1, 2, 3, 4), mutableListOf(5, 6, 7, 8),  mutableListOf(9, 10, 11, 12), mutableListOf(13, 14, 15, 16))
    val matrix2 = mutableListOf(mutableListOf(1, 2, 3, 4), mutableListOf(5, 6, 7, 8),  mutableListOf(9, 10, 11, 12), mutableListOf(13, 14, 15, 16))
    val result = matrix.strassensMultiplication(matrix1, matrix2)
    val sizes = listOf(16, 32, 64, 128, 256, 512, 1024)
    for (size in sizes) {
        val matrix1 = MutableList(size) { MutableList(size) { (0..10).random() } }
        val matrix2 = MutableList(size) { MutableList(size) { (0..10).random() } }

        val strassenTime = measureTimeMillis {
            val result = matrix.strassensMultiplication(matrix1, matrix2)
        }

        val traditionalTime = measureTimeMillis {
            val result = matrix.tradMultiplicationDiffMatrix(matrix1, matrix2)
        }

        println("Matrix size: $size x $size")
        println("Strassen's algorithm time: $strassenTime ms")
        println("Traditional multiplication time: $traditionalTime ms")
        println()
    }

}
