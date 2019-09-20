package com.briup.test;

import com.briup.bean.Environment;
import com.briup.gather.Gather;
import com.briup.gather.GatherImpl;
import org.junit.Test;

import java.util.List;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/17 10:03
 */
public class TestGather {
    
    
    
    @Test
    public  void testGather(){
        Gather gather=new GatherImpl();
      List<Environment> envs = gather.gather("F:\\radwtmp");
      envs.forEach(System.out::println);




    }

}
