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

import java.io.PrintStream;

import org.apache.commons.logging.impl.SimpleLog;

/**
 * Main logger which output to a specific OutputStream.
 * 
 * @author Koka El Kiwi
 * 
 */
public class ParentLog extends SimpleLog
{
    private static final long serialVersionUID = -7901568994222905177L;
    
    private final OutLog      out;
    private final OutLog      err;
    
    public ParentLog(String name)
    {
        super(name);
        out = new OutLog(name, System.out);
        err = new OutLog(name, System.err);
    }
    
    @Override
    protected void log(int type, Object message, Throwable t)
    {
        switch (type)
        {
            case SimpleLog.LOG_LEVEL_TRACE:
                out.log(type, message, t);
                break;
            case SimpleLog.LOG_LEVEL_DEBUG:
                out.log(type, message, t);
                break;
            case SimpleLog.LOG_LEVEL_INFO:
                out.log(type, message, t);
                break;
            case SimpleLog.LOG_LEVEL_WARN:
                err.log(type, message, t);
                break;
            case SimpleLog.LOG_LEVEL_ERROR:
                err.log(type, message, t);
                break;
            case SimpleLog.LOG_LEVEL_FATAL:
                err.log(type, message, t);
                break;
        }
    }
    
    private static class OutLog extends SimpleLog
    {
        private static final long serialVersionUID = -7901568994336905177L;
        
        private final PrintStream output;
        
        public OutLog(String name, PrintStream output)
        {
            super(name);
            this.output = output;
        }
        
        @Override
        public void log(int type, Object message, Throwable t)
        {
            super.log(type, message, t);
        }
        
        @Override
        protected void write(StringBuffer buffer)
        {
            output.println(buffer.toString());
        }
        
    }
}
