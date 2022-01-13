package com.github.iogisdaki.flappybirdnew.gameobjects;

import com.github.iogisdaki.flappybirdnew.enums.PipeType;
import com.github.iogisdaki.flappybirdnew.loaders.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

public class Pipe {
    public int x;
    public final int y;
    public static final int WIDTH = 140;
    public static final int HEIGHT = 512;
    private final float velX = 3;
    private final BufferedImage image;
    public final PipeType type;

    public Pipe(int x, int y, PipeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        image = ImageLoader.loadBufferedImage("resources/" + type.toString().toLowerCase(Locale.ROOT) + "_pipe.png");
    }

    public void tick() {
        x -= velX;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
    }

    public boolean isOutOfScreen() {
        return x <= -WIDTH;
    }

    public Rectangle getBounds() {//!!
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean isTopPipe() {
        return type == PipeType.TOP;
    }
}