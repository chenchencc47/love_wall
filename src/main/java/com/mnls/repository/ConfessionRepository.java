package com.mnls.repository;

import com.mnls.entity.Confession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfessionRepository extends JpaRepository<Confession, Long> {

    // 按创建时间倒序获取所有表白
    @Query("SELECT c FROM Confession c ORDER BY c.createdTime DESC")
    List<Confession> findAllOrderByCreatedTimeDesc();

    Optional<Confession> findTopByFromWhoAndToWhoAndContentOrderByCreatedTimeDesc(
            String fromWho, String toWho, String content);
}