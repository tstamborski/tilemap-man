/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.tstamborski.tilemapman;

import com.tstamborski.tilemapman.tools.StampTool;
import com.tstamborski.gui.TabbedWindow;
import com.tstamborski.tilemapman.commands.CommandManager;
import com.tstamborski.tilemapman.gui.MainMenu;
import com.tstamborski.tilemapman.gui.EnhancedTilemapEdit;
import com.tstamborski.tilemapman.gui.TilesetPicker;
import com.tstamborski.tilemapman.model.TilemapProject;
import com.tstamborski.tilemapman.model.Tileset;
import com.tstamborski.tilemapman.model.TilesetLoader;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class TilemapMan {

    public static void main(String[] args) {
        TabbedWindow testWnd = new TabbedWindow();
        MainMenu menu = new MainMenu();
        CommandManager cmdManager = new CommandManager();
        StampTool stamp = new StampTool(cmdManager);
        URL url = TilemapMan.class.getResource("images/test-tileset.bmp");
        Tileset tiles;
        TilemapProject map = new TilemapProject(2, 8, 8);
        
        try {
            tiles = TilesetLoader.fromURL(url, 16, 16, 20);
        } catch (IOException ex) {
            System.exit(-1);
            return;
        }
        
        //for (int i = 0; i < map.getWidth() * map.getHeight(); i++)
            //map.getLayer(0).set(i % map.getWidth(), i / map.getWidth(), (short)(i));
        
        menu.edit.undo.addActionListener(ae->cmdManager.undo());
        menu.edit.redo.addActionListener(ae->cmdManager.redo());
        
        EnhancedTilemapEdit edit = new EnhancedTilemapEdit();
        edit.setBackground(new Color(0xAA, 0xAA, 0xFF));
        edit.setZoom(2);
        edit.setTilemapProject(map);
        edit.setTileset(tiles);
        edit.setTool(stamp);
        
        TilesetPicker picker = new TilesetPicker();
        picker.setBackground(Color.pink);
        picker.setTileset(tiles);
        picker.setZoom(1);
        picker.setActionListener(ae -> edit.setPattern(picker.getSelectionPattern()));
        
        testWnd.addComponentTab("TILEMAP", edit);
        testWnd.addComponentTab("TILESET", picker);
        testWnd.setJMenuBar(menu);
        testWnd.setTitle(TilemapMan.class.getSimpleName());
        testWnd.setVisible(true);
        
        System.out.println("Tileset resolution: " + tiles.getOriginalWidth() + "x" + tiles.getOriginalHeight());
        System.out.println("Tileset size in tiles: " + tiles.getSize());
        System.out.println("Tile index at point 20x20: " + tiles.getTileIndexAt(20, 20));
    }
}
