package com.ebiblio.dao;

import com.ebiblio.model.Adherent;
import com.ebiblio.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDAOImpl implements AdherentDAO {
    @Override
    public void ajouter(Adherent adherent) {
        String sql = "INSERT INTO Adherents (nom, prenom, type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getType().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Adherent adherent) {
        String sql = "UPDATE Adherents SET nom=?, prenom=?, type=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getType().name());
            stmt.setInt(4, adherent.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM Adherents WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Adherent trouverParId(int id) {
        String sql = "SELECT * FROM Adherents WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Adherent(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    Adherent.Type.valueOf(rs.getString("type"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adherent> listerTous() {
        List<Adherent> adherents = new ArrayList<>();
        String sql = "SELECT * FROM Adherents";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                adherents.add(new Adherent(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    Adherent.Type.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }
}
