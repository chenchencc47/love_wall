package com.mnls.config;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class StyleConfig {

    // 字体样式列表
    public static final List<String> FONTS = Arrays.asList(
            "cursive", "fantasy", "monospace",
            "'Comic Sans MS', cursive", "'Brush Script MT', cursive",
            "'Arial', sans-serif", "'Times New Roman', serif"
    );

    // 背景颜色列表
    public static final List<String> BG_COLORS = Arrays.asList(
            "#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4",
            "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F",
            "#FFA07A", "#20B2AA", "#87CEEB", "#778899"
    );

    // 文字颜色列表
    public static final List<String> TEXT_COLORS = Arrays.asList(
            "#FFFFFF", "#000000", "#333333", "#2C3E50",
            "#8B4513", "#2F4F4F", "#800000"
    );

    // 字体大小范围
    public static final int MIN_FONT_SIZE = 16;
    public static final int MAX_FONT_SIZE = 24;

    // 位置范围
    public static final int MAX_POSITION_X = 1200;
    public static final int MAX_POSITION_Y = 800;

    // 旋转角度范围
    public static final int MIN_ROTATION = -5;
    public static final int MAX_ROTATION = 5;

    public <T> T getRandomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}