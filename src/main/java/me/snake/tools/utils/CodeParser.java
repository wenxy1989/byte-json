package me.snake.tools.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/6/13.
 */
public abstract class CodeParser {

    public static byte[] getByteArrayFromByteList(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

    public static byte[] encode(byte[] data) {
        List<Byte> list = new ArrayList<Byte>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0x5b) { //0x5b <————> 0x5a 后紧跟一个0x01；
                list.add((byte)(0x5a));
                list.add((byte)(0x01));
            } else if (data[i] == 0x5a) { //0x5a <————> 0x5a 后紧跟一个0x02；
                list.add((byte)(0x5a));
                list.add((byte)(0x02));
            } else if (data[i] == 0x5d) { //0x5d <————> 0x5e 后紧跟一个0x01；
                list.add((byte)(0x5e));
                list.add((byte)(0x01));
            } else if (data[i] == 0x5e) { //0x5e <————> 0x5e 后紧跟一个0x02；
                list.add((byte)(0x5e));
                list.add((byte)(0x02));
            } else {
                list.add(data[i]);
            }
        }
        return getByteArrayFromByteList(list);
    }

    public static byte[] decode(byte[] data) {
        List<Byte> list = new ArrayList<Byte>();
        int i = 0;
        for (; i < data.length - 1; i++) {
            if (data[i] == 0x5a && data[i + 1] == 0x01) { //0x5b <————> 0x5a 后紧跟一个0x01；
                list.add((byte)(0x5b));
                i++;
            } else if (data[i] == 0x5a && data[i + 1] == 0x02) { //0x5a <————> 0x5a 后紧跟一个0x02；
                list.add((byte)(0x5a));
                i++;
            } else if (data[i] == 0x5e && data[i + 1] == 0x01) { //0x5d <————> 0x5e 后紧跟一个0x01；
                list.add((byte)(0x5d));
                i++;
            } else if (data[i] == 0x5e && data[i + 1] == 0x02) { //0x5e <————> 0x5e 后紧跟一个0x02；
                list.add((byte)(0x5e));
                i++;
            } else {
                list.add(data[i]);
            }
        }
        if (i == data.length - 1) {
            list.add(data[i]);
        }
        return getByteArrayFromByteList(list);
    }

    public static byte check(byte[] bytes) {
        byte check = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            check = (byte) (check ^ bytes[i]);
        }
        return check;
    }
}
