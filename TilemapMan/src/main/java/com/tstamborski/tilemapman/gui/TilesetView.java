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

import com.tstamborski.tilemapman.model.Tileset;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilesetView extends JComponent {
    private int zoom;
    private Tileset tileset;
    
    public TilesetView() {
        zoom = 1;
        tileset = null;
        setEnabled(false);
    }
    
    public void setTileset(Tileset set) {
        this.tileset = set;
        
        if (set == null) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
        
        pack();
        repaint();
    }
    
    public Tileset getTileset() {
        return tileset;
    }
    
    public void setZoom(int newZoom) {
        if (newZoom != zoom) {
            zoom = newZoom;
            
            pack();
            repaint();
        }
    }
    
    public int getZoom() {
        return zoom;
    }
    
    protected void pack() {
        int setWidth, setHeight;
        
        if (tileset != null) {
            setWidth = tileset.getOriginalWidth();
            setHeight = tileset.getOriginalHeight();
        } else {
            setWidth = 0;
            setHeight = 0;
        }
        
        setPreferredSize(
                new Dimension(setWidth * zoom, setHeight * zoom)
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!isEnabled()) return;
        
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.drawImage(
                tileset.getOriginalImage(), 
                0, 0, 
                getWidth(), getHeight(),
                null
        );
    }
}
