package com.mnls.controller;

import com.mnls.dto.ApiResponse;
import com.mnls.dto.request.ConfessionRequest;
import com.mnls.dto.response.ConfessionResponse;
import com.mnls.service.ConfessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/confessions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 允许所有跨域请求
public class ConfessionController {

    private final ConfessionService confessionService;

    /**
     * 发布表白
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ConfessionResponse>> createConfession(
            @Valid @RequestBody ConfessionRequest request) {
        try {
            ConfessionResponse response = confessionService.createConfession(request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("发布失败: " + e.getMessage()));
        }
    }

    /**
     * 获取表白墙
     */
    @GetMapping("/wall")
    public ResponseEntity<ApiResponse<List<ConfessionResponse>>> getWall() {
        try {
            List<ConfessionResponse> confessions = confessionService.getAllConfessions();
            return ResponseEntity.ok(ApiResponse.success(confessions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("获取失败: " + e.getMessage()));
        }
    }
}