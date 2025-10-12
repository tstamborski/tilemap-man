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
package com.tstamborski.tilemapman;

import com.tstamborski.tilemapman.model.FixedShortMap2D;
import com.tstamborski.tilemapman.model.ShortMap2D;
import java.awt.Point;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class PatternFromTilemap {
    private FixedShortMap2D layer;
    private int startX, startY;
    private int endX, endY;
    
    public void setTilemapLayer(FixedShortMap2D layer) {
        this.layer = layer;
    }
    
    public void setStartPoint(int x, int y) {
        startX = x;
        startY = y;
    }
    
    public void setEndPoint(int x, int y) {
        endX = x;
        endY = y;
    }
    
    public Point getUpperLeft() {
        return new Point(
                Math.min(startX, endX),
                Math.min(startY, endY)
        );
    }
    
    public Point getLowerRight() {
        return new Point(
                Math.max(startX, endX),
                Math.max(startY, endY)
        );
    }
    
    public ShortMap2D get() {
        int w = Math.max(startX, endX) - Math.min(startX, endX) + 1;
        int h = Math.max(startY, endY) - Math.min(startY, endY) + 1;
        
        ShortMap2D pattern = new ShortMap2D(w, h);
        
        int minx = Math.min(startX, endX);
        int miny = Math.min(startY, endY);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pattern.set(x, y, layer.get(minx + x, miny + y));
            }
        }
        
        return pattern;
    }
}
