/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.data.LocationDAODB.LocationMapper;
import com.jobep.SuperheroAssessment.data.SuperDAODB.SuperMapper;
import com.jobep.SuperheroAssessment.models.Location;
import com.jobep.SuperheroAssessment.models.Sighting;
import com.jobep.SuperheroAssessment.models.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author powel
 */
@Repository
public class SightingDAODB implements SightingDAO{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE idSighting = ?";
            Sighting sight = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sight.setLocation(getLocationForSighting(id));
            sight.setSupers(getSupersForSighting(id));
            return sight;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private Location getLocationForSighting(int id){
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s ON l.idLocation = s.Location_idLocation WHERE s.idSighting = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }
    
    private List<Super> getSupersForSighting(int id){
        final String SELECT_SUPERS_FOR_SIGHTING = "SELECT s.* FROM super s"
                + " JOIN super_sighting ss ON s.idSuper = ss.super_idsuper WHERE sighting_idsighting = ?";
        return jdbc.query(SELECT_SUPERS_FOR_SIGHTING,new SuperMapper(), id);
    }
    
    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sights =  jdbc.query(GET_ALL_SIGHTINGS,new SightingMapper());
        associateLocationAndSupers(sights);
        
        return sights;
    }
    
    @Override
    public List<Sighting> getRecentSightings(){
        final String GET_RECENT_SIGHTINGS = "SELECT * " +
                                        "    FROM sighting s " +
                                        "    ORDER BY s.SightingDate DESC "+
                                        "    LIMIT 10";
        
        List<Sighting> sights = jdbc.query(GET_RECENT_SIGHTINGS, new SightingMapper());
        associateLocationAndSupers(sights);
        return sights;
    }
    
    private void associateLocationAndSupers(List<Sighting> sights){
        for(Sighting sight : sights){
            sight.setLocation(getLocationForSighting(sight.getId()));
            sight.setSupers(getSupersForSighting(sight.getId()));
        }
    }
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(SightingDate,Location_idLocation) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SIGHTING,
                java.sql.Date.valueOf(sighting.getDate()),
                sighting.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertSuperSighting(sighting);
        return sighting;
    }

    private void insertSuperSighting(Sighting sight){
        final String INSERT_SUPER_SIGHTING = "INSERT INTO super_Sighting(super_idSuper,sighting_idSighting) VALUES(?,?)";
        for(Super sup : sight.getSupers())
            jdbc.update(INSERT_SUPER_SIGHTING,sup.getId(),sight.getId());
    }
    
    @Override
    @Transactional
    public void removeSightingById(int id) {
        final String DELETE_SUPER_SIGHTING = "DELETE FROM Super_Sighting WHERE Sighting_idSighting = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE idSighting = ?";
        jdbc.update(DELETE_SIGHTING,id);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "Update sighting SET SightingDate = ?, Location_idLocation = ? WHERE idSighting = ?";
        jdbc.update(UPDATE_SIGHTING,
               sighting.getDate(),
               sighting.getLocation().getId(),
               sighting.getId());
        
        final String DELETE_SUPER_SIGHTING = "DELETE FROM super_sighting WHERE sighting_idsighting = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, sighting.getId());
        insertSuperSighting(sighting);
    }

    @Override
    public List<Sighting> getSightingByDate(LocalDate date) {
        final String SIGHTING_BY_DATE = "SELECT sighting.* FROM sighting WHERE SightingDate = ?";
        return jdbc.query(SIGHTING_BY_DATE, new SightingMapper(), java.sql.Date.valueOf(date));
    }
    
    public static final class SightingMapper implements RowMapper<Sighting>{
        
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException{
            Sighting sight = new Sighting();
            sight.setId(rs.getInt("idSighting"));
            sight.setDate(rs.getDate("SightingDate").toLocalDate());
            return sight;
        }
    }  
}
