package com.briup.client;

import com.briup.bean.Environment;
import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 *
 *          客户端程序接口
 *
 *
 * @author: ZZZss
 * @Created 2019/9/17 19:29
 */
public interface Client {


    void send (List<Environment> envs);
    void close();


}
