/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.controller;

import com.jobep.SuperheroAssessment.models.Organization;
import com.jobep.SuperheroAssessment.models.Super;
import com.jobep.SuperheroAssessment.service.ServiceLayerDB;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author powel
 */

@Controller
public class OrganizationController {
    @Autowired
    ServiceLayerDB service;
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model){
        List<Organization> organizations = service.getAllOrganizations();
        List<Super> supers = service.getAllSupers();
        model.addAttribute("organizations",organizations);
        model.addAttribute("supers",supers);
        model.addAttribute("errors", violations);
        
        return "organizations";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String[] superIds = request.getParameterValues("superId");
        
        System.out.println(superIds);
        Organization org = new Organization();
        org.setName(name);
        org.setDescription(description);
        org.setAddress(address);
        org.setContact(contact);
        
        List<Super> supers = new ArrayList<>();
        if(superIds != null){
            for(String superId : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(superId)));
            }
        }
        org.setSupers(supers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);
        if(violations.isEmpty())
            service.addOrganization(org);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model){
        Organization org = service.getOrganizationById(id);
        model.addAttribute("org",org);
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        service.removeOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model){
        Organization org = service.getOrganizationById(id);
        List<Super> supers = service.getAllSupers();
        model.addAttribute("org",org);
        model.addAttribute("supers",supers);
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditCourse(@Valid Organization org, BindingResult result, HttpServletRequest request,Model model) {
        String[] superIds = request.getParameterValues("superId");
        
        
        List<Super> supers = new ArrayList<>();
        if(superIds != null){
            for(String superId : superIds) {
                supers.add(service.getSuperById(Integer.parseInt(superId)));
            }
        }
        
        org.setSupers(supers);
        
        if(result.hasErrors()){
            model.addAttribute("supers",service.getAllSupers());
            model.addAttribute("org",org);
            return "editOrganization";
        }
        
        service.updateOrganization(org);
        
        return "redirect:/organizationDetail?id=" + org.getId();
    }
}
