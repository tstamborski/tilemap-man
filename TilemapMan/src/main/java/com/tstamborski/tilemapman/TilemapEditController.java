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
package com.tstamborski.tilemapman;

import com.tstamborski.tilemapman.gui.TilemapEdit;
import com.tstamborski.tilemapman.model.DataModifyEvent;
import com.tstamborski.tilemapman.model.DataModifyListener;
import com.tstamborski.tilemapman.model.ShortMap2D;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.util.ImageLoader;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapEditController implements DataModifyListener {
    private final TilemapEdit view;
    private final PatternFromTilemap selectMaker;
    
    private SelectionImage selectImage;
    private BufferedImage sorryImage;
    
    private ShortMap2D pattern;
    private TilemapProject project;
    private DrawingTool tool;
    private Tileset tileset;
    
    private int zoom;
    private boolean isSelect;
    
    public TilemapEditController(TilemapEdit view) {
        this.view = view;
        this.selectMaker = new PatternFromTilemap();
        this.zoom = 1;
        
        try {
            sorryImage = ImageLoader.fromURL(getClass().getResource("images/sorry-notileset.png"));
        } catch (IOException ex) {
            if (sorryImage == null)
                sorryImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public boolean isSelection() {
        return isSelect;
    }
    
    public BufferedImage getSelectionImage() {
        return selectImage;
    }
    
    public Point getSelectionUpperLeft() {
        Point p = selectMaker.getUpperLeft();
        p.setLocation(
                p.x * tileset.getTileWidth() * zoom, 
                p.y * tileset.getTileHeight() * zoom
            );
        return p;
    }
    
    public Point getSelectionLowerRight() {
        Point p = selectMaker.getLowerRight();
        p.setLocation(
                (p.x + 1) * tileset.getTileWidth() * zoom, 
                (p.y + 1) * tileset.getTileHeight() * zoom
            );
        return p;
    }
    
    public void setTilemapProject(TilemapProject project) {
        this.project = project;
        tool.setProject(project);
        
        project.addDataModifyListener(this);
        createImages();
        
        view.pack();
        view.repaint();
    }
    
    public void setTileset(Tileset set) {
        this.tileset = set;
        createSelectionImage();
        createImages();
        
        view.pack();
        view.repaint();
    }
    
    public void setZoom(int zoom) {
        this.zoom = zoom;
        createSelectionImage();
        createImages();
        
        view.pack();
        view.repaint();
    }
    
    public void setPattern(ShortMap2D pattern) {
        this.pattern = pattern;
        tool.setPattern(pattern);
    }
    
    public ShortMap2D getPattern() {
        return pattern;
    }
    
    public void setTool(DrawingTool tool) {
        tool.setPattern(pattern);
        tool.setProject(project);
        this.tool = tool;
    }
    
    public void processMouseEvent(MouseEvent event) {
        if (project == null || tileset == null)
            return;
        
        if (event.getID() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseEvent.BUTTON1) {
            tool.press(view.getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        } if (event.getID() == MouseEvent.MOUSE_DRAGGED && (event.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
            tool.apply(view.getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        } if (event.getID() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseEvent.BUTTON1) {
            tool.release(view.getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        }
        
        if (event.getID() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseEvent.BUTTON3) {
            selectMaker.setTilemapLayer(project.getLayer(view.getWorkLayer()));
            selectMaker.setStartPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            selectMaker.setEndPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            isSelect = true;
            view.repaint();
        } if (event.getID() == MouseEvent.MOUSE_DRAGGED && isSelect) {
            selectMaker.setEndPoint(getTilemapX(event.getX()), getTilemapY(event.getY()));
            view.repaint();
        } if (event.getID() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseEvent.BUTTON3) {
            setPattern(selectMaker.get());
            isSelect = false;
            view.repaint();
        }
    }
    
    private void createImages() {
        view.removeAllLayers();
        
        if (project == null || tileset == null) {
            view.addLayer(sorryImage);
            return;
        }
        
        for (int i = 0; i < project.getLayersNumber(); i++) {
            view.addLayer(TilemapRenderer.getLayerImage(project.getLayer(i), tileset, zoom));
        }
    }
    
    private void createSelectionImage() {
        if (tileset == null) return;
        
        selectImage = new SelectionImage(tileset.getTileWidth() * zoom, tileset.getTileHeight() * zoom);
    }
    
    private int getTilemapX(int mousex) {
        if (mousex < 0)
            return 0;
        if (mousex > project.getWidth() * tileset.getTileWidth() * zoom)
            return project.getWidth() - 1;
        
        return mousex / (tileset.getTileWidth() * zoom);
    }
    
    private int getTilemapY(int mousey) {
        if (mousey < 0)
            return 0;
        if (mousey > project.getHeight() * tileset.getTileHeight() * zoom)
            return project.getHeight() - 1;
        
        return mousey / (tileset.getTileHeight() * zoom);
    }

    @Override
    public void dataModified(DataModifyEvent event) {
        if (event.isResized()) {
            createImages();
            view.repaint();
        } else {
            for (int i = 0; i < project.getLayersNumber(); i++) {
                if (event.isLayerModified(i)) {
                    if (i < view.getLayersNumber())
                        TilemapRenderer.renderLayer(view.getLayer(i), project.getLayer(i), tileset, zoom);
                    else
                        view.addLayer(TilemapRenderer.getLayerImage(project.getLayer(i), tileset, zoom));
                }
            }
            for (int i = project.getLayersNumber(); i < view.getLayersNumber(); i++) {
                view.removeLayer(view.getLayersNumber() - 1);
            }
            view.repaint();
        }
    }
}
