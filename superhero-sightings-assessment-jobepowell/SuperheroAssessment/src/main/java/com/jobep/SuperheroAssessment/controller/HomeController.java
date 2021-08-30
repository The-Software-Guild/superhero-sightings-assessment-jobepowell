/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Sighting;
import com.jobep.SuperheroAssessment.service.ServiceLayerDB;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author powel
 */
@Controller
public class HomeController {
    
    @Autowired
    ServiceLayerDB service;
    
    @GetMapping("home")
    public String displayLocations(Model model){
        List<Sighting> sightings = service.getRecentSightings();
        model.addAttribute("sightings", sightings);
        return "home";
    }
    
}
