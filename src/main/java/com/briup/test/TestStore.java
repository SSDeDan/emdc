package com.briup.test;

import com.briup.bean.Environment;
import com.briup.gather.Gather;
import com.briup.gather.GatherImpl;
import com.briup.store.Store;
import com.briup.store.StoreImpl;
import org.junit.Test;

import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/17 11:29
 */
public class TestStore {
    @Test
    public void testStore(){
        Gather gather=new GatherImpl();

       List<Environment> envs= gather.gather("F:\\radwtmp");
        Store store=new StoreImpl();

        store.store(envs);


    }
}
