package com.briup.backup;

import java.io.*;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/19 9:13
 */
public class BackupImpl implements Backup{
    private static final String ROOT_PATH="F:\\emdc";
    private File rootDir;

    public BackupImpl(){
        this.rootDir=new File(ROOT_PATH);
        if(!rootDir.exists()) rootDir.mkdirs();
    }

    @Override
    public void backup(Object o,String fileName){
        if(o==null){
            System.out.println("需要备份的对象不能为空！");
            return;
        }
        try{
            File file=new File(this.rootDir,fileName);
            FileOutputStream fos=new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object load(String fileName){
        FileInputStream fis=null;
        ObjectInputStream ois=null;
        try{
            File file=new File(this.rootDir,fileName);
            if(!file.exists()) return null;
            fis=new FileInputStream(file);
            ois=new ObjectInputStream(fis);
            return ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            if(ois!=null){
                try{
                    ois.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(fis!=null){
                try{
                    fis.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

