/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.SuperheroAssessment.data;

import com.jobep.SuperheroAssessment.data.SuperDAODB.SuperMapper;
import com.jobep.SuperheroAssessment.models.Organization;
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
public class OrganizationDAODB implements OrganizationDAO{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORG_BY_ID = "SELECT * FROM superOrganization WHERE idSuperOrganization = ?";
            Organization org =  jdbc.queryForObject(GET_ORG_BY_ID, new OrganizationMapper(), id);
            org.setSupers(getSupersForOrg(id));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Super> getSupersForOrg(int id){
        final String SELECT_SUPERS_FOR_ORG = "SELECT s.* FROM super s JOIN super_superorganization sso ON sso.super_idSuper = s.idSuper WHERE sso.superorganization_idsuperorganization = ?";
        return jdbc.query(SELECT_SUPERS_FOR_ORG, new SuperMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGS = "SELECT * FROM superOrganization";
        List<Organization> orgs =  jdbc.query(GET_ALL_ORGS,new OrganizationMapper());
        associateSupers(orgs);
        return orgs;
    }
    
    private void associateSupers(List<Organization> orgs){
        for(Organization org : orgs)
            org.setSupers(getSupersForOrg(org.getId()));
            
    }
    
    @Override
    public List<Organization> getOrganizationsForSuper(int id){
        final String SELECT_ORGS_FOR_SUPER = "SELECT o.* FROM superorganization o JOIN"
                + " super_superorganization sso ON sso.superorganization_idsuperoganization = o.idsuperorganization WHERE sso.super_idsuper = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_SUPER, new OrganizationMapper(), id);
        associateSupers(orgs);
        return orgs;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORG = "INSERT INTO superOrganization(SuperOrganizationName, SuperOrganizationDescription, SuperOrganizationAddress, SuperOrganizationContact)" +
                 "VALUES (?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertSuperOrganization(org);
        return org;
        
    }
    
    private void insertSuperOrganization(Organization org){
        final String INSERT_SUPER_ORGANIZATION = "INSERT INTO "
                + "super_superOrganization(super_idsuper,superorganization_idsuperorganization)"
                + "VALUES (?,?)";
        if(org.getSupers() != null)
            for(Super sup : org.getSupers()){
                jdbc.update(INSERT_SUPER_ORGANIZATION,sup.getId(),org.getId());
        }
    }

    @Override
    @Transactional
    public void removeOrganizationById(int id) {
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM Super_SuperOrganization WHERE SuperOrganization_idSuperOrganization = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, id);
        
        final String DELETE_ORG = "DELETE FROM SuperOrganization WHERE idSuperOrganization = ?";
        jdbc.update(DELETE_ORG,id);
    }

    @Override
    public void updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE superOrganization SET SuperOrganizationName = ?, "
                + "SuperOrganizationDescription = ?, SuperOrganizationAddress = ?, SuperOrganizationContact = ? WHERE idSuperOrganization = ?";
        jdbc.update(UPDATE_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact(),
                org.getId());
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_superorganization WHERE superorganization_idsuperorganization = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, org.getId());
        insertSuperOrganization(org);
    }

    
    public static final class OrganizationMapper implements RowMapper<Organization>{
        
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException{
            Organization org = new Organization();
            org.setId(rs.getInt("idSuperOrganization"));
            org.setName(rs.getString("SuperOrganizationName"));
            org.setDescription(rs.getString("SuperOrganizationDescription"));
            org.setAddress(rs.getString("SuperOrganizationAddress"));
            org.setContact(rs.getString("SuperOrganizationContact"));
            
            return org;
        }
    }
    
}
