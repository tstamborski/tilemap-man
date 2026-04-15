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

import com.tstamborski.tilemapman.util.TilemapRenderer;
import com.tstamborski.tilemapman.model.DataModifyEvent;
import com.tstamborski.tilemapman.model.DataModifyListener;
import com.tstamborski.tilemapman.model.LayerMask;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapView extends AbstractLayeredView implements DataModifyListener {
    private int zoom;
    private Tileset set;
    private TilemapProject map;
    
    public TilemapView() {
        zoom = 1;
    }
    
    public void setZoom(int zoom) {
        if (this.zoom == zoom)
            return;
        
        this.zoom = zoom;
        revalidateLayers();
    }
    
    public void setTileset(Tileset tileset) {
        if (this.set == tileset)
            return;
        
        this.set = tileset;
        revalidateLayers();
    }
    
    public void setTilemapProject(TilemapProject project) {
        if (this.map == project)
            return;
        
        if (this.map != null) 
            this.map.removeDataModifyListener(this);
        
        project.addDataModifyListener(this);
        this.map = project;
        
        revalidateLayers();
    }

    public int getZoom() {
        return zoom;
    }

    public Tileset getTileset() {
        return set;
    }

    public TilemapProject getTilemapProject() {
        return map;
    }
    
    @Override
    public void dataModified(DataModifyEvent event) {
        if (event.isResized()) {
            revalidateLayers();
        } else {
            repaintLayers(event.getLayerMask());
        }
    }
    
    protected void repaintLayers(LayerMask mask) {
        for (int i = 0; i < map.getLayersNumber(); i++) {
            if (mask.isLayerModified(i)) {
                if (i < getLayersNumber())
                    TilemapRenderer.renderLayer(getLayer(i), map.getLayer(i), set, zoom);
                else
                    addLayer(TilemapRenderer.getLayerImage(map.getLayer(i), set, zoom));
            }
        }
        for (int i = map.getLayersNumber(); i < getLayersNumber(); i++) {
            removeLayer(getLayersNumber() - 1);
        }
        repaint();
    }
    
    protected void revalidateLayers() {
        if (set != null && map != null) {
            removeAllLayers();
            map.forEachLayer(m2d -> addLayer(TilemapRenderer.getLayerImage(m2d, set, zoom)));
            
            setEnabled(true);
            pack();
        } else {
            setEnabled(false);
        }
    }
}
