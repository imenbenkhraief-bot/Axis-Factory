package database;

import business.models.Worker;
import business.models.workers.Operator;
import business.models.workers.Supervisor;
import business.models.workers.Technician;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAO {
    private Connection conn;

    public WorkerDAO() {
        this.conn = DBConnection.getConnection();
    }

    public void save(Worker worker) {
        String sql = "INSERT INTO workers (id, name, role, extra_info) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, role=?, extra_info=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String extra = "N/A"; 
            if (worker instanceof Operator) {
                extra = ((Operator) worker).getAssignedLine();
            } else if (worker instanceof Supervisor) {
                extra = ((Supervisor) worker).getDepartment();
            } else if (worker instanceof Technician) {
                extra = "Maintenance";
            }

            ps.setString(1, worker.getId());
            ps.setString(2, worker.getName());
            ps.setString(3, worker.getRole());
            ps.setString(4, extra);
            ps.setString(5, worker.getName());
            ps.setString(6, worker.getRole());
            ps.setString(7, extra);
            ps.executeUpdate(); 
            System.out.println("Axis DB: Successfully synced " + worker.getName());

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }

    public List<String[]> findAll() {
        List<String[]> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM workers";
        java.sql.Connection conn = DBConnection.getConnection();
        
        if (conn == null) {
            System.err.println("Cannot load workers: Database is offline.");
            return list; 
        }
        
        try (java.sql.Statement st = conn.createStatement(); 
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getString("extra_info")
                });
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
