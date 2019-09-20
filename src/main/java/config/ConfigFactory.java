package config;

import com.briup.backup.Backup;
import com.briup.client.Client;
import com.briup.gather.Gather;
import com.briup.server.Server;
import com.briup.store.Store;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @program: shuaixiaohuo.java
 * @Description: emdc
 * @author: ZZZss
 * @Created 2019/9/20 9:16
 */
public class ConfigFactory {

    private static Map<String,Object>  map=new HashMap<>();
    private static Properties prop=new Properties();


    public static void main(String[] args) {

    }

    static {

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(ConfigFactory.class.getResourceAsStream("/emdc.xml"));

            List<Element> es = doc.getRootElement().elements();
            for (Element e: es){
                // 得到属性值时class的属性    class="com.briup.backup.BackupImpl"
                Attribute attr = e.attribute("class");
                //得到属性里面的信息   com.briup.backup.BackupImpl
                String value = attr.getStringValue();
                //通过反射的方式  Class.forName(类全名)获得class对象，再通过class.newInstance实例化对象
                Object o = Class.forName(value).newInstance();
                map.put(e.getName(),o);

                List<Element> e1s = e.elements();
                for (Element e1:e1s) {
                    String propName = e1.getName();
                    String propValue = e1.getStringValue();
                    prop.setProperty(propName,propValue);
                }
            }



//            map.forEach((k,v)->{
//                System.out.println(k+":::"+v);
//            });
//
//            System.out.println("---------------------");
//            prop.forEach((k,v)->{
//                System.out.println(k+":::"+v);
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static Properties getProp(){
        return prop;
    }

    public static Client getClient(){
        return (Client) map.get("client");
    }

    public static Server getServer(){
        return (Server) map.get("server");
    }

    public static Gather getGather(){
        return (Gather) map.get("gather");
    }

    public static Store getStore(){
        return (Store) map.get("store");
    }

    public static Backup getBackup(){
        return (Backup) map.get("backup");
    }



}
