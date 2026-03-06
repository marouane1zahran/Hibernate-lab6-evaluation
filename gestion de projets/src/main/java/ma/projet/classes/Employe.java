package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;

    // Un employé peut diriger plusieurs projets
    @OneToMany(mappedBy = "chefDeProjet")
    private List<Projet> projetsDiriges;

    // Relation avec la table intermédiaire
    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches;

    public Employe() {}

    public Employe(String nom, String prenom, String telephone, List<Projet> projetsDiriges, List<EmployeTache> employeTaches) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.projetsDiriges = projetsDiriges;
        this.employeTaches = employeTaches;
    }

    //  ici les getters et setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Projet> getProjetsDiriges() {
        return projetsDiriges;
    }

    public void setProjetsDiriges(List<Projet> projetsDiriges) {
        this.projetsDiriges = projetsDiriges;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }
}