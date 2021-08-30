/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.service;

import com.jobep.SuperheroAssessment.data.LocationDAO;
import com.jobep.SuperheroAssessment.data.OrganizationDAO;
import com.jobep.SuperheroAssessment.data.PowerDAO;
import com.jobep.SuperheroAssessment.data.SightingDAO;
import com.jobep.SuperheroAssessment.data.SuperDAO;
import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Organization;
import com.jobep.SuperheroAssessment.models.Power;
import com.jobep.SuperheroAssessment.models.Sighting;
import com.jobep.SuperheroAssessment.models.Super;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author powel
 */
@Component
public class ServiceLayerDB implements ServiceLayer,LocationDAO,OrganizationDAO,PowerDAO,SightingDAO,SuperDAO {
    
    @Autowired
    LocationDAO lDao;
    
    @Autowired
    OrganizationDAO oDao;
    
    @Autowired
    PowerDAO pDao;
    
    @Autowired
    SightingDAO siDao;
    
    @Autowired
    SuperDAO suDao;
    
    //Specified Service Methods
    @Override
    public List<Super> getSupesByLocation(int id) {
        return suDao.getSupesByLocation(id);
    }

    @Override
    public List<Location> getLocationsBySupe(int id) {
        return lDao.getLocationsBySupe(id);
    }

    @Override
    public List<Sighting> getSightingByDate(LocalDate date) {
        return siDao.getSightingByDate(date);
    }

    @Override
    public List<Super> getSupersForOrg(int id) {
        return oDao.getSupersForOrg(id);
    }
    
    @Override
    public List<Organization> getOrganizationsForSuper(int id) {
        return oDao.getOrganizationsForSuper(id);
    }
    
    //General pass through methods
    //Power
    @Override
    public Power getPowerById(int id){
        return pDao.getPowerById(id);
    }
    @Override
    public List<Power> getAllPowers(){
        return pDao.getAllPowers();
    }
    @Override
    public Power addPower(Power power){
        return pDao.addPower(power);
    }
    @Override
    public void removePowerById(int id){
        pDao.removePowerById(id);
    }
    @Override
    public void updatePower(Power power){
        pDao.updatePower(power);
    }
    //Location
    @Override
    public Location getLocationById(int id){
        return lDao.getLocationById(id);
    }
    @Override
    public List<Location> getAllLocations(){
        return lDao.getAllLocations();
    }
    @Override
    public Location addLocation(Location location){
        return lDao.addLocation(location);
    }
    @Override
    public void removeLocationById(int id){
        lDao.removeLocationById(id);
    }
    @Override
    public void updateLocation(Location location){
        lDao.updateLocation(location);
    }
    
    //Super
    @Override
    public Super getSuperById(int id){
        return suDao.getSuperById(id);
    }
    @Override
    public List<Super> getAllSupers(){
        return suDao.getAllSupers();
    }
    @Override
    public Super addSuper(Super sup){
        return suDao.addSuper(sup);
    }
    @Override
    public void removeSuperById(int id){
        suDao.removeSuperById(id);
    }
    @Override
    public void updateSuper(Super sup){
        suDao.updateSuper(sup);
    }
    
    //Organization
    @Override
    public Organization getOrganizationById(int id){
        return oDao.getOrganizationById(id);
    }
    @Override
    public List<Organization> getAllOrganizations(){
        return oDao.getAllOrganizations();
    }
    @Override
    public Organization addOrganization(Organization org){
        return oDao.addOrganization(org);
    }
    @Override
    public void removeOrganizationById(int id){
        oDao.removeOrganizationById(id);
    }
    @Override
    public void updateOrganization(Organization org){
        oDao.updateOrganization(org);
    }
    
    //Sighting
    @Override
    public Sighting getSightingById(int id){
        return siDao.getSightingById(id);
    }
    @Override
    public List<Sighting> getAllSightings(){
        return siDao.getAllSightings();
    }
    
    @Override
    public Sighting addSighting(Sighting sighting){
        return siDao.addSighting(sighting);
    }
    @Override
    public void removeSightingById(int id){
        siDao.removeSightingById(id);
    }
    @Override
    public void updateSighting(Sighting sighting){
        siDao.updateSighting(sighting);
    }

    @Override
    public List<Sighting> getRecentSightings() {
        return siDao.getRecentSightings();
    }
}
