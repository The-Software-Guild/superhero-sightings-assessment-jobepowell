/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LocationDAODB implements LocationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOC_BY_ID = "SELECT * FROM location WHERE idLocation= ?;";
            return jdbc.queryForObject(GET_LOC_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCS = "SELECT * FROM location;";
        return jdbc.query(GET_ALL_LOCS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOC = "INSERT INTO location(LocationName,LocationDescription,LocationAddress,LocationLatitude,LocationLongitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOC,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    @Transactional
    public void removeLocationById(int id) {
        final String DELETE_SUPER_SIGHTING = "DELETE ss.* FROM super_sighting ss JOIN sighting  s ON ss.sighting_idSighting = s.idSighting WHERE s.Location_idLocation = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE Location_idLocation = ?";
        jdbc.update(DELETE_SIGHTING,id );
        
        final String DELETE_LOCATION = "DELETE FROM location where idLocation = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOC = "UPDATE location SET LocationName = ?, LocationDescription = ?,"
                + "LocationAddress = ?, LocationLatitude = ?, LocationLongitude = ? WHERE idLocation = ?";
        jdbc.update(UPDATE_LOC,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    public List<Location> getLocationsBySupe(int id) {
        final String LOCATION_BY_SUPER = "SELECT location.* FROM location INNER JOIN sighting ON idLocation = Location_idLocation INNER JOIN super_sighting ON idSighting = Sighting_idSighting WHERE Super_idSuper = ?";
        return jdbc.query(LOCATION_BY_SUPER, new LocationMapper(), id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("idLocation"));
            loc.setName(rs.getString("LocationName"));
            loc.setDescription(rs.getString("LocationDescription"));
            loc.setAddress(rs.getString("LocationAddress"));
            loc.setLatitude(rs.getString("LocationLatitude"));
            loc.setLongitude(rs.getString("LocationLongitude"));

            return loc;
        }
    }

}
