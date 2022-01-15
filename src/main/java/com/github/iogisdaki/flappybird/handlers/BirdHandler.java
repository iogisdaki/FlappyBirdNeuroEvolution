package com.github.iogisdaki.flappybird.handlers;

import com.github.iogisdaki.flappybird.gameobjects.Bird;
import com.github.iogisdaki.flappybird.main.Game;

import java.awt.*;

public class BirdHandler {
    private static final int AMOUNT_OF_BIRDS = 300;
    private static Bird[] birds = new Bird[AMOUNT_OF_BIRDS];
    private static Bird[] newBirds = new Bird[AMOUNT_OF_BIRDS];
    private static int bestScore = 0;
    private static Bird bestBird;

    public static void initialize() {
        for (int i = 0; i < AMOUNT_OF_BIRDS; i++)
            birds[i] = new Bird();
    }

    public static void tick() {
        boolean allBirdsAreDead = true;
        for (int i = 0; i < AMOUNT_OF_BIRDS; i++) {
            if (!birds[i].isDead) {
                allBirdsAreDead = false;
                birds[i].tick();
                birds[i].think();
            }
        }
        if (allBirdsAreDead) Game.gameOver = true;
    }

    public static void render(Graphics2D g) {
        for (int i = 0; i < AMOUNT_OF_BIRDS; i++)
            if (!birds[i].isDead)
                birds[i].render(g);
    }

    public static void createNextGeneration() {
        int sum = 0;
        for (int i = 0; i < AMOUNT_OF_BIRDS; i++) {
            sum += birds[i].score;
            if (birds[i].score > bestScore) {
                bestScore = birds[i].score;
                bestBird = birds[i];
            }
        }
        newBirds[0] = new Bird(bestBird.neuralNetwork);
        for (int i = 1; i < AMOUNT_OF_BIRDS; i++) {
            newBirds[i] = pickOneBird(sum).copy();
            newBirds[i].mutate(0.1);
        }
        for (int i = 0; i < AMOUNT_OF_BIRDS; i++)
            birds[i] = newBirds[i].copy();
    }

    // pool selection
    private static Bird pickOneBird(int sum) {
        int index = 0;
        double random = getRandom(1, sum);
        while (random > 0) {
            random -= birds[index].score;
            index++;
        }
        index--;
        return birds[index];
    }

    private static double getRandom(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }
}