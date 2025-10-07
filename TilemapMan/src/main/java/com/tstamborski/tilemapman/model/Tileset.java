/*
 * The MIT License
 *
 * Copyright 2025 Tobiasz Stamborski <tstamborski@outlook.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.tstamborski.tilemapman.model;

import java.awt.image.BufferedImage;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class Tileset {
    private final BufferedImage originalImage;
    private final BufferedImage[] tileImages;
    private final int tileWidth, tileHeight;
    
    public BufferedImage getTile(int index) {
        return tileImages[index];
    }
    
    public int getTileIndexAt(int x, int y) {
        int columns = originalImage.getWidth() / tileWidth;
        int index = ((y / tileHeight) * columns) + (x / tileWidth);
        
        if (index < 0 || index >= tileImages.length)
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bounds.", index));
        
        return index;
    }
    
    public int getTileWidth() {
        return tileWidth;
    }
    
    public int getTileHeight() {
        return tileHeight;
    }
    
    public int getSize() {
        return tileImages.length;
    }
    
    public int getOriginalWidth() {
        return originalImage.getWidth();
    }
    
    public int getOriginalHeight() {
        return originalImage.getHeight();
    }
    
    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public Tileset(int tilew, int tileh, BufferedImage[] tiles, BufferedImage srcImg) {
        this.tileWidth = tilew;
        this.tileHeight = tileh;
        this.tileImages = tiles;
        this.originalImage = srcImg;
    }
}
