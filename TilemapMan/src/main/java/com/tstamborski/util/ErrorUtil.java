
package com.tstamborski.util;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tobiasz Stamborski <tstamborski@outlook.com>
 */
public class ErrorUtil {
    public static void logError(Exception ex, Level level) {
        Logger.getLogger(ErrorUtil.class.getName()).log(level, ex.getMessage(), ex);
    }
    
    public static void logError(Exception ex, Level level, String comment) {
        Logger.getLogger(ErrorUtil.class.getName()).log(level, ex.getMessage() + "\n" + comment, ex);
    }
    
    public static void showError(Component parent, String str) {
        JOptionPane.showMessageDialog(parent, str, "Error!", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void criticalError(Exception ex, String description) {
        logError(ex, Level.SEVERE);
        
        if (description == null)
            showError(null, ex.getMessage());
        else
            showError(null, ex.getMessage() + "\n" + description);
        
        System.exit(-1);
    }
    
    public static void casualError(Exception ex, String description) {
        logError(ex, Level.WARNING);
        
        if (description == null)
            showError(null, ex.getMessage());
        else
            showError(null, ex.getMessage() + "\n" + description);
    }
}
