package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;

public class LigneCommandeService extends AbstractFacade<LigneCommandeProduit> {
    public LigneCommandeService() {
        super(LigneCommandeProduit.class);
    }
}
