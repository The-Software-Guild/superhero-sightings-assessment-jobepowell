/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Power;
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
public class PowerController {
    @Autowired
    ServiceLayerDB service;
    
    Set<ConstraintViolation<Power>> violations = new HashSet<>();
    
    @GetMapping("powers")
    public String displayPowers(Model model){
        List<Power> powers = service.getAllPowers();
        model.addAttribute("powers",powers);
        model.addAttribute("errors",violations);
        return "powers";
    }
    
    @PostMapping("addPower")
    public String addPower(HttpServletRequest request){
        String name = request.getParameter("name");
        
        Power pow = new Power();
        pow.setName(name);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(pow);
        if(violations.isEmpty())
            service.addPower(pow);
        
        return "redirect:/powers";
    }
    
    @GetMapping("powerDetail")
    public String powerDetail(Integer id, Model model){
        Power pow = service.getPowerById(id);
        model.addAttribute("power",pow);
        return "powerDetail";
    }
    
    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        service.removePowerById(id);
        return "redirect:/powers";
    }
    
    @GetMapping("editPower")
    public String editPower(Integer id, Model model){
        Power pow = service.getPowerById(id);
        model.addAttribute("power",pow);
        return "editPower";
    }
    
    @PostMapping("editPower")
    public String performEditPower(@Valid Power pow, BindingResult result,HttpServletRequest request,Model model) {
        
        if(result.hasErrors()){
            model.addAttribute("power", pow);
            return "editSighting";
        }
        service.updatePower(pow);
        
        return "redirect:/powerDetail?id=" + pow.getId();
    }
}
