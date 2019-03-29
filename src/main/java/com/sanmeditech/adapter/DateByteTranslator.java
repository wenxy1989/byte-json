package com.sanmeditech.adapter;

import com.snake.tools.mina.adapter.AbstractByteTranslator;
import com.snake.tools.utils.BCDByteUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HP on 2018/5/24.
 */
public class DateByteTranslator extends AbstractByteTranslator<String> {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateNumberFormat = "yyyyMMdd";
    private static final int length = 4;

    @Override
    public String getType() {
        return "date";
    }

    @Override
    public byte[] encode(Object value) {
        assert null != value;
        try {
            Date date = new SimpleDateFormat(dateFormat).parse((String) value);
            Long number = Long.parseLong(new SimpleDateFormat(dateNumberFormat).format(date));
            return BCDByteUtil.number2bcd(number, length);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        try {
            String numberString = String.format("%d", BCDByteUtil.bcd2long(bytes));
            Date date = new SimpleDateFormat(dateNumberFormat).parse(numberString);
            return new SimpleDateFormat(dateFormat).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
