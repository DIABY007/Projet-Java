// Méthode déplacée à l'intérieur de la classe
package com.aubenc.ebiblio.dao;

import com.aubenc.ebiblio.model.Adherent;
import com.aubenc.ebiblio.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDAOImpl implements AdherentDAO {
// ...existing code...
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
                        rs.getString("idUnique"),
                        rs.getString("adresse"),
                        com.aubenc.ebiblio.model.TypeAdherent.valueOf(rs.getString("type"))
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    @Override
    public void ajouter(Adherent adherent) {
        String sql = "INSERT INTO Adherents (nom, prenom, adresse, type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getAdresse());
            stmt.setString(4, adherent.getType().name());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                adherent.setId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Adherent adherent) {
        String sql = "UPDATE Adherents SET nom=?, prenom=?, adresse=?, type=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setString(3, adherent.getAdresse());
            stmt.setString(4, adherent.getType().name());
            stmt.setInt(5, adherent.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(String idUnique) {
        // Suppression par clé primaire id
        String sql = "DELETE FROM Adherents WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(idUnique));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Adherent trouverParIdUnique(String idUnique) {
        String sql = "SELECT * FROM Adherents WHERE idUnique=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idUnique);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Adherent(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("idUnique"),
                    rs.getString("adresse"),
                    com.aubenc.ebiblio.model.TypeAdherent.valueOf(rs.getString("type"))
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
                    rs.getString("idUnique"),
                    rs.getString("adresse"),
                    com.aubenc.ebiblio.model.TypeAdherent.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adherents;
    }
}
