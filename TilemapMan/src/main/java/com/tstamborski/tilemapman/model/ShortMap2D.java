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

import java.util.Arrays;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class ShortMap2D {
    private short[] data;
    int width, height;
    
    public ShortMap2D(int width, int height) {
        data = new short[width * height];
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getSize() {
        return data.length;
    }
    
    public short get(int x, int y) {
        return data[y*width + x];
    }
    
    public void set(int x, int y, short val) {
        data[y*width + x] = val;
    }
    
    public void resize(int new_width, int new_height) {
        short[] new_data = new short[new_width * new_height];
        int minw = Math.min(new_width, width);
        int minh = Math.min(new_height, height);
        
        for (int y = 0; y < minh; y++) {
            for (int x = 0; x < new_width; x++) {
                if (x < minw)
                    new_data[y * new_width + x] = data[y * width + x];
            }
        }
        
        this.data = new_data;
        this.width = new_width;
        this.height = new_height;
    }
    
    public void clear(short val) {
        Arrays.fill(data, val);
    }
}
