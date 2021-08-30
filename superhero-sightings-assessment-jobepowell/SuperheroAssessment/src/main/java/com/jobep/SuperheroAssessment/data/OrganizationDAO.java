/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Organization;
import com.jobep.SuperheroAssessment.models.Super;
import java.util.List;

/**
 *
 * @author powel
 */
public interface OrganizationDAO {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization org);
    void removeOrganizationById(int id);
    void updateOrganization(Organization org);
    public List<Organization> getOrganizationsForSuper(int id);
    public List<Super> getSupersForOrg(int id);
}
