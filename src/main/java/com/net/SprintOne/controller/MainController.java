package com.net.SprintOne.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class MainController {

    @GetMapping("/date")
    public String getDate() {
        Date d1 = new Date();
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(d1);
    }
}
