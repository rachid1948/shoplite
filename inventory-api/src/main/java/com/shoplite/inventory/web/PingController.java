package com.shoplite.inventory.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/api/inventory/ping")
    public String ping() {
        return "pong";
    }
}
