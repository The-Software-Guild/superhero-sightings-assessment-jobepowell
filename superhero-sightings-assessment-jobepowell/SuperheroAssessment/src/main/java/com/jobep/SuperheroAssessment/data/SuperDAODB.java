/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.data.PowerDAODB.PowerMapper;
import com.jobep.SuperheroAssessment.models.Power;
import com.jobep.SuperheroAssessment.models.Super;
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
public class SuperDAODB implements SuperDAO{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Super getSuperById(int id) {
        try {
            final String GET_SUPER_BY_ID = "SELECT * FROM super WHERE idSuper = ?";
            Super sup =  jdbc.queryForObject(GET_SUPER_BY_ID, new SuperMapper(), id);
            sup.setSuperPower(getPowerForSuper(id));
            return sup;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private Power getPowerForSuper(int id){
        final String SELECT_POWER_FOR_SUPER = "SELECT p.* FROM superpower p JOIN super s ON p.idSuperPower = s.idSuperPower WHERE s.idSuper = ?";
        return jdbc.queryForObject(SELECT_POWER_FOR_SUPER, new PowerMapper(), id);
    }

    @Override
    public List<Super> getAllSupers() {
        final String GET_ALL_SUPERS = "SELECT * FROM super";
        List<Super> supers =  jdbc.query(GET_ALL_SUPERS, new SuperMapper());
        for(Super sup : supers)
            sup.setSuperPower(getPowerForSuper(sup.getId()));
        return supers;
    
    }

    @Override
    @Transactional
    public Super addSuper(Super sup) {
        final String INSERT_SUPER = "INSERT INTO super(SuperName,SuperDescription,idSuperPower) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPER,
                sup.getName(),
                sup.getDescription(),
                sup.getSuperPower().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sup.setId(newId);
        return sup;
    }

    @Override
    @Transactional
    public void removeSuperById(int id) {
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM Super_SuperOrganization WHERE Super_idSuper = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, id);

        final String DELETE_SUPER_SIGHTING = "DELETE FROM Super_Sighting WHERE Super_idSuper = ?";
        jdbc.update(DELETE_SUPER_SIGHTING, id);

        final String DELETE_SUPER = "DELETE FROM super WHERE idSuper = ?";
        jdbc.update(DELETE_SUPER, id);
    }

    @Override
    public void updateSuper(Super sup) {
        final String UPDATE_SUPER = "UPDATE super SET SuperName = ?, SuperDescription = ?, "
                + "idSuperPower = ? WHERE idSuper = ?";
        jdbc.update(UPDATE_SUPER,
                sup.getName(),
                sup.getDescription(),
                sup.getSuperPower().getId(),
                sup.getId());
    }
    
    @Override
    public List<Super> getSupesByLocation(int id) {
        final String SUPES_BY_LOCATION = "SELECT super.* FROM super INNER JOIN super_sighting ON idSuper = Super_idSuper INNER JOIN sighting on Sighting_idSighting = idSighting WHERE Location_idLocation = ?";
        return jdbc.query(SUPES_BY_LOCATION, new SuperMapper(),id);
    }
    
    public static final class SuperMapper implements RowMapper<Super> {
        
        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException{
            Super sup = new Super();
            sup.setId(rs.getInt("idSuper"));
            sup.setName(rs.getString("SuperName"));
            sup.setDescription(rs.getString("SuperDescription"));
            
            return sup;
        }
    }
    
}
