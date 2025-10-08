package com.mnls.service;

import com.mnls.config.StyleConfig;
import com.mnls.dto.request.ConfessionRequest;
import com.mnls.dto.response.ConfessionResponse;
import com.mnls.entity.Confession;
import com.mnls.repository.ConfessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConfessionService {

    private final ConfessionRepository confessionRepository;
    private final StyleConfig styleConfig;

    /**
     * 创建表白
     */
    /**
     * 创建表白 —— 带 5 秒幂等
     */
    public ConfessionResponse createConfession(ConfessionRequest req) {
        // 1. 5 秒内重复内容直接返回已有记录
        Optional<Confession> recent = confessionRepository
                .findTopByFromWhoAndToWhoAndContentOrderByCreatedTimeDesc(
                        req.getFromWho(), req.getToWho(), req.getContent());

        if (recent.isPresent() &&
                recent.get().getCreatedTime().isAfter(LocalDateTime.now().minusSeconds(5))) {
            return convertToResponse(recent.get());
        }

        // 2. 否则新建
        Confession c = new Confession();
        c.setFromWho(req.getFromWho());
        c.setToWho(req.getToWho());
        c.setContent(req.getContent());
        c.setIsAnonymous(req.getIsAnonymous());
        generateRandomStyle(c);
        Confession saved = confessionRepository.save(c);
        return convertToResponse(saved);
    }

    /**
     * 获取所有表白（按时间倒序）
     */
    public List<ConfessionResponse> getAllConfessions() {
        List<Confession> confessions = confessionRepository.findAllOrderByCreatedTimeDesc();
        return confessions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 生成随机样式
     */
    private void generateRandomStyle(Confession confession) {
        confession.setFontStyle(styleConfig.getRandomElement(StyleConfig.FONTS));
        confession.setBgColor(styleConfig.getRandomElement(StyleConfig.BG_COLORS));
        confession.setTextColor(styleConfig.getRandomElement(StyleConfig.TEXT_COLORS));
        confession.setFontSize(styleConfig.getRandomInt(
                StyleConfig.MIN_FONT_SIZE, StyleConfig.MAX_FONT_SIZE));
        confession.setPositionX(styleConfig.getRandomInt(0, StyleConfig.MAX_POSITION_X));
        confession.setPositionY(styleConfig.getRandomInt(0, StyleConfig.MAX_POSITION_Y));
        confession.setZIndex(styleConfig.getRandomInt(1, 100));
        confession.setRotation(styleConfig.getRandomInt(
                StyleConfig.MIN_ROTATION, StyleConfig.MAX_ROTATION));
    }

    /**
     * 转换为响应对象
     */
    private ConfessionResponse convertToResponse(Confession confession) {
        ConfessionResponse response = new ConfessionResponse();
        response.setId(confession.getId());

        // 处理匿名显示
        if (Boolean.TRUE.equals(confession.getIsAnonymous())) {
            response.setFromWho("匿名");
        } else {
            response.setFromWho(confession.getFromWho());
        }

        response.setToWho(confession.getToWho());
        response.setContent(confession.getContent());
        response.setFontStyle(confession.getFontStyle());
        response.setBgColor(confession.getBgColor());
        response.setTextColor(confession.getTextColor());
        response.setFontSize(confession.getFontSize());
        response.setPositionX(confession.getPositionX());
        response.setPositionY(confession.getPositionY());
        response.setZIndex(confession.getZIndex());
        response.setRotation(confession.getRotation());
        response.setCreatedTime(confession.getCreatedTime());

        return response;
    }
}