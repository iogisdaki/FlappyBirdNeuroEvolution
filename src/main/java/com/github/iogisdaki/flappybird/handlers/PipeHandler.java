package com.github.iogisdaki.flappybird.handlers;

import com.github.iogisdaki.flappybird.main.Game;
import com.github.iogisdaki.flappybird.window.GameWindow;
import com.github.iogisdaki.flappybird.enums.PipeType;
import com.github.iogisdaki.flappybird.gameobjects.Bird;
import com.github.iogisdaki.flappybird.gameobjects.Pipe;

import java.awt.*;
import java.util.LinkedList;

public class PipeHandler {
    public static LinkedList<Pipe> pipes = new LinkedList<>();
    private static final int SPACE_BETWEEN_PIPES = 5 * Bird.HEIGHT;
    private static int spawnPipeTime = 200;
    private static int currentTime;

    public static void initialize() {
        int topPipeY = getRandom(GameWindow.HEIGHT / 6, GameWindow.HEIGHT - GameWindow.HEIGHT / 6 - SPACE_BETWEEN_PIPES);
        // rendering the pipes a little outside the screen so that they don't have to change size and it looks symmetrical
        Pipe topPipe = new Pipe(GameWindow.WIDTH - Pipe.WIDTH, topPipeY - Pipe.HEIGHT, PipeType.TOP);
        Pipe bottomPipe = new Pipe(GameWindow.WIDTH - Pipe.WIDTH, topPipeY + SPACE_BETWEEN_PIPES, PipeType.BOTTOM);
        pipes.add(topPipe);
        pipes.add(bottomPipe);
        currentTime = 0;
    }

    public static void render(Graphics2D g) {
        for (Pipe pipe : pipes)
            pipe.render(g);
    }

    public static void tick() {
        if (currentTime >= spawnPipeTime)
            spawnPipes();
        else
            currentTime++;
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).tick();
            if (pipes.get(i).isOutOfScreen()) {
                // so as not to count both bottom and top and overcount
                if (pipes.get(i).isTopPipe())
                    Game.score++;
                if (Game.score > Game.bestScore)
                    Game.bestScore = Game.score;
                pipes.remove(i);
            }
        }
    }

    private static void spawnPipes() {
        int topPipeY = getRandom(GameWindow.HEIGHT / 6, GameWindow.HEIGHT - GameWindow.HEIGHT / 6 - SPACE_BETWEEN_PIPES);
        // rendering the pipes a little outside the screen so that they don't have tom change size and it looks symmetrical
        Pipe topPipe = new Pipe(GameWindow.WIDTH, topPipeY - Pipe.HEIGHT, PipeType.TOP);
        Pipe bottomPipe = new Pipe(GameWindow.WIDTH, topPipeY + SPACE_BETWEEN_PIPES, PipeType.BOTTOM);
        pipes.add(topPipe);
        pipes.add(bottomPipe);
        currentTime = 0;
    }

    private static int getRandom(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
