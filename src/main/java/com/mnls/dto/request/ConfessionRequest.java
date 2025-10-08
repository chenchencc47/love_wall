package com.mnls.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfessionRequest {

    @NotNull(message = "表白者不能为空")
    @Size(min = 1, message = "表白者不能为空")
    private String fromWho;

    @NotNull(message = "被表白者不能为空")
    @Size(min = 1, message = "被表白者不能为空")
    private String toWho;

    @NotNull(message = "表白内容不能为空")
    @Size(min = 1, message = "表白内容不能为空")
    private String content;

    private Boolean isAnonymous = false;  // 默认为非匿名
}