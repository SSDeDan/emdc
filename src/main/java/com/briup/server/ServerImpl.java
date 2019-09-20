package com.briup.server;

import com.briup.bean.Environment;
import com.briup.store.Store;
import com.briup.store.StoreImpl;
import config.ConfigFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/18 9:27
 */
public class ServerImpl implements Server{
    private static Properties prop=ConfigFactory.getProp();

    public static void main(String[] args) throws IOException{
        ServerSocket ss=new ServerSocket(Integer.parseInt(prop.getProperty("port")));

        System.out.println("服务器启动了。。。");

        while(true){
            Socket cs=ss.accept();
            System.out.println(cs.getInetAddress().getHostAddress()+"连接进来了！");
            new Thread(()->{
                try{
                    InputStream is=cs.getInputStream();
                    ObjectInputStream ois=new ObjectInputStream(is);
                    List<Environment> envs=new ServerImpl().receive(ois);
                    Store store=new StoreImpl();
                    store.store(envs);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }).start();
        }

    }

    @Override
    public List<Environment> receive(ObjectInputStream ois){
        try{
            Object o=ois.readObject();
            if(o instanceof List){
                List<Environment> envs=(List<Environment>)o;
                return envs;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close(){

    }
}
