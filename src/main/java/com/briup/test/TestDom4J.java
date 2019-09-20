package com.briup.test;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/19 22:20
 */
public class TestDom4J {
    public static void main(String[] args) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(TestDom4J.class.getResourceAsStream("/emdc.xml"));
        //整个XML文档对象，读取该document里面的所有东西
//        System.out.println(document.asXML());
        //获取根节点r
        Element re = document.getRootElement();
        System.out.println(re);

        //获取某个几点的子节点即：
        // emdc的子节点，将所有子节点放在一个List集合里面
        List es = re.elements();
        es.forEach(System.out::println);








    }
}
