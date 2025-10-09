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

import com.tstamborski.tilemapman.model.ReadonlyShortMap2D;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.util.ErrorUtil;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapRenderer {
    public static BufferedImage getLayerImage(ReadonlyShortMap2D src, Tileset tiles, int zoom) {
        GraphicsConfiguration conf = 
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage img = 
                conf.createCompatibleImage(
                        src.getWidth() * tiles.getTileWidth() * zoom, 
                        src.getHeight() * tiles.getTileHeight() * zoom, 
                        Transparency.TRANSLUCENT
                );
        
        renderLayer(img, src, tiles, zoom);
        
        return img;
    }
    
    public static void renderLayer(BufferedImage dst, ReadonlyShortMap2D src, Tileset tiles, int zoom) {
        Graphics2D g2d = dst.createGraphics();
        renderLayer(g2d, src, tiles, zoom);
    }
    
    public static void renderLayer(Graphics2D g2d, ReadonlyShortMap2D src, Tileset tiles, int zoom) {
        int stepx = tiles.getTileWidth() * zoom;
        int stepy = tiles.getTileHeight() * zoom;
        
        g2d.setComposite(AlphaComposite.Src);
        
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                try {
                    g2d.drawImage(tiles.getTile(src.get(x, y)), x*stepx, y*stepy, stepx, stepy, null);
                } catch(IndexOutOfBoundsException ex) {
                    ErrorUtil.logError(ex, Level.WARNING, "Tileset is smaller than value in source tilemap.");
                    g2d.drawImage(tiles.getTile(src.get(x, y) % tiles.getSize()), x*stepx, y*stepy, stepx, stepy, null);
                }
            }
        }
    }
}
