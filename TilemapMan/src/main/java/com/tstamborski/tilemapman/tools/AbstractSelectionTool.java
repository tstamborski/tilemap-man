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
package com.tstamborski.tilemapman.tools;

import com.tstamborski.tilemapman.model.ShortMap2D;
import com.tstamborski.tilemapman.model.TileLimits;
import com.tstamborski.tilemapman.util.AbstractPatternMaker;
import com.tstamborski.tilemapman.util.SelectionImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public abstract class AbstractSelectionTool implements SelectionTool {
    protected SelectionImage selectionImage, invalidSelectionImage;
    protected AbstractPatternMaker selectionMaker;
    
    @Override
    public void setTileSize(int width, int height) {
        selectionImage = new SelectionImage(width, height);
        invalidSelectionImage = 
                new SelectionImage(width, height, Color.RED, new Color(0x44, 0, 0, SelectionImage.ALPHA));
    }

    @Override
    public void paintSelection(Graphics g) {
        if (selectionImage == null || selectionMaker == null)
            return;
        
        Point upperLeft = selectionMaker.getGridUpperLeft();
        upperLeft.x *= selectionImage.getWidth();
        upperLeft.y *= selectionImage.getHeight();
        
        Point lowerRight = selectionMaker.getGridLowerRight();
        lowerRight.x *= selectionImage.getWidth();
        lowerRight.y *= selectionImage.getHeight();
        
        ShortMap2D pattern = selectionMaker.get();
        
//        for (int y = upperLeft.y; y <= lowerRight.y; y += selectionImage.getHeight()) {
//            for (int x = upperLeft.x; x <= lowerRight.x; x += selectionImage.getWidth()) {
//                g.drawImage(selectionImage, x, y, null);
//            }
//        }

        for (int y = 0; y < pattern.getHeight(); y++) {
            for (int x = 0; x < pattern.getWidth(); x++) {
                if (TileLimits.isValidTile(pattern.get(x, y)))
                    g.drawImage(
                            selectionImage,
                            upperLeft.x + x * selectionImage.getWidth(),
                            upperLeft.y + y * selectionImage.getHeight(),
                            null
                        );
                else
                    g.drawImage(
                            invalidSelectionImage,
                            upperLeft.x + x * selectionImage.getWidth(),
                            upperLeft.y + y * selectionImage.getHeight(),
                            null
                        );
            }
        }
    }

    @Override
    public void press(int x, int y) {
        selectionMaker = getSelectionMaker();
        selectionMaker.setStartPoint(x, y);
        selectionMaker.setEndPoint(x, y);
    }

    @Override
    public void apply(int x, int y) {
        selectionMaker.setEndPoint(x, y);
    }

    @Override
    public ShortMap2D release() {
        return selectionMaker.get();
    }
    
    protected abstract AbstractPatternMaker getSelectionMaker();
}
