package rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RpcRequest implements Serializable {

    //待调用接口名称
    private String interfaceName;

    //待调用接口方法名称
    private String methodName;

    // 方法参数
    private Object[] parameters;

    // 方法参数类型
    private Class<?> [] paramTypes;

}
