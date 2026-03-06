package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(
        name = "Femme.marieesDeuxFoisOuPlus",
        query = "SELECT m.femme FROM Mariage m GROUP BY m.femme.id HAVING COUNT(m.id) >= 2"
)
@NamedNativeQuery(
        name = "Femme.countEnfantsNative",
        query = "SELECT COALESCE(SUM(nbrEnfant), 0) FROM Mariage WHERE femme_id = :idFemme AND dateDebut BETWEEN :d1 AND :d2"
)
public class Femme extends Personne {
    public Femme() {}
    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
}