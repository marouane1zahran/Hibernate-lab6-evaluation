package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeTache {

    @EmbeddedId
    private EmployeTacheKey id = new EmployeTacheKey();

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeTache() {}

    public EmployeTache(Date dateDebutReelle, Employe employe, Tache tache, Date dateFinReelle) {
        this.dateDebutReelle = dateDebutReelle;
        this.employe = employe;
        this.tache = tache;
        this.dateFinReelle = dateFinReelle;
    }

    //  ici les getters et setters


    public EmployeTacheKey getId() {
        return id;
    }

    public void setId(EmployeTacheKey id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }
}