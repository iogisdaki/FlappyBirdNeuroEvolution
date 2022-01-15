package com.github.iogisdaki.flappybird.neuralnetwork;

public class Matrix {
    public int rows, cols;
    public double[][] matrix;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    // copy constructor
    Matrix(Matrix matrixToCopy) {
        this.rows = matrixToCopy.rows;
        this.cols = matrixToCopy.cols;
        matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] = matrixToCopy.matrix[i][j];
    }

    public void randomize() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] = Math.random() * 2 - 1;
    }

    public void add(Matrix m) {
        if (cols != m.cols || rows != m.rows) throw new RuntimeException("Illegal Vector Dimensions.");
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] += m.matrix[i][j];
    }

    public static Matrix fromArray(double[] x) {
        Matrix temp = new Matrix(x.length, 1);
        for (int i = 0; i < x.length; i++)
            temp.matrix[i][0] = x[i];
        return temp;

    }

    public double[] toArray() {
        double[] temp = new double[rows];
        for (int i = 0; i < rows; i++)
            temp[i] = matrix[i][0];
        return temp;
    }

    public static Matrix dotProduct(Matrix a, Matrix b) {
        if (a.cols != b.rows) throw new RuntimeException("Illegal Matrix Dimensions.");
        Matrix temp = new Matrix(a.rows, b.cols);
        for (int i = 0; i < temp.rows; i++) {
            for (int j = 0; j < temp.cols; j++) {
                double sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.matrix[i][k] * b.matrix[k][j];
                }
                temp.matrix[i][j] = sum;
            }
        }
        return temp;
    }

    public void passThroughSigmoid() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.matrix[i][j] = 1 / (1 + Math.exp(-this.matrix[i][j]));
    }
}