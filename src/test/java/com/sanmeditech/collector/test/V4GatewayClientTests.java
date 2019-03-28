package com.sanmeditech.collector.test;

import com.alibaba.fastjson.JSONObject;
import com.snake.base.SocketTool;
import snake.tools.mina.protocol.ConfigTools;
import snake.tools.mina.config.Command;
import snake.tools.mina.config.Server;
import snake.tools.mina.protocol.Content;
import snake.tools.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by HP on 2017/12/25.
 */
public class V4GatewayClientTests {


    private static Server config;

    @BeforeClass
    public static void beforeClass() throws IOException {
        String fileName = "protocol";
        config = Server.build(fileName).buildParameters().buildCommands().buildActions();
    }

    // 更新版本
    public void upgradeCheckTest() throws UnsupportedEncodingException {
        String name = "更新版本";
        String code = "9201";
        Command command = config.getCommandMap().get(code);//注销手机
        Content content = ConfigTools.buildContent(command);
        byte[] bytes = content.encode(command.getDefaultJson());
        byte[] responseBytes = SocketTool.request(bytes);
        System.out.println(BCDByteUtil.hexString(responseBytes));
    }

    public void nameRegisterTest(/**0202**/) {
        String name = "用户名注册";
        String code = ("9202");
    }

    public void mobileRegisterTest(/**0203**/) {// 手机号注册
        String name = "手机号注册";
        String code = ("9203");
    }

    public void emailRegisterTest(/**0204**/) {
        String name = "邮箱注册";
        String code = ("9204");
    }

