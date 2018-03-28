package me.snake.collector;

import com.alibaba.fastjson.JSONObject;
import me.snake.base.Utils;
import me.snake.tools.SocketTool;
import me.snake.tools.config.Action;
import me.snake.tools.config.Command;
import me.snake.tools.config.Server;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.protocol.Content;
import me.snake.tools.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HP on 2018/3/28.
 */
public class SamCollectorTest {

    private static final String ERROR_CODE_KEY = "errcode";
    private static final String CODE_KEY = "code";

    public static Server config = null;

    @BeforeClass
    public static void setConfig() throws IOException {
        String fileName = "protocol";
        config = Server.build(fileName).buildParameters().buildCommands().buildActions();
    }

    @Test
    public void checkInterfaces() throws IOException {
        List<Action> actions = config.getActionList();
        Action[] actionArray = new Action[actions.size()];
        actions.toArray(actionArray);
        Arrays.sort(actionArray);
        String userId = "00000000000";
        for (int i = 0; i < actionArray.length; i++) {
            if (actionArray[i].getIndex() < 0) {
                continue;
            }
            Command command = actionArray[i].getRequest();
            Content content = ConfigTools.buildContent(command);
            if (null != content) {
                System.out.println(String.format("code:%s ,name:%s", command.getCode(), command.getName()));
//            JSONObject json = new JSONObject();
//            json.put("version","0.2v20180105");
                JSONObject defaultJson = content.getBody().getDefaultJson();
                System.out.println("request default json : " + defaultJson);
                defaultJson.put("userId", userId);
                byte[] bytes = content.encode(defaultJson);
                System.out.println("request byte : " + BCDByteUtil.hexString(bytes));
                byte[] responseBytes = SocketTool.request(bytes);
                System.out.println("response byte : " + BCDByteUtil.hexString(responseBytes));
                assert null != (content = Content.decode(responseBytes));
                if (null != content.getBody()) {
                    JSONObject json = content.getBody().getJson();
                    if (null != json) {
                        System.out.println("response json value : " + json);
                        if (null != json.get(ERROR_CODE_KEY)) {
                            assert json.getIntValue(ERROR_CODE_KEY) == 0;
                        } else if (null != json.get(CODE_KEY)) {
                            assert json.getIntValue(CODE_KEY) == 0;
                            if (null != json.get("uid")) {
                                userId = Utils.formatNumber(json.getLong("uid").toString(), 11);
                            }
                        }
                    } else {
                        System.out.println("no data return !!!");
                    }
                } else {
                    System.out.println("error happened !!!");
                }
            }
        }
    }
}
