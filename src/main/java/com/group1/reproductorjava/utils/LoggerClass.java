package com.group1.reproductorjava.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerClass {
    private final Logger logger;

    public LoggerClass(String className) {
        logger = Logger.getLogger(className);
    }

    public void info(String msg){
        logger.log(Level.INFO, msg);
    }

    public void servere(String msg){
        logger.log(Level.SEVERE, msg);
    }

    public void warning(String msg){
        logger.log(Level.WARNING, msg);
    }
}
