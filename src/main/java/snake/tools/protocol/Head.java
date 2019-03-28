package snake.tools.protocol;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Head {

    public static final short head_length = 8;
    public static final short byte_bit_length = 8;

    int version;
    int serial;
    int command;
    int length;

    byte[] bytes;

    public Head(byte[] bytes) {
        this.bytes = bytes;
    }

    public Head(int version, int serial, int command) {
        this.version = version;
        this.serial = serial;
        this.command = command;
    }

    public int getVersion() {
        return version;
    }

    public int getSerial() {
        return serial;
    }

    public int getCommand() {
        return command;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] encode() {
        bytes = new byte[head_length];
        bytes[0] = (byte) (version >> byte_bit_length);
        bytes[1] = (byte) (version & 0x00FF);
        bytes[2] = (byte) (serial >> byte_bit_length);
        bytes[3] = (byte) (serial & 0x00FF);
        bytes[4] = (byte) (command >> byte_bit_length);
        bytes[5] = (byte) (command & 0x00FF);
        bytes[6] = (byte) (length >> byte_bit_length);
        bytes[7] = (byte) (length & 0x00FF);
        return bytes;
    }

    public boolean decode() {
        if (null != bytes && bytes.length == head_length) {
            version = (bytes[0] << byte_bit_length) | (bytes[1] & 0xFF);
            serial = (bytes[2] << byte_bit_length) | (bytes[3] & 0xFF);
            command = (bytes[4] << byte_bit_length) | (bytes[5] & 0xFF);
            length = (bytes[6] << byte_bit_length) | (bytes[7] & 0xFF);
            return true;
        }
        return false;
    }

}
