package me.snake.collector;

import com.alibaba.fastjson.JSONObject;
import me.snake.base.Utils;
import me.snake.base.SocketTool;
import me.snake.tools.config.Action;
import me.snake.tools.config.Command;
import me.snake.tools.config.Server;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.protocol.Content;
import me.snake.tools.protocol.Head;
import me.snake.tools.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HP on 2018/3/28.
 */
public class SamCollectorTest {

    private static final String ERROR_CODE_KEY = "errcode";
    private static final String CODE_KEY = "code";

    public static Server config = null;

    private static String userId = "00000000000";

    @BeforeClass
    public static void setConfig() throws IOException {
        String fileName = "protocol";
        config = Server.build(fileName).buildParameters().buildCommands().buildActions();
    }

    private void requestAction(Action action) throws IOException {

        Command command = action.getRequest();
        Content content = ConfigTools.buildContent(command);
        if (null != content) {
            System.out.println(String.format("command : code:%s ,name:%s", command.getCode(), command.getName()));
            JSONObject defaultJson = content.getBody().getDefaultJson();

            defaultJson.put("userId", userId);
            byte[] bytes = content.encode(defaultJson);
            byte[] responseBytes = SocketTool.request(bytes);
            System.out.println("command : request default json : " + defaultJson);
            System.out.println("command : request byte : " + BCDByteUtil.hexString(bytes));
            System.out.println("command : response byte : " + BCDByteUtil.hexString(responseBytes));

            assert null != (content = Content.decode(responseBytes));
            if (null != content.getBody()) {
                Head head = content.getHead();
                if (head.decode()) {
                    System.out.println(String.format("command : request code : %s  response code : %s  current code : %s", action.getRequest().getCode(), action.getResponse().getCode(), ConfigTools.int2Code(head.getCommand())));
                    assert head.getCommand() == ConfigTools.code2Int(action.getResponse().getCode());
                }
                JSONObject responseJson = content.getBody().getJson();
                if (null != responseJson) {
                    System.out.println("command : response json value : " + responseJson);
                    if (null != responseJson.get(ERROR_CODE_KEY)) {
//                        assert responseJson.getIntValue(ERROR_CODE_KEY) == 0;
                        System.out.println("command : response result error code : " + responseJson.getIntValue(ERROR_CODE_KEY));
                        if (null != responseJson.get("uid")) {
                            userId = Utils.formatNumber(responseJson.getLong("uid").toString(), 11);
                        }
                    } else if (null != responseJson.get(CODE_KEY)) {
//                        assert responseJson.getIntValue(CODE_KEY) == 0;
                        System.out.println("command : response result code : " + responseJson.getIntValue(CODE_KEY));
                        if (null != responseJson.get("uid")) {
                            userId = Utils.formatNumber(responseJson.getLong("uid").toString(), 11);
                        }
                    }
                } else {
                    System.out.println("command : no data return !!!");
                }
            } else {
                System.out.println("command : error happened !!!");
            }
        }
    }

    @Test
    public void requestActionTest() throws IOException {
        List<Action> actions = config.getActionList();
        Action[] actionArray = new Action[actions.size()];
        actions.toArray(actionArray);
        Arrays.sort(actionArray);
        for (int i = 0; i < actionArray.length; i++) {
            if (actionArray[i].getIndex() < 0) {
                continue;
            }
            Action action = actionArray[i];
            requestAction(action);
        }
    }
}
