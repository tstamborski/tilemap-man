/*
 * The MIT License
 *
 * Copyright 2023 Tobiasz Stamborski <tstamborski@outlook.com>.
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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class ColorUtil {
    public static Color getDarker(Color c, int step) {
        int r, g, b, a;
        
        a = c.getAlpha();
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        
        r -= step;
        g -= step;
        b -= step;
        if (r < 0)
            r = 0;
        if (g < 0)
            g = 0;
        if (b < 0)
            b = 0;
        if (r > 0xff)
            r = 0xff;
        if (g > 0xff)
            g = 0xff;
        if (b > 0xff)
            b = 0xff;
        
        return new Color(r, g, b, a);
    }
    
    public static Color getBrighter(Color c, int step) {
        int r, g, b, a;
        
        a = c.getAlpha();
        r = c.getRed();
        g = c.getGreen();
        b = c.getBlue();
        
        r += step;
        g += step;
        b += step;
        if (r < 0)
            r = 0;
        if (g < 0)
            g = 0;
        if (b < 0)
            b = 0;
        if (r > 0xff)
            r = 0xff;
        if (g > 0xff)
            g = 0xff;
        if (b > 0xff)
            b = 0xff;
        
        return new Color(r, g, b, a);
    }
    
    public static BufferedImage makeIndexTrasparent(BufferedImage img, byte index) {
        if (img.getType() != BufferedImage.TYPE_BYTE_INDEXED)
            throw new IllegalArgumentException("BufferedImage must be type TYPE_BYTE_INDEXED.");
        
        IndexColorModel icm = (IndexColorModel)img.getColorModel();
        int icm_size = icm.getMapSize();
        byte[] a = new byte[icm_size];
        byte[] r = new byte[icm_size];
        byte[] g = new byte[icm_size];
        byte[] b = new byte[icm_size];
        icm.getAlphas(a);
        icm.getReds(r);
        icm.getGreens(g);
        icm.getBlues(b);
        if (index < icm_size)
            a[index] = 0;
        
        IndexColorModel icm2 = new IndexColorModel(8, icm_size, r, g, b, a);
        BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, icm2);
        img2.setData(img.getData());
        
        return img2;
    }
    
    public static BufferedImage makeARGBTransparent(BufferedImage img, int argb) {
        if (img.getType() != BufferedImage.TYPE_INT_ARGB)
            throw new IllegalArgumentException("BufferedImage must be type TYPE_INT_ARGB.");
        
        BufferedImage new_img = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int pixel;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                pixel = img.getRGB(x, y);
                if (pixel == argb)
                    new_img.setRGB(x, y, 0x00000000);
                else
                    new_img.setRGB(x, y, pixel);
            }
        }
        
        return new_img;
    }
}
