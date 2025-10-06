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
package com.tstamborski.tilemapman.model;

import java.util.ArrayList;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapProject {
    private final ArrayList<ShortMap2D> layers;
    int width, height;
    
    public TilemapProject(int nlayers, int w, int h) {
        this.width = w;
        this.height = h;
        this.layers = new ArrayList<>();
        
        for (int i = 0; i < nlayers; i++)
            pushEmptyLayer();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public short get(int layer, int x, int y) {
        return layers.get(layer).get(x, y);
    }
    
    public void set(int layer, int x, int y, short val) {
        layers.get(layer).set(x, y, val);
    }
    
    public int getLayersNumber() {
        return layers.size();
    }
    
    public final void pushEmptyLayer() {
        layers.add(new ShortMap2D(width, height));
    }
    
    public void pushLayer(ShortMap2D layer) {
        if (layer.getWidth() != width || layer.getHeight() != height)
            throw new IllegalArgumentException("Invalid size of ShortMap2D.");
        
        layers.add(layer);
    }
    
    public ShortMap2D popLayer() {
        ShortMap2D layer = layers.get(layers.size()-1);
        layers.remove(layers.size()-1);
        return layer;
    }
    
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        layers.forEach(layer -> layer.resize(w, h));
    }
    
    public void clear(int layer, short clearvalue) {
        layers.get(layer).clear(clearvalue);
    }
    
    public void copyLayerFrom(int layer, ShortMap2D src) {
        layers.get(layer).copyFrom(src);
    }
    
    public ShortMap2D getLayerCopy(int layer) {
        return layers.get(layer).deepCopy();
    }
    
    public short[] exportLayerData(int layer) {
        return layers.get(layer).toShortArray();
    }
    
    public TilemapProject deepCopy() {
        TilemapProject copy = new TilemapProject(0, width, height);
        for (int i = 0; i < layers.size(); i++)
            copy.layers.add(layers.get(i).deepCopy());
        return copy;
    }
}
