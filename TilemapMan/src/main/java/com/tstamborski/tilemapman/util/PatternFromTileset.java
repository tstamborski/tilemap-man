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
import com.tstamborski.tilemapman.model.TileLimits;
import com.tstamborski.tilemapman.model.Tileset;
import java.awt.Point;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class PatternFromTileset extends AbstractPatternMaker {
    private final Tileset tileset;
    
    public PatternFromTileset(Tileset set) {
        this.tileset = set;
    }

    @Override
    public Point getGridLowerRight() {
        return new Point(
                Math.max(startX, endX) / tileset.getTileWidth(),
                Math.max(startY, endY) / tileset.getTileHeight()
        );
    }

    @Override
    public Point getGridUpperLeft() {
        return new Point(
                Math.min(startX, endX) / tileset.getTileWidth(),
                Math.min(startY, endY) / tileset.getTileHeight()
        );
    }
    
    @Override
    public ShortMap2D get() {
        if (endX < 0) endX = 0;
        if (endX >= tileset.getOriginalWidth()) endX = tileset.getOriginalWidth() - 1;
        if (endY < 0) endY = 0;
        if (endY >= tileset.getOriginalHeight()) endY = tileset.getOriginalHeight() - 1;
        
        int w = Math.max(startX, endX) / tileset.getTileWidth() - Math.min(startX, endX) / tileset.getTileWidth() + 1;
        int h = Math.max(startY, endY) / tileset.getTileWidth() - Math.min(startY, endY) / tileset.getTileWidth() + 1;
        
        ShortMap2D pattern = new ShortMap2D(w, h);
        
        int minx = Math.min(startX, endX);
        int miny = Math.min(startY, endY);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                try {
                    pattern.set(x, y, 
                            (short)tileset.getTileIndexAt(minx + x * tileset.getTileWidth(), miny + y * tileset.getTileHeight())
                    );
                } catch(IndexOutOfBoundsException ex) {
                    pattern.set(x, y, TileLimits.INVALID_TILE);
                }
            }
        }
        
        return pattern;
    }
}
