/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.tstamborski.tilemapman;

import com.tstamborski.gui.TestWindow;
import com.tstamborski.tilemapman.gui.TestView;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.tilemapman.model.TilesetLoader;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapMan {

    public static void main(String[] args) {
        TestWindow test = new TestWindow();
        //String path = "C:/Users/tstam/Documents/aseprite/asm/test-tileset.bmp";
        URL url = TilemapMan.class.getResource("images/test-tileset.bmp");
        Tileset tiles;
        TilemapProject map = new TilemapProject(3, 16, 8);
        
        try {
            tiles = TilesetLoader.fromURL(url, 16, 16);
        } catch (IOException ex) {
            System.exit(-1);
            return;
        }
        
        for (int i = 0; i < map.getWidth() * map.getHeight(); i++)
            map.getLayer(1).set(i % map.getWidth(), i / map.getWidth(), (short)(i % 4));
        for (int i = 0; i < map.getWidth() * map.getHeight(); i++)
            if (i % 4 == 0)
                map.getLayer(2).set(i % map.getWidth(), i / map.getWidth(), (short)(63));
        TestView view = new TestView(map, tiles);
        test.addComponent(view);
        test.setVisible(true);
        
        System.out.println("Tileset resolution: " + tiles.getWidth() + "x" + tiles.getHeight());
        System.out.println("Tileset size in tiles: " + tiles.getSize());
        System.out.println("Tile index at point 20x20: " + tiles.getTileIndexAt(20, 20));
    }
}
