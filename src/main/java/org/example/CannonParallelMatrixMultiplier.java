package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CannonParallelMatrixMultiplier implements MatrixMultiplier {
	private final int processes;

	public CannonParallelMatrixMultiplier(int processes) {
		this.processes = processes;
	}

	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		A = A.performLeftSkew();
		B = B.performTopSkew();
		var n = A.cols();
		var processesToStart = Math.min(n, processes);
		var futures = new CompletableFuture[processesToStart];
		for (int i = 0; i < processesToStart; i++) {
			futures[i] = CompletableFuture.supplyAsync(new CannonTask(A, B, i, processesToStart));
		}
		try {
			CompletableFuture.allOf(futures).get();
			var result = new Matrix(new int[n][n]);
			for (var future : futures) {
				Matrix matrix = (Matrix) future.get();
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < n; k++) {
						result.update(j, k, matrix.getWithShiftTop(j, k, 0));
					}
				}
			}
			return result;
		}
		catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
