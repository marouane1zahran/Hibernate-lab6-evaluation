package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // Relation avec le chef de projet
    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefDeProjet;

    // Relation avec les tâches
    @OneToMany(mappedBy = "projet")
    private List<Tache> taches;

    public Projet() {}

    public Projet(Employe chefDeProjet, List<Tache> taches, String nom, Date dateDebut, Date dateFin) {
        this.chefDeProjet = chefDeProjet;
        this.taches = taches;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    //  ici les getters et setter


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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Employe getChefDeProjet() {
        return chefDeProjet;
    }

    public void setChefDeProjet(Employe chefDeProjet) {
        this.chefDeProjet = chefDeProjet;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

}