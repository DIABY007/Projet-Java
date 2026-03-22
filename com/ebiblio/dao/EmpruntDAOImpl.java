package com.ebiblio.dao;

import com.ebiblio.model.FicheEmprunt;
import com.ebiblio.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {
    @Override
    public void ajouter(FicheEmprunt emprunt) {
        String sql = "INSERT INTO Emprunts (adherent_id, livre_id, date_emprunt, date_limite, date_retour) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprunt.getAdherentId());
            stmt.setInt(2, emprunt.getLivreId());
            stmt.setDate(3, emprunt.getDateEmprunt());
            stmt.setDate(4, emprunt.getDateLimite());
            stmt.setDate(5, emprunt.getDateRetour());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(FicheEmprunt emprunt) {
        String sql = "UPDATE Emprunts SET adherent_id=?, livre_id=?, date_emprunt=?, date_limite=?, date_retour=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprunt.getAdherentId());
            stmt.setInt(2, emprunt.getLivreId());
            stmt.setDate(3, emprunt.getDateEmprunt());
            stmt.setDate(4, emprunt.getDateLimite());
            stmt.setDate(5, emprunt.getDateRetour());
            stmt.setInt(6, emprunt.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM Emprunts WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FicheEmprunt trouverParId(int id) {
        String sql = "SELECT * FROM Emprunts WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new FicheEmprunt(
                    rs.getInt("id"),
                    rs.getInt("adherent_id"),
                    rs.getInt("livre_id"),
                    rs.getDate("date_emprunt"),
                    rs.getDate("date_limite"),
                    rs.getDate("date_retour")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FicheEmprunt> listerTous() {
        List<FicheEmprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunts";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                emprunts.add(new FicheEmprunt(
                    rs.getInt("id"),
                    rs.getInt("adherent_id"),
                    rs.getInt("livre_id"),
                    rs.getDate("date_emprunt"),
                    rs.getDate("date_limite"),
                    rs.getDate("date_retour")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
}
