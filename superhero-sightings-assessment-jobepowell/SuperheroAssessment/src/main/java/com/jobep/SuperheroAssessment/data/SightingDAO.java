/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author powel
 */
public interface SightingDAO {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void removeSightingById(int id);
    void updateSighting(Sighting sighting);
    List<Sighting> getSightingByDate(LocalDate date);
    public List<Sighting> getRecentSightings();
}
