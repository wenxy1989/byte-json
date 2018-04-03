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


    private static String userId = null;
    private static String tempLoginId = null;
    private static String loginId = null;
    private static String mobile = null;
    private static String email = null;

    private void requestAction(Action action) throws IOException {

        Command command = action.getRequest();
        Content content = ConfigTools.buildContent(command);
        if (null != content) {
            System.out.println(String.format("command : code:%s ,name:%s", command.getCode(), command.getName()));
            JSONObject requestJson = content.getBody().getDefaultJson();
            if(requestJson.containsKey("loginId") && loginId != null){
                requestJson.put("loginId",loginId);
            }
            if(requestJson.containsKey("loginName") && loginId != null){
                requestJson.put("loginName",loginId);
            }
            if(requestJson.containsKey("userId") && userId != null) {
                requestJson.put("userId", userId);
            }
            if(requestJson.containsKey("mobile") && mobile != null) {
                requestJson.put("mobile", mobile);
            }
            if(requestJson.containsKey("email") && email != null) {
                requestJson.put("email", email);
            }
            if (requestJson.containsKey("mobile")) {
                tempLoginId = requestJson.getString("mobile");
            }
            if (requestJson.containsKey("email")) {
                tempLoginId = requestJson.getString("email");
            }
            if (requestJson.containsKey("loginId")) {
                tempLoginId = requestJson.getString("loginId");
            }
            if (requestJson.containsKey("loginName")) {
                tempLoginId = requestJson.getString("loginName");
            }
            byte[] bytes = content.encode(requestJson);
            byte[] responseBytes = SocketTool.request(bytes);
            System.out.println("command : request json : " + requestJson);
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
                        assert responseJson.getIntValue(ERROR_CODE_KEY) == 0;
                        System.out.println("command : response result error code : " + responseJson.getIntValue(ERROR_CODE_KEY));
                        if (responseJson.containsKey("uid")) {
                            userId = Utils.formatNumber(responseJson.getLong("uid").toString(), 11);
                            loginId = tempLoginId;
                        }
                    } else if (null != responseJson.get(CODE_KEY)) {
                        assert responseJson.getIntValue(CODE_KEY) == 0;
                        System.out.println("command : response result code : " + responseJson.getIntValue(CODE_KEY));
                        if (responseJson.containsKey("uid")) {
                            userId = Utils.formatNumber(responseJson.getLong("uid").toString(), 11);
                            loginId = tempLoginId;
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
    public void loginIdRequestActionTest() throws IOException {
        String fileName = "protocol-loginId";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
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

    @Test
    public void mobileRequestActionTest() throws IOException {
        String fileName = "protocol-mobile";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
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

    @Test
    public void emailRequestActionTest() throws IOException {
        String fileName = "protocol-email";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
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

    @Test
    public void hospitalRequestActionTest() throws IOException {
        String fileName = "protocol-hospital";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
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
