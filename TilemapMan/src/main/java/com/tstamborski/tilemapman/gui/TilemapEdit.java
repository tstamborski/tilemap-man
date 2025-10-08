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
package com.tstamborski.tilemapman.gui;

import com.tstamborski.tilemapman.TilemapEditController;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapEdit extends LayeredView {
    private final TilemapEditController controller;
    
    public TilemapEdit() {
        controller = new TilemapEditController(this);
        enableEvents(MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);
    }
    
    public void setZoom(int zoom) {
        controller.setZoom(zoom);
    }
    
    public void setTileset(Tileset tileset) {
        controller.setTileset(tileset);
    }
    
    public void setTilemapProject(TilemapProject project) {
        controller.setTilemapProject(project);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        controller.processMouseEvent(e);
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        controller.processMouseEvent(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (controller.isSelection()) {
            Point ul = controller.getSelectionUpperLeft();
            Point lr = controller.getSelectionLowerRight();
            BufferedImage img = controller.getSelectionImage();
            
            for (int y = ul.y; y < lr.y; y += img.getHeight()) {
                for (int x = ul.x; x < lr.x; x += img.getWidth()) {
                    g.drawImage(img, x, y, null);
                }
            }
        }
    }
}
