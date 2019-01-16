package com.study.rabbitmqDemo.util;

import java.io.IOException;
import java.util.Properties;

public class RabbitmqPropertiesTools {
    private static Properties p = new Properties();

    static{
        try {
            p.load(RabbitmqPropertiesTools.class.getClassLoader().getResourceAsStream("rabbitmq.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return p.getProperty(key);
    }
}
