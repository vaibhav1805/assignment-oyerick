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

    @Scheduled(fixedRate = 5000)
    public void fireUpdate() {
        this.template.convertAndSend("/topic/updates", trackerService.getAllDrivers());
    }
}