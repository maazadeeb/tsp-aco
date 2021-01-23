package aco;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Matrix {
    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Matrix.class.getName());

    private final double[][] matrix;
    private final int N;

    public Matrix(int n) {
        N = n;
        matrix = new double[n][n];
    }

    public double get(int i, int j) {
        validateIndices(i, j);
        return matrix[i][j];
    }

    public void set(int i, int j, double val) {
        validateIndices(i, j);
        matrix[i][j] = val;
    }

    private void validateIndices(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            LOGGER.log(Level.SEVERE, "Invalid indices: ({}, {})", new Object[]{i, j});
            throw new IllegalArgumentException("Invalid indices");
        }
    }

    public void print() {
        LOGGER.log(Level.INFO, "Size: {}", N);
        for (int i = 0; i < N; i++) {
            LOGGER.log(Level.INFO, "{}", Arrays.toString(matrix[i]));
        }
    }
    public int getSize(){
        return this.N;
    }
}

