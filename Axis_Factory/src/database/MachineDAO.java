package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MachineDAO {

    public void save(business.models.Machine machine) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;

        String sql = "INSERT INTO machines (id, name, type, parts, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, machine.getId());
            stmt.setString(2, machine.getName());
            stmt.setString(3, machine.getClass().getSimpleName()); 
            stmt.setInt(4, 0);
            stmt.setString(5, "READY");
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("-> DB Save Failed: " + e.getMessage());
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }

    public List<String[]> findAll() {
        List<String[]> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) return list;         
        String sql = "SELECT * FROM machines";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("status"),
                    rs.getString("parts")
                });
            }
        } catch (SQLException e) {
            System.err.println("-> DB Fetch Failed: " + e.getMessage());
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
        return list;
    }
    
    public void update(business.models.Machine machine) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) return;
        
        String sql = "UPDATE machines SET parts = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, machine.getTotalPartsProduced());
            stmt.setString(2, "READY");
            stmt.setString(3, machine.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("-> DB Update Failed: " + e.getMessage());
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
    }
}