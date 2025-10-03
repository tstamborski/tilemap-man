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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilesetLoader {
    public static Tileset fromFile(File file, int tilew, int tileh) throws IOException {
        return fromFile(file, tilew, tileh, Short.MAX_VALUE);
    }
    
    public static Tileset fromFile(File file, int tilew, int tileh, int limit) throws IOException {
        BufferedImage src = ImageIO.read(file);
        
        src = convertToARGB(src);
        BufferedImage[] tiles = slice(src, tilew, tileh, limit);
        return new Tileset(tilew, tileh, tiles, src);
    }

    private static BufferedImage convertToARGB(BufferedImage src) {
        BufferedImage argb_img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = argb_img.createGraphics();
        
        if (src.getType() == BufferedImage.TYPE_BYTE_INDEXED)
            src = ColorUtil.makeIndexTrasparent(src, (byte)0);
        
        g.drawImage(src, 0, 0, null);
        g.dispose();
        
        return argb_img;
    }

    private static BufferedImage[] slice(BufferedImage src, int tilew, int tileh, int limit) {
        int img_capacity = (src.getWidth() / tilew) * (src.getHeight() / tileh);
        BufferedImage[] tiles = new BufferedImage[Math.min(img_capacity, limit)];
        
        int i = 0;
        int x, y;
        int columns = src.getWidth() / tilew;
        while (i < tiles.length) {
            x = (i % columns) * tilew;
            y = (i / columns) * tileh;
            tiles[i] = src.getSubimage(x, y, tilew, tileh);
            i++;
        }
        
        return tiles;
    }
}
