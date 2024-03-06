package org.example;

import java.util.Arrays;

public record Matrix(int[][] matrix) {

	public int getWithShiftLeft(int row, int col, int s) {
		return matrix[row][normalize(col + s, cols())];
	}

	public int getWithShiftTop(int row, int col, int s) {
		return matrix[normalize(row + s, rows())][col];
	}

	public void update(int row, int col, int d) {
		matrix[row][col] += d;
	}

	public int[][] getCopy() {
		var rows = rows();
		var cols = cols();
		int[][] copy = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			System.arraycopy(matrix[i], 0, copy[i], 0, cols);
		}
		return copy;
	}

	public int rows() {
		return matrix.length;
	}

	public int cols() {
		return matrix[0].length;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(matrix);
	}

	public Matrix performLeftSkew() {
		int rows = rows();
		int cols = cols();
		var copy = getCopy();
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy[i][j] = matrix[i][normalize(j + i, cols)];
			}
		}
		return new Matrix(copy);
	}

	public Matrix performTopSkew() {
		int rows = rows();
		int cols = cols();
		var copy = getCopy();
		for (int i = 1; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				copy[j][i] = matrix[normalize(j + i, rows)][i];
			}
		}
		return new Matrix(copy);
	}

	private int normalize(int i, int range) {
		if (i >= 0 && i < range) {
			return i;
		}
		if (i < 0) {
			return range + i;
		}
		return i % range;
	}

}
