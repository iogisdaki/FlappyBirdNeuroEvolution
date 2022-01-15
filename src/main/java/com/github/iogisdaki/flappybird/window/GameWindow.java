package com.github.iogisdaki.flappybird.window;

import com.github.iogisdaki.flappybird.main.Game;

import javax.swing.*;

public class GameWindow extends JFrame {
    public static final int WIDTH = 1024, HEIGHT = 786;

    public GameWindow(Game game) {
        setTitle("Flappy Bird");
        pack();
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(game);
        game.start();
    }
}
