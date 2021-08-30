/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Location;
import java.util.List;

/**
 *
 * @author powel
 */
public interface LocationDAO {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void removeLocationById(int id);
    void updateLocation(Location location);
    public List<Location> getLocationsBySupe(int id);
}
