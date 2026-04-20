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
package com.tstamborski.tilemapman.gui;

import com.tstamborski.tilemapman.events.SelectionEvent;
import com.tstamborski.tilemapman.events.SelectionListener;
import com.tstamborski.tilemapman.model.ShortMap2D;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.tilemapman.tools.DefaultTilesetSelectionTool;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilesetPicker extends TilesetView {
    private final DefaultTilesetSelectionTool selectionTool;
    private ShortMap2D selectionPattern;
    private SelectionListener selectionListener;
    
    public TilesetPicker() {
        selectionTool = new DefaultTilesetSelectionTool();
        enableEvents(MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);
    }
    
    public void setSelectionListener(SelectionListener al) {
        this.selectionListener = al;
    }

    @Override
    public void setZoom(int newZoom) {
        super.setZoom(newZoom);
        
        if (getTileset() == null) return;
        selectionTool.setTileSize(getTileset().getTileWidth() * newZoom, getTileset().getTileHeight() * newZoom);
    }

    @Override
    public void setTileset(Tileset set) {
        super.setTileset(set);
        selectionTool.setTileset(set);
        selectionTool.setTileSize(set.getTileWidth() * getZoom(), set.getTileHeight() * getZoom());
    }
    
    public ShortMap2D getSelectionPattern() {
        return selectionPattern;
    }
    
    protected int getTilesetX(int mouseX) {
        return mouseX / getZoom();
    }
    
    protected int getTilesetY(int mouseY) {
        return mouseY / getZoom();
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
            selectionTool.apply(getTilesetX(e.getX()), getTilesetY(e.getY()));
            repaint();
        }
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        
        if (e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_PRESSED) {
            selectionTool.press(getTilesetX(e.getX()), getTilesetY(e.getY()));
            repaint();
        } else if (e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_RELEASED) {
            selectionPattern = selectionTool.release();
            fireSelectionEvent();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        selectionTool.paintSelection(g);
    }

    protected void fireSelectionEvent() {
        if (selectionListener != null) {
            selectionListener.selectionPerformed(new SelectionEvent(this, selectionPattern));
        }
    }
}
