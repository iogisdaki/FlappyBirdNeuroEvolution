package com.github.iogisdaki.flappybirdnew.gameobjects;

import com.github.iogisdaki.flappybirdnew.enums.PipeType;
import com.github.iogisdaki.flappybirdnew.neuralnetwork.NeuralNetwork;
import com.github.iogisdaki.flappybirdnew.window.GameWindow;
import com.github.iogisdaki.flappybirdnew.handlers.PipeHandler;
import com.github.iogisdaki.flappybirdnew.loaders.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bird {
    private final int x;
    private int y;
    private static final int WIDTH = 75;
    public static final int HEIGHT = 54;
    private float velY;
    private final BufferedImage image;
    public final NeuralNetwork neuralNetwork;
    private final int[] netStructure = {5, 8, 1};
    public boolean isDead = false;
    public int score = 0;

    public Bird() {
        x = 50;
        y = 50;
        image = ImageLoader.loadBufferedImage("resources/flappy_bird.png");
        neuralNetwork = new NeuralNetwork(netStructure, 0.01);
    }

    // copy constructor
    public Bird(NeuralNetwork neuralNetwork) {
        x = 50;
        y = 50;
        image = ImageLoader.loadBufferedImage("resources/flappy_bird.png");
        this.neuralNetwork = new NeuralNetwork(neuralNetwork);
    }

    public void tick() {
        score++;
        float GRAVITY = 0.3f;
        velY += GRAVITY;
        y += velY;
        float MAX_SPEED = 12f;
        if (velY > MAX_SPEED)
            velY = MAX_SPEED;
        if (y + HEIGHT / 2 > GameWindow.HEIGHT)
            isDead = true;
        else if (y < 0)
            y = 0;
        for (int i = 0; i < PipeHandler.pipes.size(); i++)
            if (this.getBounds().intersects(PipeHandler.pipes.get(i).getBounds()))
                isDead = true;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void think() {
        if (PipeHandler.pipes.size() >= 1) {
            Pipe closestTopPipe = null;
            Pipe closestBottomPipe = null;
            int closestTopDistance = GameWindow.WIDTH + 10;
            for (int i = 0; i < PipeHandler.pipes.size(); i++) {
                Pipe currentPipe = PipeHandler.pipes.get(i);
                int currentDistance = currentPipe.x - x;
                if (currentPipe.type == PipeType.TOP && currentDistance >= -WIDTH - WIDTH / 2 && currentDistance < closestTopDistance) {
                    closestTopPipe = currentPipe;
                    closestBottomPipe = PipeHandler.pipes.get(i + 1);
                    closestTopDistance = currentDistance;
                }
            }
            if (closestBottomPipe != null) {
                // normalising the inputs by dividing by the height of the window
                double[] inputs = {y / Window.HEIGHT, closestTopPipe.y / Window.HEIGHT, closestBottomPipe.y / Window.HEIGHT, closestTopPipe.x / Window.HEIGHT, velY / 10};
                double[] outputArray = neuralNetwork.feedforward(inputs);
                double output = outputArray[0];
                if (output > 0.7)
                    jump();
            }
        }
    }

    public void mutate(double rate) {
        neuralNetwork.mutate(rate);
    }

    public void jump() {
        velY = -5;
    }

    public Bird copy() {
        return new Bird(this.neuralNetwork);
    }
}