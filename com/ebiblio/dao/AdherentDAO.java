package com.ebiblio.dao;

import com.ebiblio.model.Adherent;
import java.util.List;

public interface AdherentDAO {
    void ajouter(Adherent adherent);
    void modifier(Adherent adherent);
    void supprimer(int id);
    Adherent trouverParId(int id);
    List<Adherent> listerTous();
}
