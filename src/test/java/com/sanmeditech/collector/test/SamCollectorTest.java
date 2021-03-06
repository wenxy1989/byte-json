package com.sanmeditech.collector.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanmeditech.utils.MD5Util;
import com.snake.base.SocketTool;
import com.snake.tools.mina.config.Action;
import com.snake.tools.mina.config.Command;
import com.snake.tools.mina.config.Server;
import com.snake.tools.mina.protocol.ConfigTools;
import com.snake.tools.mina.protocol.Content;
import com.snake.tools.mina.protocol.Head;
import com.snake.tools.utils.BCDByteUtil;
import com.snake.tools.utils.FileStringUtils;
import com.snake.tools.utils.HexByteUtil;
import org.junit.Before;
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


    private static Long userId = null;
    private static String tempLoginId = null;
    private static String loginId = null;
    private static String mobile = null;
    private static String email = null;

    private void requestAction(Command request, Command response) throws IOException {
        Content content = ConfigTools.buildContent(request);
        if (null != content) {
            System.out.println(String.format("command : code:%s ,name:%s", request.getCode(), request.getName()));
            JSONObject requestJson = request.getDefaultJson();
            if (requestJson.containsKey("loginId") && loginId != null) {
                requestJson.put("loginId", loginId);
            }
            if (requestJson.containsKey("loginName") && loginId != null) {
                requestJson.put("loginName", loginId);
            }
            if (requestJson.containsKey("userId") && userId != null) {
                requestJson.put("userId", userId);
            }
            if (requestJson.containsKey("mobile") && mobile != null) {
                requestJson.put("mobile", mobile);
            }
            if (requestJson.containsKey("email") && email != null) {
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
            if (requestJson.containsKey("glucose")) {
                requestJson.put("glucose", requestJson.getDoubleValue("glucose") + 10);
            }
            if (requestJson.containsKey("password-md5")) {
                requestJson.put("password-md5", MD5Util.encode(requestJson.getString("password-md5")));
            }
//            if (requestJson.containsKey("mobile") || requestJson.containsKey("email")) {
//                return;
//            }
            System.out.println("command : request json : " + requestJson);
            byte[] bytes = content.encode(requestJson);
            byte[] responseBytes = SocketTool.request(bytes);
            System.out.println("command : request byte : " + HexByteUtil.hexString(bytes));
            System.out.println("command : response byte : " + HexByteUtil.hexString(responseBytes));
            System.out.println("command : response code : " + response.getCode());

            assert null != (content = Content.decode(responseBytes));
            if (null != content.getBody()) {
                Head head = content.getHead();
                System.out.println(String.format("command : request code : %s  response code : %s  current code : %s", request.getCode(), response.getCode(), ConfigTools.int2Code(head.getCommand())));
                assert head.getCommand() == ConfigTools.code2Int(response.getCode());
                JSONArray array = content.getBody().getJsonArray();
                assert null != array;
                JSONObject responseJson = array.getJSONObject(0);
                if (null != responseJson) {
                    System.out.println("command : response json value : " + responseJson);
                    if (null != responseJson.get(ERROR_CODE_KEY)) {
                        assert responseJson.getIntValue(ERROR_CODE_KEY) == 0;
                        System.out.println("command : response result error code : " + responseJson.getIntValue(ERROR_CODE_KEY));
                        if (responseJson.containsKey("uid")) {
                            userId = responseJson.getLong("uid");
                            loginId = tempLoginId;
                        }
                    } else if (null != responseJson.get(CODE_KEY)) {
                        assert responseJson.getIntValue(CODE_KEY) == 0;
                        System.out.println("command : response result code : " + responseJson.getIntValue(CODE_KEY));
                        if (responseJson.containsKey("uid")) {
                            userId = responseJson.getLong("uid");
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
        System.out.println("command : response end ------------------------------------------------------------");
    }

    private void logoutLoginId(String loginId) throws IOException {
        String fileName = "protocol";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        Command command = config.getCommandMap().get("0240");
        Content content = ConfigTools.buildContent(command);
        JSONObject requestJson = command.getDefaultJson();
        requestJson.put("loginId", loginId);
        byte[] bytes = content.encode(requestJson);
        SocketTool.request(bytes);
    }

    private void logoutMobile(String mobile) throws IOException {
        String fileName = "protocol";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        Command command = config.getCommandMap().get("0241");//注销手机
        Content content = ConfigTools.buildContent(command);
        JSONObject requestJson = command.getDefaultJson();
        requestJson.put("mobile", mobile);
        byte[] bytes = content.encode(requestJson);
        SocketTool.request(bytes);
    }

    private void logoutEmail(String email) throws IOException {
        String fileName = "protocol";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        Command command = config.getCommandMap().get("0242");//注销邮箱
        Content content = ConfigTools.buildContent(command);
        JSONObject requestJson = command.getDefaultJson();
        requestJson.put("email", email);
        byte[] bytes = content.encode(requestJson);
        SocketTool.request(bytes);
    }

    @Before
    public void logout() throws IOException {
//        logoutLoginId("wenxy123");
//        logoutMobile("18911936407");
//        logoutMobile("18311050339");
//        logoutEmail("1029848260@qq.com");
//        logoutEmail("wenxy_1989@163.com");
    }

    @Test
    public void loadGlucoseListTest() throws IOException {
        String fileName = "protocol";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        List<Action> list = config.getActionList();
        for (Action action : list) {
            if ("0309".equals(action.getRequest().getCode())) {
                requestAction(action.getRequest(), action.getResponse());
            }
        }
    }

    @Test
    public void uploadGlucoseTest() throws IOException {
        String fileName = "protocol";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        Command command = config.getCommandMap().get("0301");//注销邮箱
        Content content = ConfigTools.buildContent(command);
        JSONObject jsonObject = FileStringUtils.readClassPathJSONObject("test-data-glucose");
        JSONArray glucoseArray = jsonObject.getJSONArray("glucoseList");
        if (null != glucoseArray && glucoseArray.size() > 0) {
            for (int i = 0; i < glucoseArray.size(); i++) {
                JSONObject glucose = glucoseArray.getJSONObject(i);
                System.out.println("encode and send message " + glucose);
                byte[] bytes = content.encode(glucose);
                SocketTool.request(bytes);
                try {
                    Thread.sleep(1000 * 60 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void loginIdRequestActionTest() throws IOException {
        String fileName = "protocol-loginId";
        Server config = Server.build(fileName).buildParameters().buildCommands().buildActions();
        System.out.println(config);
        List<Action> actions = config.getActionList();
        Action[] actionArray = new Action[actions.size()];
        actions.toArray(actionArray);
        Arrays.sort(actionArray);
        for (int i = 0; i < actionArray.length; i++) {
            if (actionArray[i].getIndex() < 0) {
                continue;
            }
            Action action = actionArray[i];
            if (null != action) {
                requestAction(action.getRequest(), action.getResponse());
                if ("0301".equals(action.getRequest().getCode()) || "0315".equals(action.getRequest().getCode())) {
                    requestAction(action.getRequest(), action.getResponse());
                }
            }
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
            if (null != action) {
                requestAction(action.getRequest(), action.getResponse());
            }
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
            if (null != action) {
                requestAction(action.getRequest(), action.getResponse());
            }
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
            if (null != action) {
                requestAction(action.getRequest(), action.getResponse());
            }
        }
    }

}
