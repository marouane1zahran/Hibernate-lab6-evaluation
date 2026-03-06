package ma.projet.classes;


import javax.naming.Name;
import javax.persistence.*;

@Entity
@NamedQuery(name = "Produit.findByPrixSuperieur", query = "SELECT p FROM Produit p WHERE p.prix > :prix")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    @ManyToOne
    @JoinColumn(name ="categorie_id")
    private Categorie categorie ;

    public Produit() {
    }


    public Produit(Categorie categorie, float prix, String reference) {
        this.categorie = categorie;
        this.prix = prix;
        this.reference = reference;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
