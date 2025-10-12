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
package com.tstamborski.tilemapman.commands;

import java.util.Stack;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class CommandManager {
    private final Stack<Command> undoStack, redoStack;
    
    public CommandManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    
    public boolean hasUndo() {
        return !undoStack.empty();
    }
    
    public boolean hasRedo() {
        return !redoStack.empty();
    }
    
    public void undo() {
        Command cmd = undoStack.pop();
        cmd.undo();
        redoStack.push(cmd);
    }
    
    public void redo() {
        Command cmd = redoStack.pop();
        cmd.execute();
        undoStack.push(cmd);
    }
    
    public void add(Command cmd) {
        if (!redoStack.empty())
            redoStack.clear();
        
        undoStack.push(cmd);
    }
    
    public void execute(Command cmd) {
        cmd.execute();
        add(cmd);
    }
}
