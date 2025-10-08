package com.mnls.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfessionResponse {
    private Long id;
    private String fromWho;      // 注意：如果匿名会显示"匿名"
    private String toWho;
    private String content;
    private String fontStyle;
    private String bgColor;
    private String textColor;
    private Integer fontSize;
    private Integer positionX;
    private Integer positionY;
    private Integer zIndex;
    private Integer rotation;
    private LocalDateTime createdTime;
}