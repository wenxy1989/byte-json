package snake.tools.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by HP on 2018/5/22.
 */
public class FileStringUtils {

    public static String readClassPathFile(String fileName) throws IOException {
        String classpath = System.class.getResource("/").getPath();
        File file = new File(classpath, fileName);
        List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return String.join("", list);
    }

    public static JSONObject readClassPathJSONObject(String jsonName) throws IOException {
        return JSONObject.parseObject(FileStringUtils.readClassPathFile(jsonName + ".json"));
    }

    public static JSONArray readClassPathJSONArray(String jsonName) throws IOException {
        return JSONArray.parseArray(FileStringUtils.readClassPathFile(jsonName + ".json"));
    }

}
