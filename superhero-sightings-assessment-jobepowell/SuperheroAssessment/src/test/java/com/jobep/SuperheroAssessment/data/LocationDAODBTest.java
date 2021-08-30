/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Location;
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
public class LocationDAODBTest {
    
    @Autowired
    LocationDAODB dao;
    
    public LocationDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Location> locations = dao.getAllLocations();
        for(Location loc : locations)
            dao.removeLocationById(loc.getId());
        if(!dao.getAllLocations().isEmpty())
            fail("Location DB not cleared before testing");
   
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocationDAODB.
     */
    @Test
    public void testGetLocationById() {
        Location loc = makeTestLoc();
        loc = dao.addLocation(loc);
        
        Location toGet = dao.getLocationById(loc.getId());
        
        assertEquals(loc,toGet,"Retrieved Location does not match original");
    }

    /**
     * Test of getAllLocations method, of class LocationDAODB.
     */
    @Test
    public void testGetAllLocations() {
        Location loc = makeTestLoc();
        Location loc2 = new Location();
        loc2.setName("New York City Hall");
        loc2.setAddress("4455 Brooklyn Dr.");
        loc2.setDescription("A dull building in NYC");
        loc2.setLatitude("420.69");
        loc2.setLongitude("60.420");
        
        loc = dao.addLocation(loc);
        loc2 = dao.addLocation(loc2);
        
        List<Location> locs = dao.getAllLocations();
        
        assertEquals(2,locs.size(),"Location DB does not have both locations added");
        assertTrue(locs.contains(loc),"Does not contain first Location");
        assertTrue(locs.contains(loc2),"Does not contain last location");
        
        
    }

    /**
     * Test of addLocation method, of class LocationDAODB.
     */
    @Test
    public void testAddLocation() {
        Location loc = makeTestLoc();
        loc = dao.addLocation(loc);
        
        List<Location> locs = dao.getAllLocations();
        
        assertEquals(1,locs.size(),"Too many or too few locations in db");
        assertEquals(loc , locs.get(0), "Wrong Location added to db");
    }

    /**
     * Test of removeLocationById method, of class LocationDAODB.
     */
    @Test
    public void testRemoveLocationById() {
        Location loc = makeTestLoc();
        loc = dao.addLocation(loc);
        List<Location> locs = dao.getAllLocations();
        
        assertNotEquals(0,locs.size(),"Location not added");
        
        dao.removeLocationById(loc.getId());
        locs = dao.getAllLocations();
        
        assertEquals(0,locs.size(),"Location not removed");
    }

    /**
     * Test of updateLocation method, of class LocationDAODB.
     */
    @Test
    public void testUpdateLocation() {
        String newName = "Madison Square Garden";
        Location loc = makeTestLoc();
        loc = dao.addLocation(loc);
        Location loc2 = dao.getLocationById(loc.getId());
        
        assertEquals(loc,loc2,"Locations not equal");
        
        loc.setName(newName);
        
        dao.updateLocation(loc);
        loc = dao.getLocationById(loc.getId());
        
        assertNotEquals(loc,loc2,"Location in DB not updated");
        assertEquals(loc.getName(),newName,"Name was not updated in DB");
        
        
    }
    
    public Location makeTestLoc(){
        Location loc = new Location();
        loc.setAddress("423 Jones Ave.");
        loc.setLatitude("47.201");
        loc.setLongitude("20.1112");
        loc.setName("King's Cavern");
        loc.setDescription("A deep ravine with winding tunnels");
        return loc;
    }
    
    
}
