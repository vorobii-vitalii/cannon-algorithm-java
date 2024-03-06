package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestSequentialMatrixMultiplier {

	@Test
	void multiply() {
		var multiplier = new SequentialMatrixMultiplier();

		var result = multiplier.multiply(
				new Matrix(new int[][] {
						{1, 2},
						{3, 4}
				}),
				new Matrix(new int[][] {
						{5, 6},
						{8, 4}
				}));

		assertThat(result.matrix()).isDeepEqualTo(new int[][] {
				{ 21, 14 },
				{ 47, 34 },
		});
	}
}
