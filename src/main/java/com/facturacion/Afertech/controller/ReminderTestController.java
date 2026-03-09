package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.scheduler.InvoiceReminderScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderTestController {

    private final InvoiceReminderScheduler scheduler;

    public ReminderTestController(InvoiceReminderScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping("/test-reminder")
    public String testReminder() {

        scheduler.runNowForTest();

        return "Reminder executed";
    }
}

