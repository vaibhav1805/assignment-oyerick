package com.oyerickshaw.tracker.controller;

import com.oyerickshaw.tracker.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TrackerService trackerService;

    /**
     * Push All driver locations every 1s
     */
    @Scheduled(fixedRate = 1000)
    public void fireUpdate() {
        this.template.convertAndSend("/topic/updates", trackerService.getAllDrivers());
    }
}