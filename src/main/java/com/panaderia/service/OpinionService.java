package com.panaderia.service;

import com.panaderia.entity.Opinion;
import com.panaderia.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    public void guardarOpinion(Opinion opinion) {
        opinionRepository.save(opinion);
    }
}
