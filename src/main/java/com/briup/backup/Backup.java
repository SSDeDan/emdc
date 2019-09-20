package com.briup.backup;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 *
 *          备份程序接口
 *
 * @author: ZZZss
 * @Created 2019/9/19 9:09
 */
public interface Backup {



    //将对象o备份至文件fileName中
    void backup( Object o,String fileName);




    //从fileName中读取备份的数据
   Object load(String fileName);



}
