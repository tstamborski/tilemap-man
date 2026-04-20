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

import com.tstamborski.tilemapman.events.SelectionEvent;
import com.tstamborski.tilemapman.events.SelectionListener;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.tilemapman.tools.TilemapSelectionTool;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class EnhancedTilemapEdit extends BasicTilemapEdit {
    private final TilemapSelectionTool selectionTool;
    private boolean rMouseButton;
    private SelectionListener listener;
    
    public EnhancedTilemapEdit() {
        selectionTool = new TilemapSelectionTool();
    }
    
    public void setSelectionListener(SelectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTileset(Tileset tileset) {
        super.setTileset(tileset);
        selectionTool.setTileSize(
                tileset.getTileWidth() * getZoom(), tileset.getTileHeight() * getZoom()
        );
    }

    @Override
    public void setZoom(int zoom) {
        super.setZoom(zoom);
        
        if (getTileset() == null) return;
        selectionTool.setTileSize(
                getTileset().getTileWidth() * zoom, getTileset().getTileHeight() * zoom
        );
    }
    
    @Override
    public void setTilemapProject(TilemapProject project) {
        super.setTilemapProject(project);
        selectionTool.setLayer(project.getLayer(getWorkLayer()));
    }

    @Override
    public void setWorkLayer(int workLayer) {
        super.setWorkLayer(workLayer);
        
        if (getTilemapProject() == null) return;
        selectionTool.setLayer(getTilemapProject().getLayer(workLayer));
    }

    @Override
    protected void processMouseEvent(MouseEvent event) {
        super.processMouseEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseEvent.BUTTON3) {
            selectionTool.press(getTilemapX(event.getX()), getTilemapY(event.getY()));
            rMouseButton = true;
            repaint();
        } if (event.getID() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseEvent.BUTTON3) {
            setPattern(selectionTool.release());
            rMouseButton = false;
            repaint();
            fireSelectionEvent();
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent event) {
        super.processMouseMotionEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_DRAGGED && rMouseButton) {
            selectionTool.apply(getTilemapX(event.getX()), getTilemapY(event.getY()));
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (rMouseButton) {
            selectionTool.paintSelection(g);
        }
    }
    
    protected void fireSelectionEvent() {
        if (listener != null)
            listener.selectionPerformed(new SelectionEvent(this, getPattern()));
    }
}
