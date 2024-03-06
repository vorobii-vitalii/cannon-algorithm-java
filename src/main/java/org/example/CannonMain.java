package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class CannonMain {

	public static void main(String[] args) {
		int n = 500;
		int p = 3;
		var A = createRandom(n);
		var B = createRandom(n);

		var parallelOneThread = measure(() -> new CannonParallelMatrixMultiplier(1).multiply(A, B));
		System.out.println("Parallel 1 thread = " + parallelOneThread.duration());

		var parallelPThread = measure(() -> new CannonParallelMatrixMultiplier(p).multiply(A, B));
		System.out.println("Parallel p thread = " + parallelPThread.duration());

		assertMatricesAreSame(parallelOneThread.res(), parallelPThread.res());

		var sequential = measure(() -> new SequentialMatrixMultiplier().multiply(A, B));
		System.out.println("Sequential = " + sequential.duration());
		assertMatricesAreSame(parallelOneThread.res(), sequential.res());
	}

	private static void assertMatricesAreSame(Matrix A, Matrix B) {
		final int rows = A.rows();
		final int cols = A.cols();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (A.getWithShiftLeft(i, j, 0) != B.getWithShiftLeft(i, j, 0)) {
					System.out.println(Arrays.deepToString(A.matrix()));
					System.out.println(Arrays.deepToString(B.matrix()));
					throw new IllegalStateException("результати не співпадають " + i + " " + j);
				}
			}
		}
		System.out.println("Результати співпадають");
	}

	private static  <T> TimedResult<T> measure(Supplier<T> action) {
		var start = System.currentTimeMillis();
		var r = action.get();
		return new TimedResult<>(r, System.currentTimeMillis() - start);
	}

	private static Matrix createRandom(int n) {
		int[][] matrix = new int[n][n];
		var random = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = random.nextInt(1000);
			}
		}
		return new Matrix(matrix);
	}

}
