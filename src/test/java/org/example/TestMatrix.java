package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestMatrix {

	@Test
	void performLeftShiftOperation() {
		Matrix matrix = new Matrix(new int[][] {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9},
		});

		assertThat(matrix.performLeftSkew().matrix())
				.isDeepEqualTo(
						new int[][] {
								{1, 2, 3},
								{5, 6, 4},
								{9, 7, 8},
						});
	}

	@Test
	void performTopShiftOperation() {
		Matrix matrix = new Matrix(new int[][] {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9},
		});

		assertThat(matrix.performTopSkew().matrix())
				.isDeepEqualTo(
						new int[][] {
								{1, 5, 9},
								{4, 8, 3},
								{7, 2, 6},
						});
	}

}
