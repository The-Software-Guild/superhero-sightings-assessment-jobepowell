/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.service;

import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Organization;
import com.jobep.SuperheroAssessment.models.Sighting;
import com.jobep.SuperheroAssessment.models.Super;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author powel
 */
public interface ServiceLayer {
    
    public List<Super> getSupesByLocation(int id);
    public List<Location> getLocationsBySupe(int id);
    public List<Sighting> getSightingByDate(LocalDate date);
    
}
