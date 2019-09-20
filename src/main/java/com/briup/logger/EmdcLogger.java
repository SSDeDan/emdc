package com.briup.logger;


import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/20 11:14
 */
public class EmdcLogger {
    public static void main(String[] args) {
        Logger logger = Logger.getRootLogger();

//        Logger rootLogger = Logger.getLogger("rootLogger");

        System.out.println(logger);
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.fatal("fatal");
//        System.out.println("============查看系统配置信息，打印JVM所了解的属性值===========");
//        Properties properties=System.getProperties();
//        properties.list(System.out);
    }
}
