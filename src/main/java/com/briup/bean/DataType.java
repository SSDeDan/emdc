package com.briup.bean;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/17 9:24
 */
public enum DataType {

    CO2("二氧化碳"),TMP("温度"),HUM("湿度"),ILL("光照强度");

    private  String name;

    DataType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
