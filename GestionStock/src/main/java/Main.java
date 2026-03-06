

import ma.projet.classes.*;
import ma.projet.service.*;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cmds = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();



        Categorie cat = new Categorie();
        cat.setCode("INFO");
        cat.setLibelle("Matériel Informatique");
        cs.create(cat);

        Produit p1 = new Produit(); p1.setReference("ES12"); p1.setPrix(120); p1.setCategorie(cat);
        Produit p2 = new Produit(); p2.setReference("ZR85"); p2.setPrix(100); p2.setCategorie(cat);
        Produit p3 = new Produit(); p3.setReference("EE85"); p3.setPrix(200); p3.setCategorie(cat);
        ps.create(p1); ps.create(p2); ps.create(p3);

        Calendar calCmd = Calendar.getInstance();
        calCmd.set(2013, Calendar.MARCH, 14);

        Commande cmd = new Commande();
        cmd.setDate(calCmd.getTime());
        cmds.create(cmd);

        LigneCommandeProduit lcp1 = new LigneCommandeProduit(); lcp1.setCommande(cmd); lcp1.setProduit(p1); lcp1.setQuantite(7);
        LigneCommandeProduit lcp2 = new LigneCommandeProduit(); lcp2.setCommande(cmd); lcp2.setProduit(p2); lcp2.setQuantite(14);
        LigneCommandeProduit lcp3 = new LigneCommandeProduit(); lcp3.setCommande(cmd); lcp3.setProduit(p3); lcp3.setQuantite(5);
        lcs.create(lcp1); lcs.create(lcp2); lcs.create(lcp3);


        System.out.println("          EXÉCUTION DES MÉTHODES DEMANDÉES");
        System.out.println("=======================================================\n");

       System.out.println("1. Produits de la catégorie : " + cat.getLibelle());
        System.out.println("-------------------------------------------------------");
        for (Produit p : ps.findProduitsByCategorie(cat)) {
            System.out.println("   - " + p.getReference() + " (" + p.getPrix() + " DH)");
        }
        System.out.println();

        System.out.println(" 2. Produits commandés entre le 01/01/2013 et le 31/12/2013");
        System.out.println("-------------------------------------------------------");
        Calendar calDebut = Calendar.getInstance(); calDebut.set(2013, Calendar.JANUARY, 1);
        Calendar calFin = Calendar.getInstance(); calFin.set(2013, Calendar.DECEMBER, 31);

        for (Produit p : ps.findProduitsCommandesEntreDates(calDebut.getTime(), calFin.getTime())) {
            System.out.println("   - " + p.getReference());
        }
        System.out.println();

        System.out.println(" 3. Affichage détaillé de la commande");
        System.out.println("-------------------------------------------------------\n");

        Commande commandeAffichee = cmds.findById(cmd.getId());
        ps.afficherProduitsParCommande(commandeAffichee);
        System.out.println();


        System.out.println(" 4. Produits dont le prix est supérieur à 100 DH");
        System.out.println("-------------------------------------------------------");
        for (Produit p : ps.findProduitsPrixSuperieurA(100f)) {
            System.out.println("   - Référence : " + p.getReference() + " | Prix : " + p.getPrix() + " DH");
        }
        System.out.println("\n=======================================================");



        System.exit(0);
    }
}