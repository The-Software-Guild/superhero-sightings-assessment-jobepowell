/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.models.Power;
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
public class PowerDAODB implements PowerDAO{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Power getPowerById(int id) {
        try{
            final String GET_POWER_BY_ID = "SELECT * FROM superpower WHERE idSuperPower = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID,new PowerMapper(),id);
        } catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String GET_ALL_POWER = "SELECT * FROM superpower";
        return jdbc.query(GET_ALL_POWER, new PowerMapper());
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO superpower(SuperPowerName) VALUES (?)";
        jdbc.update(INSERT_POWER,
                power.getName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    @Transactional
    public void removePowerById(int id) {
        final String DELETE_SUPER = "DELETE FROM super WHERE idSuperPower = ?";
        jdbc.update(DELETE_SUPER,id);
        
        final String DELETE_POWER = "DELETE FROM superpower WHERE idSuperPower = ?";
        jdbc.update(DELETE_POWER,id);
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE superpower SET SuperPowerName = ? WHERE idSuperPower = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getId());
    }
    
    public static final class PowerMapper implements RowMapper<Power>{
        
        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException{
            Power power = new Power();
            power.setId(rs.getInt("idSuperPower"));
            power.setName(rs.getString("SuperPowerName"));
            return power;
        }
    }
}
