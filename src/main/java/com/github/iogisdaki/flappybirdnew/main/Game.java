package com.github.iogisdaki.flappybirdnew.main;

import com.github.iogisdaki.flappybirdnew.handlers.BirdHandler;
import com.github.iogisdaki.flappybirdnew.handlers.PipeHandler;
import com.github.iogisdaki.flappybirdnew.loaders.ImageLoader;
import com.github.iogisdaki.flappybirdnew.window.GameWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static boolean gameOver;
    public static int score;
    public static int bestScore;
    private boolean running;
    private static BufferedImage background;
    private Graphics2D graphics2D;
    public static int generations = 0;

    public static void main(String[] args) {
        new GameWindow(new Game());
    }

    public synchronized void start() {
        running = true;
        Thread thread = new Thread();
        thread.start();
        run();
    }

    public void init() {
        background = ImageLoader.loadBufferedImage("resources/background.png");
        BirdHandler.initialize();
        PipeHandler.initialize();
    }

    @Override
    public void run() {
        init();
        this.requestFocus();
        // TODO make this less computationally expensive
        long pastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;
            while (delta > 0) {
                tick();
                render();
                delta--;
            }
            if (Game.gameOver) {
                PipeHandler.pipes.clear();
                Game.gameOver = false;
                Game.score = 0;
                BirdHandler.createNextGeneration();
                generations++;
                PipeHandler.initialize();
            }
        }
    }

    public void tick() {
        if (!gameOver) {
            PipeHandler.tick();
            BirdHandler.tick();
        }
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        graphics2D = (Graphics2D) g;
        graphics2D.drawImage(background, 0, 0, null);
        PipeHandler.render(graphics2D);
        BirdHandler.render(graphics2D);
        drawString("best score: " + bestScore, Color.black, GameWindow.WIDTH - 150, 30, 30);
        drawString("score: " + score, Color.black, GameWindow.WIDTH - 150, 60, 30);
        drawString("generation: " + generations, Color.black, GameWindow.WIDTH - 150, 90, 30);
        graphics2D.dispose();
        bufferStrategy.show();
    }

    private void drawString(String text, Color color, int x, int y, int size) {
        graphics2D.setFont(new Font("Times New Roman", Font.BOLD, size));
        graphics2D.setColor(color);
        int textWidth = graphics2D.getFontMetrics().stringWidth(text);
        graphics2D.drawString(text, x - textWidth / 2, y);
    }
}