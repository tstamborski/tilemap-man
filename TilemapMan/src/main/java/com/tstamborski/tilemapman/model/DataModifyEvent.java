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

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class DataModifyEvent {
    private final LayerMask layerMask;
    private final boolean resized;
    
    public DataModifyEvent(LayerMask mask, boolean resized) {
        this.layerMask = mask.copy();
        this.resized = resized;
    }
    
    public boolean isResized() {
        return resized;
    }
    
    public boolean isLayerModified(int index) {
        return layerMask.isLayerModified(index);
    }
    
    public boolean isAllLayersModified() {
        return layerMask.isAllLayersModified();
    }
    
    public LayerMask getLayerMask() {
        return layerMask;
    }
}
