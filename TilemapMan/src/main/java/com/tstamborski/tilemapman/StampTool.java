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

import com.tstamborski.tilemapman.commands.CommandManager;
import com.tstamborski.tilemapman.commands.CompositeCommand;
import com.tstamborski.tilemapman.commands.StampCommand;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class StampTool extends AbstractDrawingTool {
    private CompositeCommand compCmd;
    
    public StampTool(CommandManager manager) {
        super(manager);
    }

    @Override
    public void press(int layer, int x, int y) {
        compCmd = new CompositeCommand();
        apply(layer, x, y);
    }

    @Override
    public void apply(int layer, int x, int y) {
        StampCommand cmd = new StampCommand(project, layer, x, y, pattern);
        cmd.execute();
        compCmd.add(cmd);
    }

    @Override
    public void release(int layer, int x, int y) {
        cmdManager.add(compCmd);
    }
}
