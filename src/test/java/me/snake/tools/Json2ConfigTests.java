package me.snake.tools;

import com.alibaba.fastjson.JSONObject;
import me.snake.tools.config.Action;
import me.snake.tools.config.Server;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.protocol.Attribute;
import me.snake.tools.protocol.Content;
import me.snake.tools.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * [{version,serial,command,[length,{body:parameterList}]}check] //todo
 * Created by HP on 2017/12/27.
 */
public class Json2ConfigTests {

    public static Server config = null;

    @BeforeClass
    public static void setConfig() throws IOException {
        String fileName = "protocol";
        config = Server.build(fileName).buildParameters().buildCommands().buildInterfaces();
    }

    @Test
    public void showInterface() {
        Map<String, Action> interfaceMap = config.getInterfaceMap();
        Iterator<String> iterator = interfaceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            Action action = interfaceMap.get(code);
            Object interfaceJson = JSONObject.toJSON(action);
            System.out.println(code);
            System.out.println(interfaceJson);
        }
    }

    @Test
    public void checkVersionTest() throws IOException {
        Action checkVersionInter = config.getInterfaceMap().get("0201");
        Content content = ConfigTools.buildContent(checkVersionInter.getRequest());
        byte[] bytes = content.encode();
        System.out.println(BCDByteUtil.hexString(bytes));
        if (content.decode()) {
            Attribute[] attributes = content.getBody().getAttributes();
            for(Attribute attribute :attributes) {
                System.out.println(attribute);
            }
        }
    }

    @Test
    public void nameRegisterTest() throws IOException {
        Action checkVersionInter = config.getInterfaceMap().get("0202");
        Content content = ConfigTools.buildContent(checkVersionInter.getRequest());
        byte[] bytes = content.encode();
        System.out.println(BCDByteUtil.hexString(bytes));
        if (content.decode()) {
            Attribute[] attributes = content.getBody().getAttributes();
            for(Attribute attribute :attributes) {
                System.out.println(attribute);
            }
        }
    }

    @Test
    public void mobileRegisterTest() throws IOException {
        Action checkVersionInter = config.getInterfaceMap().get("0203");
        Content content = ConfigTools.buildContent(checkVersionInter.getRequest());
        byte[] bytes = content.encode();
        System.out.println(BCDByteUtil.hexString(bytes));
        if (content.decode()) {
            Attribute[] attributes = content.getBody().getAttributes();
            for(Attribute attribute :attributes) {
                System.out.println(attribute);
            }
        }
    }

}
