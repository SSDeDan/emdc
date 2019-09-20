package com.briup.store;

import com.briup.bean.Environment;

import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 *
 *      入库程序接口
 *
 *
 * @author: ZZZss
 * @Created 2019/9/17 11:05
 */
public interface Store {

    void store(List<Environment> envs);



}
