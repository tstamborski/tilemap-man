/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.tstamborski.tilemapman;

import com.tstamborski.gui.TestWindow;
import com.tstamborski.tilemapman.model.Tileset;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapMan {

    public static void main(String[] args) {
        TestWindow test = new TestWindow();
        JLabel label = new JLabel();
        String path = "C:/Users/tstam/Documents/aseprite/asm/test-tileset.bmp";
        Tileset tiles;
        
        try {
            tiles = new Tileset(ImageIO.read(new File(path)), 16, 16, 0, false);
        } catch (IOException ex) {
            System.exit(-1);
            return;
        }
        
        label.setIcon(new ImageIcon(tiles.toBufferedImage()));
        test.addComponent(label);
        test.setVisible(true);
        
        System.out.println("Tileset resolution: " + tiles.getWidth() + "x" + tiles.getHeight());
        System.out.println("Tileset size in tiles: " + tiles.getTileNumber());
        System.out.println("Tile index at point 20x20: " + tiles.getTileIndexAt(20, 20));
    }
}
