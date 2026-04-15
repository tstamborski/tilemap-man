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

import com.tstamborski.tilemapman.tools.DrawingTool;
import com.tstamborski.tilemapman.model.ShortMap2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class BasicTilemapEdit extends TilemapView {
    private ShortMap2D pattern;
    private DrawingTool tool;
    
    
    public BasicTilemapEdit() {
        enableEvents(MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);
    }
    
    public void setTool(DrawingTool tool) {
        tool.setPattern(pattern);
        tool.setProject(getTilemapProject());
        this.tool = tool;
    }
    
    public void setPattern(ShortMap2D pattern) {
        this.pattern = pattern;
        tool.setPattern(pattern);
    }

    public ShortMap2D getPattern() {
        return pattern;
    }

    public DrawingTool getTool() {
        return tool;
    }
    
    public int getTilemapX(int mousex) {
        if (mousex < 0)
            return 0;
        if (mousex > getTilemapProject().getWidth() * getTileset().getTileWidth() * getZoom())
            return getTilemapProject().getWidth() - 1;
        
        return mousex / (getTileset().getTileWidth() * getZoom());
    }
    
    public int getTilemapY(int mousey) {
        if (mousey < 0)
            return 0;
        if (mousey > getTilemapProject().getHeight() * getTileset().getTileHeight() * getZoom())
            return getTilemapProject().getHeight() - 1;
        
        return mousey / (getTileset().getTileHeight() * getZoom());
    }
    
    @Override
    protected void processMouseEvent(MouseEvent event) {
        super.processMouseEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_PRESSED && event.getButton() == MouseEvent.BUTTON1) {
            tool.press(getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        } if (event.getID() == MouseEvent.MOUSE_RELEASED && event.getButton() == MouseEvent.BUTTON1) {
            tool.release(getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent event) {
        super.processMouseMotionEvent(event);
        
        if (!isEnabled())
            return;
        
        if (event.getID() == MouseEvent.MOUSE_DRAGGED && (event.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
            tool.apply(getWorkLayer(), getTilemapX(event.getX()), getTilemapY(event.getY()));
        }
    }
}
