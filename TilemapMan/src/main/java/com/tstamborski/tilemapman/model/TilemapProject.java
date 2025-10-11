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
import java.util.function.Consumer;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapProject extends DataModel {
    public final int MAX_LAYERS = LayerMask.MAX_LAYERS;
    
    private final ArrayList<ShortMap2D> layers;
    int width, height;
    
    public TilemapProject(int nlayers, int w, int h) {
        this.width = w;
        this.height = h;
        this.layers = new ArrayList<>(MAX_LAYERS);
        
        for (int i = 0; i < nlayers; i++)
            pushEmptyLayer();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public FixedShortMap2D getLayer(int index) {
        getDataModifyTrigger().setLayerModified(index);
        return layers.get(index);
    }
    
    public void forEachLayer(Consumer<FixedShortMap2D> consumer) {
        layers.forEach(consumer);
        getDataModifyTrigger().setAllLayersModified();
    }
    
    public int getLayersNumber() {
        return layers.size();
    }
    
    public final void pushEmptyLayer() {
         if (layers.size() >= MAX_LAYERS)
            throw new IndexOutOfBoundsException("Tilemap has maximum number of layers already.");
        
        layers.add(new ShortMap2D(width, height));
        getDataModifyTrigger().setLayerModified(layers.size() - 1);
    }
    
    public void pushLayer(ShortMap2D layer) {
        if (layers.size() >= MAX_LAYERS)
            throw new IndexOutOfBoundsException("Tilemap has maximum number of layers already.");
        
        if (layer.getWidth() != width || layer.getHeight() != height)
            throw new IllegalArgumentException("Invalid size of ShortMap2D.");
        
        layers.add(layer);
        getDataModifyTrigger().setLayerModified(layers.size() - 1);
    }
    
    public ShortMap2D popLayer() {
        getDataModifyTrigger().setLayerModified(layers.size() - 1);
        return layers.remove(layers.size() - 1);
    }
    
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        
        layers.forEach(layer -> layer.resize(w, h));
        getDataModifyTrigger().setResized(true);
    }
        
    public TilemapProject deepCopy() {
        TilemapProject copy = new TilemapProject(0, width, height);
        
        for (int i = 0; i < layers.size(); i++)
            copy.layers.add(layers.get(i).deepCopy());
        
        getDataModifyTrigger().forEach(listener -> copy.getDataModifyTrigger().add(listener));
        
        return copy;
    }
}
