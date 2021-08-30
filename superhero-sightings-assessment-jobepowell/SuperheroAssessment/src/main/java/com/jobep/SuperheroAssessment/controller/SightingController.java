/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Sighting;
import com.jobep.SuperheroAssessment.models.Super;
import com.jobep.SuperheroAssessment.service.ServiceLayerDB;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author powel
 */
@Controller
public class SightingController {
    
    
    @Autowired
    ServiceLayerDB service;
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Sighting> sightings = service.getAllSightings();
        List<Location> locations = service.getAllLocations();
        List<Super> supers = service.getAllSupers();
        
        model.addAttribute("sightings", sightings);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        String strDate = request.getParameter("date");
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        LocalDate date;
        try{
            date = LocalDate.parse(strDate);
        } catch(Exception e){
            date = null;
        }
        
        Sighting sight = new Sighting();
        sight.setDate(date);
        sight.setLocation(service.getLocationById(Integer.parseInt(locationId)));
        
        List<Super> supers = new ArrayList<>();
        if(superIds != null){
            for(String superId : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(superId)));
            }
        }
        
        sight.setSupers(supers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sight);
        if(violations.isEmpty())
            service.addSighting(sight);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model){
        Sighting sight = service.getSightingById(id);
        model.addAttribute("sighting", sight);
        return "sightingDetail";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        service.removeSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model){
        Sighting sight = service.getSightingById(id);
        List<Location> locations = service.getAllLocations();
        List<Super> supers = service.getAllSupers();
        model.addAttribute("sighting",sight);
        model.addAttribute("supers",supers);
        model.addAttribute("locations",locations);
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sight, BindingResult result,HttpServletRequest request,Model model) {
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        
        sight.setLocation(service.getLocationById(Integer.parseInt(locationId)));
        
        List<Super> supers = new ArrayList<>();
        if(superIds != null){
            for(String superId : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(superId)));
            }
        } else {
            FieldError error = new FieldError("sight","supers","Must include at least one super");
            result.addError(error);
        }
        
        sight.setSupers(supers);
        
        if(result.hasErrors()){
            model.addAttribute("sighting",sight);
            model.addAttribute("supers",service.getAllSupers());
            model.addAttribute("locations",service.getAllLocations());
            return "editSighting";
        }
        service.updateSighting(sight);
        
        return "redirect:/sightingDetail?id=" + sight.getId();
    }
}
