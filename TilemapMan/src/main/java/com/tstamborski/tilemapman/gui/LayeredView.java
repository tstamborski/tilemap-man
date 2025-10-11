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

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class LayeredView extends JComponent {
    private final ArrayList<BufferedImage> layers;
    private LayerRenderer renderer;
    private int workLayer;
    
    public LayeredView() {
        layers = new ArrayList<>();
        renderer = new DefaultLayerRenderer();
    }

    public LayerRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(LayerRenderer renderer) {
        this.renderer = renderer;
        repaint();
    }
    
    public void addLayer(BufferedImage img) {
        layers.add(img);
    }
    
    public void removeLayer(int index) {
        layers.remove(index);
    }
    
    public void removeAllLayers() {
        layers.clear();
    }
    
    public BufferedImage getLayer(int index) {
        return layers.get(index);
    }
    
    public int getLayersNumber() {
        return layers.size();
    }
    
    public int getWorkLayer() {
        return workLayer;
    }

    public void setWorkLayer(int workLayer) {
        this.workLayer = workLayer;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!isEnabled() || !isVisible())
            return;
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setBackground(getBackground());
        g2d.clearRect(0, 0, getWidth(), getHeight());
        
        renderer.render(g2d, layers, workLayer);
        
        g2d.setComposite(AlphaComposite.SrcOver);
    }
    
    public void pack() {
        int w = 0;
        int h = 0;
        
        for (int i = 0; i < layers.size(); i++) {
            BufferedImage img = layers.get(i);
            if (img.getWidth() > w)
                w = img.getWidth();
            if (img.getHeight() > h)
                h = img.getHeight();
        }
        
        setPreferredSize(new Dimension(w, h));
    }
}
