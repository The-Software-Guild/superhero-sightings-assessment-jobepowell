/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Sighting;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class SightingDAODBTest {
    
    @Autowired
    SightingDAODB dao;
    
    @Autowired
    LocationDAODB locDao;
    
    public SightingDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Sighting> sightings = dao.getAllSightings();
        for(Sighting sighting : sightings)
            dao.removeSightingById(sighting.getId());
        if(!dao.getAllSightings().isEmpty())
            fail("Sightings not cleared from DB for testing");
        
        List<Location> locations = locDao.getAllLocations();
        for(Location loc : locations)
            locDao.removeLocationById(loc.getId());
        if(!locDao.getAllLocations().isEmpty())
            fail("Location DB not cleared before testing");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSightingById method, of class SightingDAODB.
     */
    @Test
    public void testGetSightingById() {
        Sighting sight = makeTestSightings();
        
        sight = dao.addSighting(sight);
        Sighting toGet = dao.getSightingById(sight.getId());
        
        assertEquals(sight,toGet,"Sighting retrieved does not match original");
    }

    /**
     * Test of getAllSightings method, of class SightingDAODB.
     */
    @Test
    public void testGetAllSightings() {
        Location loc = makeTestLocation();
        Sighting sight1 = makeTestSightings();
        Sighting sight2 = new Sighting();
        sight2.setDate(LocalDate.now());
        sight2.setLocation(loc);
        
        sight1 = dao.addSighting(sight1);
        sight2 = dao.addSighting(sight2);
        
        List<Sighting> sightings = dao.getAllSightings();
        
        assertEquals(2,sightings.size(),"Both sightings not added to db");
        assertTrue(sightings.contains(sight1),"First sighting not added");
        assertTrue(sightings.contains(sight2),"Last sighting not added");
    }

    /**
     * Test of addSighting method, of class SightingDAODB.
     */
    @Test
    public void testAddSighting() {
        Sighting sight = makeTestSightings();
        sight = dao.addSighting(sight);
        
        List<Sighting> sightings = dao.getAllSightings();
        
        assertEquals(1,sightings.size(),"Sighting not added to DB");
        assertEquals(sight,sightings.get(0));
    }

    /**
     * Test of removeSightingById method, of class SightingDAODB.
     */
    @Test
    public void testRemoveSightingById() {
        Sighting sight = makeTestSightings();
        sight = dao.addSighting(sight);
        
        List<Sighting> sightings = dao.getAllSightings();
        assertFalse(sightings.isEmpty(),"Sighting DB should not be empty");
        
        dao.removeSightingById(sight.getId());
        sightings = dao.getAllSightings();
        
        assertTrue(sightings.isEmpty(),"Sighting DB should be empty");
    }

    /**
     * Test of updateSighting method, of class SightingDAODB.
     */
    @Test
    public void testUpdateSighting() {
        LocalDate newDate = LocalDate.EPOCH;
        Sighting sight1 = makeTestSightings();
        Sighting sight2 = new Sighting();
        sight1 = dao.addSighting(sight1);
        sight2 = dao.getSightingById(sight1.getId());
        
        assertEquals(sight1,sight2,"Original Sighting does not match Sighting in DB");
        
        sight1.setDate(newDate);
        dao.updateSighting(sight1);
        
        sight1 = dao.getSightingById(sight1.getId());
        
        assertNotEquals(sight1,sight2,"Sightings should not be equal anymore");
        assertEquals(sight1.getDate(),newDate,"Date not updated in DB");
    }
    
    public Sighting makeTestSightings(){
        Sighting sight = new Sighting();
        Location loc = makeTestLocation();
        sight.setDate(LocalDate.now());
        sight.setLocation(loc);
        
        return sight;
    }
    
    public Location makeTestLocation(){
        Location loc = new Location();
        loc.setAddress("423 Jones Ave.");
        loc.setLatitude("47.201");
        loc.setLongitude("20.1112");
        loc.setName("King's Cavern");
        loc.setDescription("A deep ravine with winding tunnels");
        locDao.addLocation(loc);
        return loc;
    }
    
}
