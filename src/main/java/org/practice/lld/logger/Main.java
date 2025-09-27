package org.practice.lld.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Logger log = Logger.getInstance();
        log.error("This is a sensitive, error log");
        log.info("This is a regular info level log");
        log.debug("This is a debug level, little sensitive/complex log");
        log.error("This is a sensitive, error log");
    }
}

enum LogLevel{
    INFO, DEBUG, ERROR;
}

class Log{
    LogLevel logLevel;
    String msg;

    public Log(LogLevel logLevel, String msg) {
        this.logLevel = logLevel;
        this.msg = msg;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMsg() {
        return msg;
    }

    public String format(){
        return logLevel.toString() + ": [" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "] " + msg;
    }
}

abstract class LoggerProcessor{
    LoggerProcessor nextLoggerProcessor;
    LoggerProcessor(LoggerProcessor nextLoggerProcessor){
        this.nextLoggerProcessor = nextLoggerProcessor;
    }
    void log(Log logMsg){
        if(nextLoggerProcessor != null) nextLoggerProcessor.log(logMsg);
    }
}

class InfoLogger extends LoggerProcessor{

    InfoLogger(LoggerProcessor nextLoggerProcessor) {
        super(nextLoggerProcessor);
    }

    @Override
    void log(Log logMsg) {
        if(logMsg.getLogLevel() == LogLevel.INFO){
            System.out.println(logMsg.format());
            return;
        }
        super.log(logMsg);
    }
}

class DebugLogger extends LoggerProcessor{

    DebugLogger(LoggerProcessor nextLoggerProcessor) {
        super(nextLoggerProcessor);
    }

    @Override
    void log(Log logMsg) {
        if(logMsg.getLogLevel() == LogLevel.DEBUG){
            System.out.println(logMsg.format());
            return;
        }
        super.log(logMsg);
    }
}

class ErrorLogger extends LoggerProcessor{

    ErrorLogger(LoggerProcessor nextLoggerProcessor) {
        super(nextLoggerProcessor);
    }

    @Override
    void log(Log logMsg) {
        if(logMsg.getLogLevel() == LogLevel.ERROR){
            System.out.println(logMsg.format());
            return;
        }
        super.log(logMsg);
    }
}

class Logger{
    private Logger(){}
    static Logger instance;

    LoggerProcessor loggerProcessor = new InfoLogger(new DebugLogger(new ErrorLogger(null)));

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    void info(String msg){
        loggerProcessor.log(new Log(LogLevel.INFO, msg));
    }

    void debug(String msg){
        loggerProcessor.log(new Log(LogLevel.DEBUG, msg));
    }

    void error(String msg){
        loggerProcessor.log(new Log(LogLevel.ERROR, msg));
    }
}
