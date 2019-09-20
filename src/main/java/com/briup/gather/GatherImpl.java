package com.briup.gather;

import com.briup.backup.Backup;
import com.briup.backup.BackupImpl;
import com.briup.bean.DataType;
import com.briup.bean.Environment;
import config.ConfigFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/17 9:32
 */
public class GatherImpl implements Gather{
    private Backup backup=ConfigFactory.getBackup();
    private static Properties prop=ConfigFactory.getProp();

    public GatherImpl(){
    }

    @Override
    public List<Environment> gather(String filePath){
        try{
            BufferedReader br=new BufferedReader(new FileReader(filePath));
            List<Environment> envs=new ArrayList<>();
            String line;
            // 在采集之前，读取备份文件，
            // 转化成行数，然后跳过之后再做采集
            String gatheredLinesFile=prop.getProperty("gatheredLines");
            Object o=this.backup.load(gatheredLinesFile);
            int gatheredLines=0;
            if(o!=null){
                if(o instanceof Integer) gatheredLines=(int)o;   // 10
            }
            System.out.println("需要跳过的行数："+gatheredLines);
            int count=gatheredLines;
            int currentGatheredLines=0;
            while((line=br.readLine())!=null){
                // 跳过已经采集过的数据
                if(count-->0) continue;

                System.out.println(line);

                // 记录本次采集过的行数
                currentGatheredLines++;

                String[] strs=line.split("[|]");
                if("16".equals(strs[3])){
                    // 温度
                    Environment env=new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.TMP);
                    int value=Integer.parseInt(strs[6].substring(0,4),16);
                    float f=(float)(value*0.00268127-46.85);
                    String data=String.format("%.2f",f);
                    env.setData(data);
                    envs.add(env);

                    // 湿度
                    Environment env1=new Environment();
                    this.setData(env1,strs);
                    env1.setDataType(DataType.HUM);
                    value=Integer.parseInt(strs[6].substring(4,8),16);
                    f=(float)(value*0.00190735-6);
                    data=String.format("%.2f",f);
                    env1.setData(data);
                    envs.add(env1);
                }

                if("256".equals(strs[3])){
                    // 光照强度
                    Environment env=new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.ILL);
                    env.setData(String.valueOf(Integer.parseInt(strs[6],16)));
                    envs.add(env);
                }

                if("1280".equals(strs[3])){
                    // 二氧化碳浓度
                    Environment env=new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.CO2);
                    env.setData(String.valueOf(Integer.parseInt(strs[6],16)));
                    envs.add(env);
                }
            }

            // 备份行数：gatheredLines+currentGatheredLines
            int allLines=gatheredLines+currentGatheredLines;
            this.backup.backup(allLines,gatheredLinesFile);

            return envs;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void setData(Environment env,String[] strs){
        env.setSrcId(strs[0]);
        env.setDestId(strs[1]);
        env.setDevId(strs[2]);
        env.setSensorId(strs[3]);
        env.setSensorCounter(Integer.parseInt(strs[4]));
        env.setCmdType(strs[5]);
        env.setStatus(Integer.parseInt(strs[7]));
        env.setTimestamp(Long.parseLong(strs[8]));
    }
}

