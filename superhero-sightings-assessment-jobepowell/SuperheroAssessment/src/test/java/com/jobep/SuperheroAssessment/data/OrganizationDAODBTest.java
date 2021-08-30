/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Organization;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author powel
 */
@SpringBootTest
public class OrganizationDAODBTest {
    
    @Autowired
    OrganizationDAODB dao;
    
    public OrganizationDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Organization> orgs = dao.getAllOrganizations();
        for(Organization org : orgs){
            dao.removeOrganizationById(org.getId());
        }
        if(!dao.getAllOrganizations().isEmpty())
            fail("Organization DB not wiped before testing");
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrganizationById method, of class OrganizationDAODB.
     */
    @Test
    public void testGetOrganizationById() {
        Organization org = makeTestOrg();
        org = dao.addOrganization(org);
        Organization dummy = dao.getOrganizationById(org.getId());
        
        assertEquals(dummy,org,"Retrieved Org does not match added Org");
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDAODB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization org = makeTestOrg();
        org = dao.addOrganization(org);
        
        Organization org2 = new Organization();
        org2.setName("League of Evil");
        org2.setAddress("123 Evil Street");
        org2.setDescription("Does evil things");
        org2.setContact("666-666-6666");
        
        org2 = dao.addOrganization(org2);
        
        List<Organization> orgs = dao.getAllOrganizations();
        
        assertEquals(2,orgs.size(),"Both organizations were not added");
        assertTrue(orgs.contains(org),"First org not correctly added to DB");
        assertTrue(orgs.contains(org2),"Last org not correctly added to DB");
    }

    /**
     * Test of addOrganization method, of class OrganizationDAODB.
     */
    @Test
    public void testAddOrganization() {
        Organization org = makeTestOrg();
        org = dao.addOrganization(org);
        
        List<Organization> orgs = dao.getAllOrganizations();
        
        assertEquals(1,orgs.size(),"Organization not added to DB");
    }

    /**
     * Test of removeOrganizationById method, of class OrganizationDAODB.
     */
    @Test
    public void testRemoveOrganizationById() {
        Organization org = makeTestOrg();
        org = dao.addOrganization(org);
        List<Organization> orgs = dao.getAllOrganizations();
        
        assertEquals(1,orgs.size(),"test org not added to DB");
        
        dao.removeOrganizationById(org.getId());
        orgs = dao.getAllOrganizations();
        
        assertEquals(0,orgs.size(),"test org not removed from DB");
    }

    /**
     * Test of updateOrganization method, of class OrganizationDAODB.
     */
    @Test
    public void testUpdateOrganization() {
        String newDescription = "Does moderately friendly acts";
        Organization org2 = new Organization();
        Organization org = makeTestOrg();
        org = dao.addOrganization(org);
        org2 = dao.getOrganizationById(org.getId());
        
        assertEquals(org,org2,"Original organization does not match org in DB");
        
        org.setDescription(newDescription);
        
        dao.updateOrganization(org);
        
        org = dao.getOrganizationById(org.getId());
        
        assertNotEquals(org,org2,"Org not successfully updated");
        assertEquals(org.getDescription(), newDescription, "Description not updated in DB");
       
        
        
        
    }
    
    public Organization makeTestOrg(){
        Organization org = new Organization();
        org.setAddress("123 John Wayne Ave.");
        org.setName("League of Good");
        org.setContact("111-111-1111");
        org.setDescription("Commits good acts.");
        return org;
    }
    
}
