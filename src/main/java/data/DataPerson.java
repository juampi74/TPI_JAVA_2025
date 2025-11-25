package data;

import enums.PersonRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import entities.*;

public class DataPerson {

    private static final String SELECT_ROLE_BY_ID =
    	"SELECT role FROM person WHERE id = ?";
    
    private static final String SELECT_ALL_PEOPLE_JOINED =
    	"SELECT "
        + "    p.id AS person_id, "
        + "    p.fullname AS person_fullname, "
        + "    p.birthdate AS person_birthdate, "
        + "    p.address AS person_address, "
        + "    p.role AS person_role, "
        + "    p.id_nationality, "
        + "    n.id AS nationality_id, "
        + "    n.name AS nationality_name, "
        + "    n.iso_code AS nationality_iso_code "
        + "FROM person p "
        + "INNER JOIN nationality n ON p.id_nationality = n.id";

    public PersonRole getRoleByPersonId(int id) throws SQLException {

        PersonRole role = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ROLE_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                String roleStr = rs.getString("role");

                try {

                    role = PersonRole.valueOf(roleStr.toUpperCase());

                } catch (Exception e) {

                    throw new SQLException("Rol inválido en la BD: " + roleStr, e);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return role;

    }
    
    public LinkedList<Person> getByNationalityId(int id) throws SQLException {

        LinkedList<Person> people = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_PEOPLE_JOINED + " WHERE n.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    Person person = mapFullPerson(rs);
                    people.add(person);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return people;
        
    }
    
    private Person mapFullPerson(ResultSet rs) throws SQLException {

    	Person person = new Player();
    	person.setId(rs.getInt("person_id"));
    	person.setFullname(rs.getString("person_fullname"));
    	person.setBirthdate(rs.getObject("person_birthdate", LocalDate.class));
    	person.setAddress(rs.getString("person_address"));
    	String roleStr = rs.getString("person_role");
        try {

            person.setRole(PersonRole.valueOf(roleStr.toUpperCase()));

        } catch (Exception e) {

            throw new SQLException("Rol inválido en la BD: " + roleStr, e);

        }

        Nationality nationality = new Nationality();
        nationality.setId(rs.getInt("nationality_id"));
        nationality.setName(rs.getString("nationality_name"));
        nationality.setIsoCode(rs.getString("nationality_iso_code"));

        person.setNationality(nationality);

        return person;
        
    }

    private void closeResources(ResultSet rs, Statement stmt) {

        try {

            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DbConnector.getInstance().releaseConn();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}