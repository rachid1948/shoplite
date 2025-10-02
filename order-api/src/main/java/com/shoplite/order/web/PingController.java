package com.shoplite.order.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/api/orders/ping")
    public String ping() {
        return "pong";
    }
}
