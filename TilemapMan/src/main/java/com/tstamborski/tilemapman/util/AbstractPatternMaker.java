/*
 * The MIT License
 *
 * Copyright 2026 Tobiasz Stamborski <tstamborski@outlook.com>.
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
package com.tstamborski.tilemapman.util;

import com.tstamborski.tilemapman.model.ShortMap2D;
import java.awt.Point;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public abstract class AbstractPatternMaker {
    protected int startX, startY;
    protected int endX, endY;
    
    public void setStartPoint(int xTilemap, int yTilemap) {
        startX = xTilemap;
        startY = yTilemap;
    }
    
    public void setEndPoint(int xTilemap, int yTilemap) {
        endX = xTilemap;
        endY = yTilemap;
    }
    
    public Point getGridUpperLeft() {
        return new Point(
                Math.min(startX, endX),
                Math.min(startY, endY)
        );
    }
    
    public Point getGridLowerRight() {
        return new Point(
                Math.max(startX, endX),
                Math.max(startY, endY)
        );
    }
    
    public abstract ShortMap2D get();
}

