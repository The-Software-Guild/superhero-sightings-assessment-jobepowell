/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Power;
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
public class PowerDAODBTest {
    
    @Autowired
    PowerDAODB dao;
    
    public PowerDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Power> powers = dao.getAllPowers();
        for(Power power : powers)
            dao.removePowerById(power.getId());
        
        if(!dao.getAllPowers().isEmpty())
            fail("Powers DB failed to be cleared for testing");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPowerById method, of class PowerDAODB.
     */
    @Test
    public void testGetPowerById() {
        Power power = new Power();
        power.setName("Fireball");
        power = dao.addPower(power);
        
        Power toGet = dao.getPowerById(power.getId());
        
        assertEquals(power,toGet,"Retrieved power does not match original");
    }

    /**
     * Test of getAllPowers method, of class PowerDAODB.
     */
    @Test
    public void testGetAllPowers() {
        Power power1 = new Power();
        Power power2 = new Power();
        power1.setName("Fireball");
        power2.setName("Ice Stab");
        
        power1 = dao.addPower(power1);
        power2 = dao.addPower(power2);
        
        List<Power> powers = dao.getAllPowers();
        assertEquals(2,powers.size(),"Powers not added correctly");
        
        assertTrue(powers.contains(power1),"First Power not added");
        assertTrue(powers.contains(power2),"Last Power not added");
    }

    /**
     * Test of addPower method, of class PowerDAODB.
     */
    @Test
    public void testAddPower() {
        Power power = new Power();
        power.setName("Fireball");
        
        power = dao.addPower(power);
        
        List<Power> powers = dao.getAllPowers();
        
        assertEquals(1,powers.size(), "Power not added to db");
        
        assertEquals(power,powers.get(0),"Power does not match power in DB0");
    }

    /**
     * Test of removePowerById method, of class PowerDAODB.
     */
    @Test
    public void testRemovePowerById() {
        Power power = new Power();
        power.setName("Fireball");
        
        power = dao.addPower(power);
        
        List<Power> powers = dao.getAllPowers();
        
        assertEquals(1,powers.size(), "Power not initially added to db");
        
        dao.removePowerById(power.getId());
        powers = dao.getAllPowers();
        
        assertEquals(0, powers.size(),"Power not removed from DB");
        
    }

    /**
     * Test of updatePower method, of class PowerDAODB.
     */
    @Test
    public void testUpdatePower() {
        String newPower = "Ice shard";
        Power power = new Power();
        power.setName("Fireball");
        power = dao.addPower(power);
        Power OGPower = dao.getPowerById(power.getId());
        
        assertEquals(power,OGPower, "Original power and power from DB not equal");
        
        power.setName(newPower);
        dao.updatePower(power);
        
        power = dao.getPowerById(power.getId());
        
        assertNotEquals(power,OGPower, "Power not updated in DB");
        assertEquals(power.getName(),newPower,"Power name not updated in DB");
    }
    
}
