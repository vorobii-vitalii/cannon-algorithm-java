package org.example;

public class SequentialMatrixMultiplier implements MatrixMultiplier {
	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		final int rows = A.rows();
		final int cols = A.cols();
		var result = new Matrix(new int[rows][cols]);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int s = 0;
				for (int k = 0; k < rows; k++) {
					s += A.getWithShiftLeft(i, k, 0) * B.getWithShiftLeft(k, j, 0);
				}
				result.update(i, j, s);
			}
		}
		return result;
	}
}
