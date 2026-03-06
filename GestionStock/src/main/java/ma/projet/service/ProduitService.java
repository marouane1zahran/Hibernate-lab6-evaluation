package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractFacade<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    public List<Produit> findProduitsByCategorie(Categorie categorie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("FROM Produit p WHERE p.categorie = :cat", Produit.class)
                .setParameter("cat", categorie)
                .list();
        session.close();
        return produits;
    }


    public List<Produit> findProduitsCommandesEntreDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery(
                        "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp WHERE lcp.commande.date BETWEEN :d1 AND :d2", Produit.class)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .list();
        session.close();
        return produits;
    }

    public void afficherProduitsParCommande(Commande commande) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        System.out.println("Commande : " + commande.getId() + "     Date : " + sdf.format(commande.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\t\tQuantité");

        for (LigneCommandeProduit lcp : commande.getLignesCommande()) {
            System.out.printf("%s\t\t%.0f DH\t\t%d\n",
                    lcp.getProduit().getReference(),
                    lcp.getProduit().getPrix(),
                    lcp.getQuantite());
        }
    }
    public List<Produit> findProduitsPrixSuperieurA(float prix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createNamedQuery("Produit.findByPrixSuperieur", Produit.class)
                .setParameter("prix", prix)
                .list();
        session.close();
        return produits;
    }
}