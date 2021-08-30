/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Power;
import com.jobep.SuperheroAssessment.models.Super;
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
public class SuperDAODBTest {
    
    @Autowired
    SuperDAODB dao;
    
    @Autowired
    PowerDAODB powDao;
    
    public SuperDAODBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Super> supers = dao.getAllSupers();
        for(Super sup : supers)
            dao.removeSuperById(sup.getId());
        if(!dao.getAllSupers().isEmpty())
            fail("Supers dao could not be cleared for testing");
        
        List<Power> powers = powDao.getAllPowers();
        for(Power pow : powers)
            powDao.removePowerById(pow.getId());
        if(!powDao.getAllPowers().isEmpty())
            fail("Power dao could not be cleared for testing");
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSuperById method, of class SuperDAODB.
     */
    @Test
    public void testGetSuperById() {
        Super sup = makeTestSuper1();
        sup = dao.addSuper(sup);
        Super toGet = dao.getSuperById(sup.getId());
        
        assertEquals(sup,toGet,"Retrieved super does not match original");
    }

    /**
     * Test of getAllSupers method, of class SuperDAODB.
     */
    @Test
    public void testGetAllSupers() {
        Super super1 = makeTestSuper1();
        Super super2 = makeTestSuper2();
        super1 = dao.addSuper(super1);
        super2 = dao.addSuper(super2);
        
        List<Super> supers = dao.getAllSupers();
        
        assertEquals(2,supers.size(),"Wrong amount of supers in DB");
        assertTrue(supers.contains(super1),"First super not added to DB");
        assertTrue(supers.contains(super2),"Last super not added to DB");
    }

    /**
     * Test of addSuper method, of class SuperDAODB.
     */
    @Test
    public void testAddSuper() {
        Super sup = makeTestSuper1();
        sup = dao.addSuper(sup);
        
        List<Super> supers = dao.getAllSupers();
        
        assertFalse(supers.isEmpty());
        
        assertTrue(supers.contains(sup),"Created Super not added to dao");
    }

    /**
     * Test of removeSuperById method, of class SuperDAODB.
     */
    @Test
    public void testRemoveSuperById() {
        Super sup = makeTestSuper1();
        sup = dao.addSuper(sup);
        
        List<Super> supers = dao.getAllSupers();
        
        assertFalse(supers.isEmpty(), "Dao should not be empty");
        
        dao.removeSuperById(sup.getId());
        
        supers = dao.getAllSupers();
        
        assertTrue(supers.isEmpty(),"Dao should be empty");
    }

    /**
     * Test of updateSuper method, of class SuperDAODB.
     */
    @Test
    public void testUpdateSuper() {
        String newName = "Brainlord";
        Super sup = makeTestSuper1();
        sup = dao.addSuper(sup);
        Super sup2 = dao.getSuperById(sup.getId());
        
        assertEquals(sup,sup2,"Retrieved Super does not match added Super");
        
        sup.setName(newName);
        dao.updateSuper(sup);
        
        assertNotEquals(sup,sup2,"Updated SUper should not match original");
        
        sup2 = dao.getSuperById(sup.getId());
        
        assertEquals(sup,sup2,"Updated Super does not match Super in DB");
        assertEquals(newName,sup.getName(),"Name was not updated in DB");
        
    }
    
    public Super makeTestSuper1(){
        Super sup = new Super();
        sup.setName("Brainiac");
        sup.setDescription("Uses his massive brain to fight");
        sup.setSuperPower(makeTestPower());
        return sup;
    }
    
    public Super makeTestSuper2(){
        Super sup = new Super();
        sup.setName("Freezer");
        sup.setDescription("Freezes his opponents");
        sup.setSuperPower(makeTestPower());
        return sup;
    }
    
    public Power makeTestPower(){
        Power power = new Power();
        power.setName("Fireball");
        power = powDao.addPower(power);
        return power;
    }
    
}
