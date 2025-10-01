package org.practice.lld.logadapter;

public class Main {
    public static void main(String[] args) {

        LoggerEngine logger = new LoggerEngine(new LegacyLoggerAdapter());

        logger.log(new LogMessage("This is legacy log", LogLevel.DEBUG));

        logger = new LoggerEngine(new OtherLoggerAdapter());

        logger.log(new LogMessage("This is other log", LogLevel.INFO));

    }
}

enum LogLevel{
    INFO, DEBUG, ERROR;
}

interface Logger{
    void log(LogMessage message);
}

class LegacyLoggerAdapter implements Logger{

    LegacyLogger logger;

    public LegacyLoggerAdapter() {
        this.logger = new LegacyLogger();
    }

    @Override
    public void log(LogMessage message) {
        logger.writeLog(message.getMsg(), message.getLevel());
    }
}

class OtherLoggerAdapter implements Logger{

    OtherLogger ol;

    public OtherLoggerAdapter() {
        this.ol = new OtherLogger();
    }

    @Override
    public void log(LogMessage message) {
        ol.log(message.getMsg());
    }
}

class LoggerEngine{

    Logger log;

    public LoggerEngine(Logger log) {
        this.log = log;
    }

    void log(LogMessage logMessage){
        log.log(logMessage);
    }
}

class LogMessage{
    String msg;
    LogLevel level;

    public LogMessage(String msg, LogLevel level) {
        this.msg = msg;
        this.level = level;
    }

    public String getMsg() {
        return msg;
    }

    public LogLevel getLevel() {
        return level;
    }
}

class LegacyLogger{
    void writeLog(String msg, LogLevel level){
        System.out.println("["+level+"]"+"Log Message : "+msg);
    }
}

class OtherLogger{
    void log(String message){
        System.out.println("Log Message : "+message);
    }
}