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
package com.tstamborski.tilemapman.gui;

import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class EditMenu extends JMenu {
    public JMenuItem undo, redo;
    public JRadioButtonMenuItem draw, stamp, fill, eraser;
    
    public EditMenu(String title) {
        super(title);
        
        undo = new JMenuItem("Undo", new ImageIcon(getClass().getResource("icons/undo16.png")));
        undo.setMnemonic(KeyEvent.VK_U);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        redo = new JMenuItem("Redo", new ImageIcon(getClass().getResource("icons/redo16.png")));
        redo.setMnemonic(KeyEvent.VK_R);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
        
        ButtonGroup toolsGroup = new ButtonGroup();
        draw = new JRadioButtonMenuItem("Draw", new ImageIcon(getClass().getResource("icons/pencil16.png")));
        draw.setMnemonic(KeyEvent.VK_D);
        draw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
        stamp = new JRadioButtonMenuItem("Stamp", new ImageIcon(getClass().getResource("icons/stamp16.png")));
        stamp.setMnemonic(KeyEvent.VK_S);
        stamp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        fill = new JRadioButtonMenuItem("Flood Fill", new ImageIcon(getClass().getResource("icons/paint-bucket16.png")));
        fill.setMnemonic(KeyEvent.VK_F);
        fill.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
        eraser = new JRadioButtonMenuItem("Eraser", new ImageIcon(getClass().getResource("icons/rubber16.png")));
        eraser.setMnemonic(KeyEvent.VK_E);
        eraser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
        toolsGroup.add(draw);
        toolsGroup.add(stamp);
        toolsGroup.add(fill);
        toolsGroup.add(eraser);
        draw.setSelected(true);
        
        add(undo);
        add(redo);
        addSeparator();
        add(draw);
        add(stamp);
        add(fill);
        add(eraser);
    }
}
