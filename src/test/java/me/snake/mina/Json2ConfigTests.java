package me.snake.mina;

import com.alibaba.fastjson.JSONObject;
import me.snake.mina.config.Action;
import me.snake.mina.config.Server;
import me.snake.mina.inter.ConfigTools;
import me.snake.mina.protocol.Attribute;
import me.snake.mina.protocol.Content;
import me.snake.mina.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [{version,serial,command,[length,{body:parameterList}]}check] //todo
 * Created by HP on 2017/12/27.
 */
public class Json2ConfigTests {

    public static Server config = null;

    @BeforeClass
    public static void setConfig() throws IOException {
        String classpath = System.class.getResource("/").getPath();
        String fileName = "protocol.json";
        File file = new File(classpath, fileName);
//        Path path = Paths.get(classpath, );
        List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        String text = String.join("", list);
        JSONObject instance = JSONObject.parseObject(text);
        config = Server.build(instance).buildParameters().buildCommands().buildInterfaces();
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