    private void executeCommand(String code){
        Command command = config.getCommandMap().get(code);//注销手机
        JSONObject json = command.getDefaultJson();
        System.out.println(json);
        Content content = ConfigTools.buildContent(command);
        byte[] bytes = new byte[0];
        try {
            bytes = content.encode(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] responseBytes = SocketTool.request(bytes);
        System.out.println(BCDByteUtil.hexString(responseBytes));
    }

    @Test
    public void notExistsMobileCodeTest() throws UnsupportedEncodingException {//获取手机验证码
        String name = "获取手机注册或绑定验证码";
        String code = "0205"; // 应答命令
        executeCommand(code);
    }

    public void notExistsEmailCodeTest(/**0206**/) {// 获取邮箱验证码
        String name = "获取邮箱注册或绑定验证码";
        String code = ("9206"); // 应答命令
    }

    public void setPasswordByMobileTest(/**0207**/) {// 通过手机号重置密码
        String name = "通过手机号重置密码";
        String code = ("9207"); // 应答命令
    }

    public void setPasswordByEmialTest(/**0208**/) {// 通过邮箱重置密码
        String name = "通过邮箱重置密码";
        String code = ("9208"); // 应答命令
    }

    public void existsMobileCodeTest(/**0209**/) {// 获取手机号重置密码验证码
        String name = "获取重置密码手机号验证码";
        String code = ("9209"); // 应答命令
    }

    public void existsEmailCodeTest(/**0210**/) {// 获取邮箱重置密码验证码
        String name = "获取重置密码邮箱验证码";
        String code = ("9210"); // 应答命令
    }

    public void setPasswordTest(/**0211**/) {// 修改密码
        String name = "修改密码";
        String code = ("9211"); // 应答命令
    }

    public void loginTest(/**0212**/) {// 用户登录
        String name = "用户登录";
        String code = ("9212"); // 应答命令
    }

    public void userInfoTest(/**0213**/) {// 用户信息推送
        String name = "用户信息推送";
        String code = ("9213"); // 应答命令
    }

    public void mobileBindTest(/**0214**/) {// 用户手机号绑定
        String name = "用户手机号绑定";
        String code = ("9214"); // 应答命令
    }

    public void emailBindTest(/**0215**/) {// 用户邮箱绑定
        String name = "用户邮箱绑定";
        String code = ("9215"); // 应答命令
    }

    public void updateUserTest(/**0216**/) {
        String name = "用户信息上传";
        String code = ("9216");
    }

    public void uuidLoginTest(/**0217**/) {// 用户登录
        String name = "UUID登录";
        String code = ("9217"); // 应答命令
    }

    @Test
    public void glucoseUploadTest(/**0301**/) {// 血糖数据上报
        String name = "血糖数据上报";
        String code = ("9001");
    }

    public void GPSUploadTest(/**0302**/) {// GPS数据上报
        String name = "GPS数据上报";
        String code = ("9001");
    }

    public void referUploadTest(/**0303**/) {// 参比血糖数据上报
        String name = "参比血糖数据上报";
        String code = ("9001");
    }

    public void foodUploadTest(/**0304**/) {// 饮食事件数据上传
        String name = "饮食数据上传";
        String code = ("9001");
    }

    public void insulinUploadTest(/**0305**/) {// 注射胰岛素事件上传
        String name = "注射胰岛素事件上传";
        String code = ("9001");
    }

    public void medicineUploadTest(/**0306**/) {// 用药事件上传
        String name = "用药事件上传";
        String code = ("9001"); //
    }

    public void exerciseUploadTest(/**0307**/) {// 锻炼事件上传
        String name = "锻炼事件上传";
        String code = ("9001"); // 平台通用应答
    }

    public void alarmInfoTest(/**0308**/) {// 患者告警参数推送
        String name = "患者告警参数推送";
        String code = ("9301"); // 患者告警参数推送应答
    }

    public void glucoseQueryTest(/**0309**/) {// 患者血糖信息推送
        String name = "患者血糖信息推送";
        String code = ("9302"); // 患者血糖信息推送应答
    }

    public void referQueryTest(/**0310**/) {// 患者参比血糖信息推送
        String name = "患者参比血糖信息推送";
        String code = ("9303"); // 患者参比血糖信息推送应答
    }

    public void eventQueryTest(/**0311**/) {// 患者活动事件信息推送
        String name = "患者活动事件信息推送";
        String code = ("9304"); // 患者活动事件信息推送应答
    }

    public void attentionAlertTest(/**0312**/) {// 获取关注患者待处理告警推送
        String name = "获取关注患者待处理告警推送";
        String code = ("9305"); //获取关注患者待处理告警推送应答
    }

    public void confirmAlertTest(/**0313**/) {// 确认告警已处理
        String name = "确认告警已处理";
        String code = ("9306"); //确认告警已处理应答
    }

    public void reportAlertTest(/**0314**/) {// 通知上级处理
        String name = "通知上级处理";
        String code = ("9307"); //获取关注患者待处理告警推送应答
    }

    public void glucoseWithVoltageUploadTest(/**0315**/) {// 血糖数据上报
        String name = "血糖数据上报";
        String code = ("9001");
    }

    public void emitterStatusUploadTest(/**0401**/) {// 发射器工作状态上传
        String name = "发射器工作状态上传";
        String code = ("9001"); // 通用应答
    }

    public void wearUploadTest(/**0402**/) {// 用户佩戴记录上传
        String name = "上传佩戴记录";
        String code = ("9001");
    }

    public void lastWearTest(/**0403**/) {// 用户佩戴记录推送
        String name = "用户佩戴记录推送";
        String code = ("9308"); // 患者用户佩戴记录信息推送应答
    }

    public void alertWearTooLongTest(/**0404**/) {// 佩戴超时告警上传
        String name = "佩戴超时告警上传";
        String code = ("9001"); // 通用应答
    }

    public void continuousHighAlertTest(/**0405**/) {// 持续高电流告警上传
        String name = "持续高电流告警上传";
        String code = ("9001"); // 通用应答
    }

    public void continuousLowAlertTest(/**0406**/) {// 持续低电流告警上传
        String name = "持续低电流告警上传";
        String code = ("9001"); // 通用应答
    }

    public void mobileLogUploadTest(/**0500**/) {// 日志上传接口
        String name = "日志上传";
        String code = ("9001"); // 患者用户佩戴记录信息推送应答
    }

    public void hospitalNameRegisterTest(/**1001**/) {
        String name = "医院用户名注册";
        String code = ("9202");
    }

    public void hospitalMobileRegisterTest(/**1002**/) {
        String name = "医院手机号注册";
        String code = ("9202");
    }

    public void hospitalEmailRegisterTest(/**1003**/) {
        String name = "医院邮箱注册";
        String code = ("9204");
    }

    public void hospitalUserUploadTest(/**1004**/) {
        String name = "医院用户信息上传";
        String code = ("9216");
    }

}
