package com.kh.Palette_BackEnd.component;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EnableScheduling
@Component
public class ChatCleanupScheduler {

    private final ChatRepository chatRepository;

    public ChatCleanupScheduler(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Scheduled(cron = "0 0 4 * * ?") // 새벽 4시에 스케줄링
    public void deleteOldChats() {
        // 30일 후 메세지 자동 삭제
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
        chatRepository.deleteByRegDateBefore(cutoffDate);
    }
}
