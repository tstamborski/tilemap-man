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
package com.tstamborski.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class ImageLoader {
    public static BufferedImage fromFile(File file) throws IOException {
        return toCompatibleImage(ImageIO.read(file));
    }
    
    public static BufferedImage fromURL(URL url) throws IOException {
        return toCompatibleImage(ImageIO.read(url));
    }
    
    public static BufferedImage toCompatibleImage(BufferedImage src) {
        GraphicsConfiguration conf = 
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        if (src.getColorModel().equals(conf.getColorModel()))
            return src;
        
        BufferedImage comp_img = conf.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
        Graphics2D g = comp_img.createGraphics();
        
        g.drawImage(src, 0, 0, null);
        
        g.dispose();
        return comp_img;
    }
}
