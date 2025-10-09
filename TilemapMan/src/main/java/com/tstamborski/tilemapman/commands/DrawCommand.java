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
package com.tstamborski.tilemapman.commands;

import com.tstamborski.tilemapman.model.FixedShortMap2D;
import com.tstamborski.tilemapman.model.ReadonlyShortMap2D;
import com.tstamborski.tilemapman.model.ShortMap2D;
import com.tstamborski.tilemapman.model.TilemapProject;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class DrawCommand implements Command {
    private final TilemapProject project;
    private final int layer, x, y;
    private final ReadonlyShortMap2D pattern;
    private final ShortMap2D dirtyRect;
    
    public DrawCommand(TilemapProject project, int layer, int x, int y, ReadonlyShortMap2D pattern) {
        this.project = project;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.pattern = pattern;
        
        dirtyRect = new ShortMap2D(pattern.getWidth(), pattern.getHeight());
    }

    @Override
    public void execute() {
        project.beginModify();
        
        FixedShortMap2D dst = project.getLayer(layer);
        for (int i = 0; i < pattern.getWidth(); i++) {
            for (int j = 0; j < pattern.getHeight(); j++) {
                if (x + i < dst.getWidth() && y + j < dst.getHeight()) {
                    dirtyRect.set(i, j, dst.get(x + i, y + j));
                    dst.set(x+i, y+j, pattern.get(i, j));
                }
            }
        }
        
        project.endModify();
    }

    @Override
    public void undo() {
        project.beginModify();
        
        FixedShortMap2D dst = project.getLayer(layer);
        for (int i = 0; i < dirtyRect.getWidth(); i++) {
            for (int j = 0; j < dirtyRect.getHeight(); j++) {
                if (x + i < dst.getWidth() && y + j < dst.getHeight()) {
                    dst.set(x+i, y+j, dirtyRect.get(i, j));
                }
            }
        }
        
        project.endModify();
    }
    
}
