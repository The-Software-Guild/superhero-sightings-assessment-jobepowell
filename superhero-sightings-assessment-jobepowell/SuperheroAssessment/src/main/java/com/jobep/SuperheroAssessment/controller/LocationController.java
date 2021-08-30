/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.service.ServiceLayerDB;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author powel
 */
@Controller
public class LocationController {
    @Autowired
    ServiceLayerDB service;
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @GetMapping("locations")
    public String displayLocations(Model model){
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        
        Location loc = new Location();
        loc.setName(name);
        loc.setDescription(description);
        loc.setAddress(address);
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(loc);
        if(violations.isEmpty())
            service.addLocation(loc);
        
        return "redirect:/locations";
    }
    
    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model){
        Location loc = service.getLocationById(id);
        model.addAttribute("location", loc);
        return "locationDetail";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        service.removeLocationById(id);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model){
        Location loc = service.getLocationById(id);
        
        model.addAttribute("location",loc);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location loc, BindingResult result,HttpServletRequest request,Model model) {
        
        
        if(result.hasErrors()){
            model.addAttribute("location", loc);
            return "editLocation";
        }
        
        service.updateLocation(loc);
        
        return "redirect:/locationDetail?id=" + loc.getId();
    }
}
