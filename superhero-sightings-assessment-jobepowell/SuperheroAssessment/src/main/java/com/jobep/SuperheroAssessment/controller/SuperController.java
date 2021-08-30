/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Power;
import com.jobep.SuperheroAssessment.models.Super;
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
public class SuperController {
    
    @Autowired
    ServiceLayerDB service;
    
    Set<ConstraintViolation<Super>> violations = new HashSet<>();
    
    @GetMapping("supers")
    public String displaySupers(Model model){
        List<Super> supers = service.getAllSupers();
        model.addAttribute("supers",supers);
        model.addAttribute("powers",service.getAllPowers());
        model.addAttribute("errors",violations);
        return "supers";
    }
    
    @PostMapping("addSuper")
    public String addSighting(HttpServletRequest request){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String idSuperPower = request.getParameter("idSuperPower");
        Power pow = new Power();
        try{
            pow = service.getPowerById(Integer.parseInt(idSuperPower));
        }catch(Exception e){
            pow = null;
        }
        
        Super sup = new Super();
        sup.setName(name);
        sup.setDescription(description);
        sup.setSuperPower(pow);    
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sup);
        if(violations.isEmpty())
            service.addSuper(sup);
        
        return "redirect:/supers";
    }
    
    @GetMapping("superDetail")
    public String superDetail(Integer id, Model model){
        Super sup = service.getSuperById(id);
        model.addAttribute("sup",sup);
        return "superDetail";
    }
    
    @GetMapping("deleteSuper")
    public String deleteSuper(Integer id) {
        service.removeSuperById(id);
        return "redirect:/supers";
    }
    
    @GetMapping("editSuper")
    public String editSuper(Integer id, Model model){
        Super sup = service.getSuperById(id);
        List<Power> powers = service.getAllPowers();
        model.addAttribute("sup",sup);
        model.addAttribute("powers",powers);
        return "editSuper";
    }
    
    @PostMapping("editSuper")
    public String performEditSuper(@Valid Super sup, BindingResult result,HttpServletRequest request,Model model) {
        String idSuperPower = request.getParameter("idSuperPower");
        
        sup.setSuperPower(service.getPowerById(Integer.parseInt(idSuperPower)));
        
        
        if(result.hasErrors()){
            model.addAttribute("sup",sup);
            model.addAttribute("powers",service.getAllPowers());
            return "editSuper";
        }
        service.updateSuper(sup);
        
        return "redirect:/superDetail?id=" + sup.getId();
    }
}
