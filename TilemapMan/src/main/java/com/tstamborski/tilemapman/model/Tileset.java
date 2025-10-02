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

import com.tstamborski.util.ColorUtil;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class Tileset {
    BufferedImage tilesetImage;
    BufferedImage[] subImages;
    int tileWidth, tileHeight;
    int tileNumber;
    
    private void createSubImages() {
      subImages = new BufferedImage[tileNumber];
      
      int i = 0;
      int x, y;
      int xcapacity = tilesetImage.getWidth() / tileWidth;
      while (i < tileNumber) {
          x = (i % xcapacity) * tileWidth;
          y = (i / xcapacity) * tileHeight;
          subImages[i] = tilesetImage.getSubimage(x, y, tileWidth, tileHeight);
          
          i++;
      }
    }
    
    public BufferedImage getTile(int index) {
        if (index < 0 || index >= tileNumber)
            return null;
        
        return subImages[index];
    }
    
    public int getTileIndexAt(int x, int y) {
        return (x / tileWidth) * (y / tileHeight);
    }
    
    public int getTileWidth() {
        return tileWidth;
    }
    
    public int getTileHeight() {
        return tileHeight;
    }
    
    public BufferedImage toBufferedImage() {
        return tilesetImage;
    }

    public Tileset(BufferedImage img, int tilew, int tileh, int transparency, boolean limit8bit) {
        tilesetImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tilesetImage.createGraphics();
        
        if (img.getType() == BufferedImage.TYPE_BYTE_INDEXED) {
            img = ColorUtil.makeIndexTrasparent(img, (byte)0);
        }
        
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        
        this.tileWidth = tilew;
        this.tileHeight = tileh;
        int img_capacity = (tilesetImage.getWidth() / tilew) * (tilesetImage.getHeight() / tileh);
        this.tileNumber = 
                limit8bit ? Math.min(img_capacity, 256) : Math.min(img_capacity, Short.MAX_VALUE + 1);
        createSubImages();
    }
}
