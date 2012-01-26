package com.kowymaker.spec.console;

import java.io.PrintStream;

import org.apache.commons.logging.impl.SimpleLog;

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
