/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Super;
import java.util.List;

/**
 *
 * @author powel
 */
public interface SuperDAO {
    Super getSuperById(int id);
    List<Super> getAllSupers();
    Super addSuper(Super sup);
    void removeSuperById(int id);
    void updateSuper(Super sup);
    List<Super> getSupesByLocation(int id);
}
