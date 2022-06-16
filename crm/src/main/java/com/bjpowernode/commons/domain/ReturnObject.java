package com.bjpowernode.commons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnObject {
//    处理结果是否成功的标记：1-》成功，0-》失败
    private String code;
//    提示信息
    private String message;
//    返回其他的数据
    private Object retData;
}
