package cn.yz.clothManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName ResultCommon
 * @date 2022/1/11 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> implements Serializable {
    private StatusCode statusCode;
    private T data;
    public CommonResult(StatusCode statusCode){
        this(statusCode,null);
    }

}
