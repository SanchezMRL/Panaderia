package com.panaderia.controller;

import com.panaderia.entity.Opinion;
import com.panaderia.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opinion")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping
    public String guardarOpinion(@RequestBody Opinion opinion) {
        try {
            opinionService.guardarOpinion(opinion);
            return "{\"ok\": true}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"ok\": false}";
        }
    }
}
