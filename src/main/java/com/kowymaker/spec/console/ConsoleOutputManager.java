/**
 * This file is part of Kowy Maker.
 *
 * Kowy Maker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kowy Maker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 */
package com.kowymaker.spec.console;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class used to easily bind console output to an extern file.
 * 
 * @author Koka El Kiwi
 * 
 */
public class ConsoleOutputManager
{
    private final static Logger             global    = Logger.getLogger("");
    
    public final static DebugLevel          DEBUG     = new DebugLevel();
    
    private final static LoggerOutputStream outStream = new LoggerOutputStream(
                                                              global,
                                                              Level.INFO);
    private final static LoggerOutputStream errStream = new LoggerOutputStream(
                                                              global,
                                                              Level.SEVERE);
    
    /**
     * Register filename as System output.
     * 
     * @param fileName
     *            Output filename.
     */
    public static void register(String fileName)
    {
        register(new File(fileName + ".log"));
    }
    
    /**
     * Register file as System output.
     * 
     * @param file
     *            Output file
     */
    public static void register(File file)
    {
        try
        {
            // Setup Logger
            for (final Handler handler : global.getHandlers())
            {
                global.removeHandler(handler);
            }
            
            final ConsoleHandler console = new TerminalConsoleHandler();
            global.addHandler(console);
            
            final FileHandler fileHandler = new FileHandler(
                    file.getAbsolutePath(), 500000, 5, true);
            fileHandler.setFormatter(new LogFormatter());
            global.addHandler(fileHandler);
            
            System.setOut(new PrintStream(outStream, true));
            System.setErr(new PrintStream(errStream, true));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Internal class used for Logger.
     * 
     * @author Koka El Kiwi
     * 
     */
    public static class DebugLevel extends Level
    {
        private static final long serialVersionUID = 4164250706426396472L;
        
        protected DebugLevel()
        {
            super("DEBUG", Level.INFO.intValue() + 53);
        }
        
    }
}
