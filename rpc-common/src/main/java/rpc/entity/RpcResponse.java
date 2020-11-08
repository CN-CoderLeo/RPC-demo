package rpc.entity;

import lombok.Data;
import rpc.enumeration.ResponseCode;
import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable
{
    //状态码
    private Integer statusCode;

    // 响应状态的补充信息
    private String message;

    //响应的数据
    private T data;


    public static <T> RpcResponse<T> success(T data){

        RpcResponse<T> response =new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }


    public static<T> RpcResponse<T> fail(ResponseCode code){

        RpcResponse<T> response =new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }


}
