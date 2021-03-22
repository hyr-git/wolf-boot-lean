package com.hyr.lean.aop.pojo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiAccessLog  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "接口地址")
    private String requestUrl;

    @ApiModelProperty(value = "请求主机地址")
    private Long originHost;

    @ApiModelProperty(value = "api方法类型")
    private String requestMethod;

    @ApiModelProperty(value = "请求头")
    private String requestHeader;

    @ApiModelProperty(value = "请求body")
    private String requestBody;

    @ApiModelProperty(value = "响应码")
    private Integer statusCode;

    @ApiModelProperty(value = "响应")
    private String response;

    @ApiModelProperty(value = "执行时间")
    private Long executeTime;

    @ApiModelProperty(value = "请求时间")
    private Date apiRequestTime;


}
