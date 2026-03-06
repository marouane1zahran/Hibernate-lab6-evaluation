package ma.projet;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.services.EmployeService;
import ma.projet.services.EmployeTacheService;
import ma.projet.services.ProjetService;
import ma.projet.services.TacheService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        // Initialisation des services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // 1. Création d'un chef de projet
            Employe chef = new Employe();
            chef.setNom("Alaoui");
            chef.setPrenom("Mohammed");
            chef.setTelephone("0600000000");
            employeService.create(chef);

            // 2. Création du projet
            Projet projet = new Projet();
            projet.setNom("Gestion de stock");
            projet.setDateDebut(sdf.parse("14/01/2013"));
            projet.setChefDeProjet(chef);
            projetService.create(projet);

            // 3. Création des tâches
            Tache t1 = new Tache();
            t1.setNom("Analyse");
            t1.setPrix(1500);
            t1.setProjet(projet);
            tacheService.create(t1);

            Tache t2 = new Tache();
            t2.setNom("Conception");
            t2.setPrix(2000);
            t2.setProjet(projet);
            tacheService.create(t2);

            Tache t3 = new Tache();
            t3.setNom("Développement");
            t3.setPrix(800);
            t3.setProjet(projet);
            tacheService.create(t3);

            // 4. Affectation des tâches à un employé avec les dates réelles
            Employe developpeur = new Employe();
            developpeur.setNom("Benali");
            developpeur.setPrenom("Karim");
            employeService.create(developpeur);

            EmployeTache et1 = new EmployeTache();
            et1.setEmploye(developpeur);
            et1.setTache(t1);
            et1.setDateDebutReelle(sdf.parse("10/02/2013"));
            et1.setDateFinReelle(sdf.parse("20/02/2013"));
            employeTacheService.create(et1);

            EmployeTache et2 = new EmployeTache();
            et2.setEmploye(developpeur);
            et2.setTache(t2);
            et2.setDateDebutReelle(sdf.parse("10/03/2013"));
            et2.setDateFinReelle(sdf.parse("15/03/2013"));
            employeTacheService.create(et2);

            EmployeTache et3 = new EmployeTache();
            et3.setEmploye(developpeur);
            et3.setTache(t3);
            et3.setDateDebutReelle(sdf.parse("10/04/2013"));
            et3.setDateFinReelle(sdf.parse("25/04/2013"));
            employeTacheService.create(et3);

            System.out.println("\n--- TEST DE L'AFFICHAGE  ---\n");


            projetService.afficherTachesRealiseesAvecDates(projet);

            System.out.println("\n--- TEST DE LA REQUÊTE NOMMÉE (Prix > 1000 DH) ---\n");
            tacheService.afficherTachesPrixSuperieur(1000.0);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}