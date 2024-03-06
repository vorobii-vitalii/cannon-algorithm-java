package org.example;

import java.util.function.Supplier;

public class CannonTask implements Supplier<Matrix> {
	private final Matrix A;
	private final Matrix B;
	private final int id;
	private final int numProcesses;

	public CannonTask(Matrix a, Matrix b, int id, int numProcesses) {
		A = a;
		B = b;
		this.id = id;
		this.numProcesses = numProcesses;
	}

	@Override
	public Matrix get() {
		var rows = A.rows();
		var cols = A.cols();
		var resultMatrix = new Matrix(new int[rows][cols]);
		int c = id;
		while (c < rows) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					resultMatrix.update(i, j, A.getWithShiftLeft(i, j, c) * B.getWithShiftTop(i, j, c));
				}
			}
			c += numProcesses;
		}
		return resultMatrix;
	}
}
