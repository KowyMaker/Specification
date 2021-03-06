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

import java.util.logging.ConsoleHandler;

/**
 * Internal ConsoleHandler used by {@link ConsoleOutputManager}.
 * 
 * @author Koka El Kiwi
 * 
 */
public class TerminalConsoleHandler extends ConsoleHandler
{
    public TerminalConsoleHandler()
    {
        super();
        setOutputStream(System.out);
        setFormatter(new LogFormatter());
    }
    
    @Override
    public synchronized void flush()
    {
        super.flush();
    }
}
