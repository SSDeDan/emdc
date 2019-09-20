package com.briup.server;


import com.briup.bean.Environment;

import java.io.ObjectInputStream;
import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 *
 *      服务器端程序接口
 *
 *
 * @author: ZZZss
 * @Created 2019/9/17 19:30
 */
public interface Server {


    List<Environment> receive(ObjectInputStream ois);

    void close();

}
