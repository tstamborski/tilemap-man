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

import com.tstamborski.tilemapman.util.PatternFromTilemap;
import com.tstamborski.tilemapman.model.ShortMap2D;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.util.SelectionImage;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapSelectionTool implements SelectionTool {
    private TilemapProject project;
    private SelectionImage selectionImage;
    private PatternFromTilemap selectionMaker;

    @Override
    public void setProject(TilemapProject project) {
        this.project = project;
    }

    @Override
    public void setTileSize(int width, int height) {
        selectionImage = new SelectionImage(width, height);
    }

    @Override
    public void paintSelection(Graphics g) {
        Point upperLeft = selectionMaker.getUpperLeft();
        upperLeft.x *= selectionImage.getWidth();
        upperLeft.y *= selectionImage.getHeight();
        
        Point lowerRight = selectionMaker.getLowerRight();
        lowerRight.x *= selectionImage.getWidth();
        lowerRight.y *= selectionImage.getHeight();
        
        for (int y = upperLeft.y; y <= lowerRight.y; y += selectionImage.getHeight()) {
            for (int x = upperLeft.x; x <= lowerRight.x; x += selectionImage.getWidth()) {
                g.drawImage(selectionImage, x, y, null);
            }
        }
    }

    @Override
    public void press(int layer, int x, int y) {
        selectionMaker = new PatternFromTilemap(project.getLayer(layer));
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
    
}
