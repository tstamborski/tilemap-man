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

import com.tstamborski.tilemapman.PatternFromTilemap;
import com.tstamborski.tilemapman.model.Tileset;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class EnhancedTilemapEdit extends BasicTilemapEdit {
    private final PatternFromTilemap selectMaker;
    private SelectionImage selectImage;
    private boolean rMouseButton;
    private ActionListener listener;
    
    public EnhancedTilemapEdit() {
        selectMaker = new PatternFromTilemap();
    }
    
    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTileset(Tileset tileset) {
        super.setTileset(tileset);
        createSelectionImage();
    }

    @Override
    public void setZoom(int zoom) {
        super.setZoom(zoom);
        createSelectionImage();
    }

    @Override
    protected void processMouseEvent(MouseEvent event) {
        super.processMouseEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseEvent.BUTTON3) {
            selectMaker.setTilemapLayer(getTilemapProject().getLayer(getWorkLayer()));
            selectMaker.setStartPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            selectMaker.setEndPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            rMouseButton = true;
            repaint();
        } if (event.getID() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseEvent.BUTTON3) {
            setPattern(selectMaker.get());
            rMouseButton = false;
            repaint();
            fireActionEvent();
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent event) {
        super.processMouseMotionEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_DRAGGED && rMouseButton) {
            selectMaker.setEndPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (rMouseButton) {
            paintSelection(g);
        }
    }
    
    protected void paintSelection(Graphics g) {
        Point ul = getSelectionUpperLeft();
        Point lr = getSelectionLowerRight();

        for (int y = ul.y; y < lr.y; y += selectImage.getHeight()) {
            for (int x = ul.x; x < lr.x; x += selectImage.getWidth()) {
                g.drawImage(selectImage, x, y, null);
            }
        }
    }
    
    protected void createSelectionImage() {
        if (getTileset() == null) return;
        
        selectImage = new SelectionImage(getTileset().getTileWidth() * getZoom(), getTileset().getTileHeight() * getZoom());
    }
    
    private Point getSelectionUpperLeft() {
        Point p = selectMaker.getUpperLeft();
        p.setLocation(
                p.x * getTileset().getTileWidth() * getZoom(), 
                p.y * getTileset().getTileHeight() * getZoom()
            );
        return p;
    }
    
    private Point getSelectionLowerRight() {
        Point p = selectMaker.getLowerRight();
        p.setLocation(
                (p.x + 1) * getTileset().getTileWidth() * getZoom(), 
                (p.y + 1) * getTileset().getTileHeight() * getZoom()
            );
        return p;
    }
    
    protected void fireActionEvent() {
        if (listener != null)
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "SELECTION"));
    }
}
