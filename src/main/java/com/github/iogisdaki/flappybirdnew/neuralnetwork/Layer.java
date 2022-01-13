package com.github.iogisdaki.flappybirdnew.neuralnetwork;

public class Layer {
    public Matrix neurons;
    public Matrix weights;
    public Matrix biases;
    public Matrix errors;
    public boolean isInputLayer;

    Layer(int numberOfNeurons, int numberOfPreviousLayerNeurons) {
        neurons = new Matrix(numberOfNeurons, 1);
        weights = new Matrix(numberOfNeurons, numberOfPreviousLayerNeurons);
        biases = new Matrix(numberOfNeurons, 1);
        errors = new Matrix(numberOfNeurons, 1);
        weights.randomize();
        biases.randomize();
        isInputLayer = false;
    }

    // input layer case where no weights are needed
    Layer(int numberOfNeurons) {
        neurons = new Matrix(numberOfNeurons, 1);
        isInputLayer = true;
    }

    // copy constructor
    Layer(Layer layerToCopy) {
        if (layerToCopy.isInputLayer) {
            this.neurons = new Matrix(layerToCopy.neurons);
            isInputLayer = true;
        } else {
            weights = new Matrix(layerToCopy.weights);
            biases = new Matrix(layerToCopy.biases);
            errors = new Matrix(layerToCopy.errors.rows, 1);
            isInputLayer = false;
        }
    }

    public void calculateNeurons(Matrix previousLayerNeurons) {
        neurons = Matrix.dotProduct(weights, previousLayerNeurons);
        neurons.add(biases);
        neurons.passThroughSigmoid();
    }

    public void mutate(double rate) {
        if (!isInputLayer) {
            for (int i = 0; i < weights.rows; i++)
                for (int j = 0; j < weights.cols; j++)
                    if (getRandom(0, 1) < rate)
                        weights.matrix[i][j] = weights.matrix[i][j] + getRandom(-0.1, 0.1);
        }
    }

    private static double getRandom(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void setNeurons(Matrix value) {
        neurons = value;
    }

    public Matrix getNeurons() {
        return neurons;
    }
}