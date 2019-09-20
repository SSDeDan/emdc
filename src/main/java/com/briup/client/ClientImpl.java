package com.briup.client;

import com.briup.backup.Backup;
import com.briup.backup.BackupImpl;
import com.briup.bean.Environment;
import com.briup.gather.Gather;
import com.briup.gather.GatherImpl;
import config.ConfigFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 *
 *      客户端实现类
 *
 * @author: ZZZss
 * @Created 2019/9/18 9:10
 */
public class ClientImpl implements Client{
    private Socket cs;
    private Backup backup=ConfigFactory.getBackup();
    private static Properties prop=ConfigFactory.getProp();

    public ClientImpl() {
    }

    public static void main(String[] args) throws IOException{
        Client client=ConfigFactory.getClient();
        Gather gather=ConfigFactory.getGather();
        List<Environment> envs=gather.gather(prop.getProperty("radwtmp"));
        client.send(envs);
        client.close();
    }

    @Override
    public void send(List<Environment> envs){
        OutputStream os=null;
        ObjectOutputStream oos=null;
        try{
            this.cs=new Socket(
                    prop.getProperty("ip"),
                    Integer.parseInt(prop.getProperty("port"))
            );
            // 发送之前，需要从文件中读取备份的集合对象，
            // 和当前需要发送的集合对象合并到一起发送给服务器
            Object o=this.backup.load(prop.getProperty("clientSendEnvs"));
            if(o!=null){
                if(o instanceof List){
                    List<Environment> backupedEnvs=(List<Environment>)o;
                    System.out.println("-------------"+backupedEnvs.size());
                    envs.addAll(backupedEnvs);
                }
            }
            System.out.println("++++++++++++++++++"+envs.size());
            os=this.cs.getOutputStream();
            oos=new ObjectOutputStream(os);
            oos.writeObject(envs);
            oos.flush();
        }catch(Exception e){
            e.printStackTrace();
            // 需要备份当前正在发送的List集合；
            this.backup.backup(envs,prop.getProperty("clientSendEnvs"));
        }finally{
            if(oos!=null){
                try{
                    oos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try{
                    os.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close(){
        if(cs!=null){
            try{
                this.cs.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
