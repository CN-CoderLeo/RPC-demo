package rpc.socket.util;

import rpc.entity.RpcRequest;
import rpc.enumeration.PackageType;
import rpc.serializer.CommonSerializer;

import java.io.IOException;
import java.io.OutputStream;

public class ObjectWriter {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static void writeObject(OutputStream outputStream, Object object, CommonSerializer serializer) throws IOException {

        outputStream.write(intToBytes(MAGIC_NUMBER));
        if (object instanceof RpcRequest) {
            outputStream.write(intToBytes(PackageType.REQUEST_PACK.getCode()));
        } else {
            outputStream.write(intToBytes(PackageType.RESPONSE_PACK.getCode()));
        }
        outputStream.write(intToBytes(serializer.getCode()));
        byte[] bytes = serializer.serialize(object);
        outputStream.write(intToBytes(bytes.length));
        outputStream.write(bytes);
        outputStream.flush();

    }

    private static byte[] intToBytes(int value) {
        byte[] des = new byte[4];
        des[3] =  (byte) ((value>>24) & 0xFF);
        des[2] =  (byte) ((value>>16) & 0xFF);
        des[1] =  (byte) ((value>>8) & 0xFF);
        des[0] =  (byte) (value & 0xFF);
        return des;
    }
}
