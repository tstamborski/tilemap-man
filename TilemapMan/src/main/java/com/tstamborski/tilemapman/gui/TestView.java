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

import com.tstamborski.tilemapman.TilemapRenderer;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TestView extends LayeredView {
    private final TilemapProject project;
    private final Tileset set;
    
    public TestView(TilemapProject project, Tileset set) {
        this.project = project;
        this.set = set;
        
        project.forEachLayer(map -> addLayer(TilemapRenderer.getLayerImage(map, this.set, 1)));
        
        pack();
        enableEvents(MouseEvent.MOUSE_EVENT_MASK);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                setWorkLayer((getWorkLayer() + 1) % project.getLayersNumber());
            }
        }
    }
    
    
}
