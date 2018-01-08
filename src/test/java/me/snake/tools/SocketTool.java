package me.snake.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/1/8.
 */
public class SocketTool {

    public static final String RemoteIP = "127.0.0.1";
    public static final int Port = 30007;
    public static final int readTimeout = 1000 * 45;
    public static final int connectTimeout = 1000 * 45;

    public static byte int2byte(int i) {
        return (byte) (i & 0xFF);
    }

    public static byte[] request(byte[] data) {
        Socket socket = new Socket();
        InputStream socketIn = null;
        OutputStream socketOut = null;
        try {
            socket.setSoTimeout(readTimeout); //设置读取超时时间
            socket.connect(new InetSocketAddress(RemoteIP, Port), connectTimeout); //设置连接超时
            socketIn = socket.getInputStream();
            socketOut = socket.getOutputStream();
            socketOut.write(data);
            List<Byte> responseList = null;
            while (true) {
                int read = socketIn.read();
                byte b = int2byte(read);
                //丢弃流中消息前缀前的数据
                if (b == 0x5b) {
                    responseList = new ArrayList<Byte>();
                }
                //开始读取数据,如果不是结束标记，则加入list中
                if (null != responseList) {
                    responseList.add(b);
                }
                if (b == 0x5d) {
                    break;
                }
            }
            if (null != responseList && responseList.size() > 0) {
                byte[] bytes = new byte[responseList.size()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = responseList.get(i);
                }
                return bytes;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != socketIn) {
                    socketIn.close();
                }
                if (null != socketOut) {
                    socketOut.close();
                }
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
