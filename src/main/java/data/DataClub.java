package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import entities.*;

public class DataClub {

    public LinkedList<Club> getAll() {
        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {
            stmt = DbConnector.getInstancia().getConn().createStatement();
            rs = stmt.executeQuery("select id,name,fundation_date,email,badgeImage,phoneNumber,budget from club");
            if (rs != null) {
                while (rs.next()) {
                    Club c = new Club();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setfoundationDate(rs.getObject("fundation_date", LocalDate.class)); // Para que no de error si birthdate es null
                    c.setphoneNumber(rs.getString("phoneNumber"));
                    c.setEmail(rs.getString("email"));
                    c.setbadgeImage(rs.getString("badgeImage"));
                    c.setBudget(rs.getDouble("budget"));

                    clubs.add(c);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clubs;
    }

    public Club getById(Club club) {
        Club c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnector.getInstancia().getConn().prepareStatement(
                    "SELECT id, name, fundation_date, phoneNumber, email, badgeImage, budget FROM club WHERE id=?"
            );
            stmt.setInt(1, club.getId());
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                c = new Club();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setfoundationDate(rs.getObject("fundation_date", LocalDate.class));
                c.setphoneNumber(rs.getString("phoneNumber"));
                c.setEmail(rs.getString("email"));
                c.setbadgeImage(rs.getString("badgeImage"));
                c.setBudget(rs.getDouble("budget"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public LinkedList<Club> getByName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {
            stmt = DbConnector.getInstancia().getConn().prepareStatement(
                    "select id,fullname,birthdate,adress from person where fullname=?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Club c = new Club();

                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setfoundationDate(rs.getObject("fundation_date", LocalDate.class));
                    c.setphoneNumber(rs.getString("phoneNumber"));
                    c.setEmail(rs.getString("email"));
                    c.setbadgeImage(rs.getString("badgeImage"));
                    c.setBudget(rs.getDouble("budget"));

                    clubs.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clubs;

    }

    public void add(Club c) {
        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        try {
            stmt = DbConnector.getInstancia().getConn().prepareStatement(
                    "INSERT INTO club(name, fundation_date, phoneNumber, email, badgeImage, budget) VALUES(?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getfoundationDate());
            stmt.setString(3, c.getphoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getbadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                c.setId(keyResultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (keyResultSet != null) {
                    keyResultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Club c) {
        PreparedStatement stmt = null;
        try {
            stmt = DbConnector.getInstancia().getConn().prepareStatement(
                    "UPDATE club SET name=?, fundation_date=?, phoneNumber=?, email=?, badgeImage=?, budget=? WHERE id=?"
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getfoundationDate());
            stmt.setString(3, c.getphoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getbadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Club c) {
        PreparedStatement stmt = null;
        try {
            stmt = DbConnector.getInstancia().getConn().prepareStatement(
                    "DELETE FROM club WHERE id=?"
            );
            stmt.setInt(1, c.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
