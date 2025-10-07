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

import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TestView extends JComponent {
    private final TilemapProject map;
    private final Tileset set;
    private int usedLayer;
    
    public TestView(TilemapProject map, Tileset set) {
        this.map = map;
        this.set = set;
        
        setPreferredSize(new Dimension(map.getWidth()*set.getTileWidth(), map.getHeight()*set.getTileHeight()));
        enableEvents(MouseEvent.MOUSE_EVENT_MASK);
    }

    protected void drawLayer(Graphics2D g, float alpha, int layer) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        
        int stepx = set.getTileWidth();
        int stepy = set.getTileHeight();
        int maxx = map.getWidth();
        int maxy = map.getHeight();
        
        g.setComposite(ac);
        for (int x = 0; x < maxx; x++)
            for (int y = 0; y < maxy; y++)
                g.drawImage(set.getTile(map.getLayer(layer).get(x, y)), x*stepx, y*stepy, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());
        
        for (int i = 0; i < map.getLayersNumber(); i++)
            if (i <= usedLayer)
                drawLayer((Graphics2D)g, 1.0f, i);
            else
                drawLayer((Graphics2D)g, 0.2f, i);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                usedLayer = (usedLayer + 1) % map.getLayersNumber();
                repaint();
            }
        }
    }
    
    
}
