/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Power;
import java.util.List;

/**
 *
 * @author powel
 */
public interface PowerDAO {
    Power getPowerById(int id);
    List<Power> getAllPowers();
    Power addPower(Power power);
    void removePowerById(int id);
    void updatePower(Power power);
}
