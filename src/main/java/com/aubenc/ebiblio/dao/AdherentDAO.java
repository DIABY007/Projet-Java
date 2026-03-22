package com.aubenc.ebiblio.dao;

import com.aubenc.ebiblio.model.Adherent;
import java.util.List;

public interface AdherentDAO {
    void ajouter(Adherent adherent);
    void modifier(Adherent adherent);
    void supprimer(String idUnique);
    Adherent trouverParIdUnique(String idUnique);
    Adherent trouverParId(int id);
    List<Adherent> listerTous();
}
