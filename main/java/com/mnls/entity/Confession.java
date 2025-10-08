package com.mnls.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "confession")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Confession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_who", nullable = false)
    private String fromWho;          // 表白者

    @Column(name = "to_who", nullable = false)
    private String toWho;            // 被表白者

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;          // 想说的话

    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;  // 是否匿名

    @Column(name = "font_style")
    private String fontStyle;        // 字体样式

    @Column(name = "bg_color")
    private String bgColor;          // 背景颜色

    @Column(name = "text_color")
    private String textColor;        // 文字颜色

    @Column(name = "font_size")
    private Integer fontSize;        // 字体大小

    @Column(name = "position_x")
    private Integer positionX;       // X坐标

    @Column(name = "position_y")
    private Integer positionY;       // Y坐标

    @Column(name = "z_index")
    private Integer zIndex;          // 层级

    private Integer rotation;        // 旋转角度

    @Column(name = "created_time")
    private LocalDateTime createdTime; // 创建时间

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
    }
}
