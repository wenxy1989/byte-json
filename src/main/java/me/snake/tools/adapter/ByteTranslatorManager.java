package me.snake.tools.adapter;

import me.snake.tools.adapter.sam.ByteBCDTranslator;
import me.snake.tools.adapter.sam.DateByteTranslator;
import me.snake.tools.adapter.sam.DateTimeByteTranslator;
import me.snake.tools.adapter.sam.ShortFloatByteTranslator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2018/5/24.
 */
public class ByteTranslatorManager {

    private static final String type_boolean = "boolean";
    private static final String type_byte = "byte";
    private static final String type_char = "char";
    private static final String type_short = "short";
    private static final String type_integer = "int";
    private static final String type_long = "long";
    private static final String type_float = "float";
    private static final String type_double = "double";
    private static final String type_bcd = "bcd";
    public static final String type_string = "string";

    private static Map<String, AbstractByteTranslator> translatorMap;

    private static Map<String, AbstractByteTranslator> getTranslatorMap() {
        if (null == translatorMap) {
            translatorMap = new HashMap<String, AbstractByteTranslator>();
            translatorMap.put(type_boolean,new BooleanTranslator());
            translatorMap.put(type_byte,new ByteTranslator());
            translatorMap.put(type_char,new CharByteTranslator());
            translatorMap.put(type_short,new ShortByteTranslator());
            translatorMap.put(type_integer,new IntegerByteTranslator());
            translatorMap.put(type_long,new LongByteTranslator());
            translatorMap.put(type_float,new FloatByteTranslator());
            translatorMap.put(type_double,new DoubleByteTranslator());
            translatorMap.put(type_bcd,new BCDByteTranslator());
            translatorMap.put(type_string,new StringByteTranslator());

            translatorMap.put("date",new DateByteTranslator());
            translatorMap.put("datetime",new DateTimeByteTranslator());
            translatorMap.put("bcd-byte",new ByteBCDTranslator());
            translatorMap.put("float-short",new ShortFloatByteTranslator());
        }
        return translatorMap;
    }

    private static AbstractByteTranslator currentTranslator(String type, int length, int decimal) {
        AbstractByteTranslator translator = getTranslatorMap().get(type);
        assert (null != translator);
        translator.setLength(length);
        translator.setDecimal(decimal);
        return translator;
    }

    public static byte[] encode(Object value, String type, int length, int decimal) {
        return currentTranslator(type, length, decimal).encode(value);
    }

    public static byte[] encode(String type, int length, Object value) {
        return encode(value, type, length, 0);
    }

    public static Object decode(byte[] bytes, String type, int length, int decimal) {
        return currentTranslator(type, length, decimal).decode(bytes);
    }

}
