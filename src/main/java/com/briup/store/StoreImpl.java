package com.briup.store;

import com.briup.backup.Backup;
import com.briup.backup.BackupImpl;
import com.briup.bean.Environment;
import config.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/17 11:06
 */
public class StoreImpl implements Store{
    private Backup backup;

    private static Properties prop= ConfigFactory.getProp();

    public StoreImpl() {
        this.backup=ConfigFactory.getBackup();
    }

    @Override
    public void store(List<Environment> envs){
        Connection conn=null;
        try{
            Class.forName(prop.getProperty("driver"));
            String url=prop.getProperty("url");
            String user=prop.getProperty("user");
            String password=prop.getProperty("password");
            conn=DriverManager.getConnection(url,user,password);
            String sql="insert into emdc.envs value(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstat=conn.prepareStatement(sql);
            int count=0;

            // 需要在JDBC中设置手动提交事务
            conn.setAutoCommit(false);

            // 保存至数据库之前，需要从备份文件中加载上次未保存成功的集合对象
            // 然后将该集合对象和本次需要保存的集合对象合并到一起，然后保存
            // 至数据库
            Object o = this.backup.load(prop.getProperty("storeEnvs"));
            if (o!=null){
                if (o instanceof List){
                    List<Environment> backupEnvs= (List<Environment>) o;
                    //将备份中的数据添加到envs集合内
                    envs.addAll(backupEnvs);
                }
            }

            for(Environment env: envs){
                pstat.setString(1,env.getSrcId());
                pstat.setString(2,env.getDestId());
                pstat.setString(3,env.getDevId());
                pstat.setString(4,env.getSensorId());
                pstat.setInt(5,env.getSensorCounter());
                pstat.setString(6,env.getCmdType());
                pstat.setString(7,env.getDataType().toString());
                pstat.setString(8,env.getData());
                pstat.setInt(9,env.getStatus());
                pstat.setLong(10,env.getTimestamp());
                pstat.addBatch();
                if(++count%Integer.parseInt(prop.getProperty("batchSize"))==0) pstat.executeBatch();
            }
//             int x=1/0;
            pstat.executeBatch();
            conn.commit();
            pstat.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
            // 备份
            this.backup.backup(envs,prop.getProperty("storeEnvs"));


            // 事务回滚，已经提交的事务不能进行回滚。
            if(conn!=null){
                try{
                    conn.rollback();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
