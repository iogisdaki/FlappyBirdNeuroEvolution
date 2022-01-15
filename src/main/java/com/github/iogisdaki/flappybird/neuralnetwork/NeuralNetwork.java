package com.github.iogisdaki.flappybird.neuralnetwork;

// A simple vanilla neural net written in java.
// It provides the ability of having multiple hidden layers and choosing the number of neurons per layer.
// Also, I have to mention this code only provides FCNNs(fully connected neural nets).
public class NeuralNetwork {
    private final double learningRate;
    private final int numberOfLayers;
    private final Layer[] layers;
    private final int[] structure;

    /**
     * Create an array and in each element provide the number of nodes for each layer
     * e.g. int[] structure = {2, 3, 4, 1};
     * This example has 2 input nodes, 3 hidden nodes in the first hidden layer, 4 in the second hidden layer, 1 output node
     */
    public NeuralNetwork(int[] structure, double learningRate) {
        if (structure.length < 3)
            throw new RuntimeException("Illegal network dimensions.The network must have at least an input, a hidden and an output layer");
        for (int i = 0; i < structure.length; i++)
            if (structure[i] < 1)
                throw new RuntimeException("Illegal layer dimensions.Each layer must have at least one neuron");

        this.learningRate = learningRate;
        this.structure = structure;
        numberOfLayers = structure.length;
        layers = new Layer[numberOfLayers];

        for (int i = 0; i < numberOfLayers; i++)
            layers[i] = (i == 0) ? new Layer(structure[i]) : new Layer(structure[i], structure[i - 1]);
    }

    // copy constructor
    public NeuralNetwork(NeuralNetwork neuralNetworkToCopy) {
        this.learningRate = neuralNetworkToCopy.learningRate;
        this.structure = neuralNetworkToCopy.structure;
        numberOfLayers = structure.length;
        layers = new Layer[numberOfLayers];
        for (int i = 0; i < numberOfLayers; i++)
            layers[i] = new Layer(neuralNetworkToCopy.layers[i]);
    }

    public double[] feedforward(double[] inputArray) {
        if (inputArray.length != structure[0])
            throw new RuntimeException("Illegal input dimensions.The input number must match the input layer neurons");

        for (int i = 0; i < numberOfLayers; i++)
            if (i == 0)
                layers[i].setNeurons(Matrix.fromArray(inputArray));
            else
                layers[i].calculateNeurons(layers[i - 1].getNeurons());
        Matrix output = layers[numberOfLayers - 1].getNeurons();
        return output.toArray();
    }

    public void mutate(double rate) {
        for (int i = 0; i < numberOfLayers; i++)
            layers[i].mutate(rate);
    }
}