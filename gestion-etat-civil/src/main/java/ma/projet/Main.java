package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println("--- Création des données en base ---");

            // 1. Créer 10 femmes
            Femme[] femmes = new Femme[10];
            femmes[0] = new Femme("RAMI", "SALIMA", "0600000001", "Adresse 1", sdf.parse("01/01/1970"));
            femmes[1] = new Femme("ALI", "AMAL", "0600000002", "Adresse 2", sdf.parse("05/06/1975"));
            femmes[2] = new Femme("ALAOUI", "WAFA", "0600000003", "Adresse 3", sdf.parse("12/10/1980"));
            femmes[3] = new Femme("ALAMI", "KARIMA", "0600000004", "Adresse 4", sdf.parse("22/03/1968"));
            femmes[4] = new Femme("BENNIS", "SANA", "0600000005", "Adresse 5", sdf.parse("15/08/1982"));
            femmes[5] = new Femme("CHRAIBI", "HIND", "0600000006", "Adresse 6", sdf.parse("30/11/1990"));
            femmes[6] = new Femme("TAZI", "MERYEM", "0600000007", "Adresse 7", sdf.parse("14/02/1985"));
            femmes[7] = new Femme("IRAQUI", "ZINEB", "0600000008", "Adresse 8", sdf.parse("19/07/1978"));
            femmes[8] = new Femme("KABBAJ", "LOUBNA", "0600000009", "Adresse 9", sdf.parse("03/09/1992"));
            femmes[9] = new Femme("SLAOUI", "NADIA", "0600000010", "Adresse 10", sdf.parse("25/12/1988"));

            for (Femme f : femmes) {
                fs.create(f);
            }

            // 2. Créer 5 hommes
            Homme[] hommes = new Homme[5];
            hommes[0] = new Homme("SAFI", "SAID", "0611111111", "Adresse H1", sdf.parse("10/05/1965")); // L'homme de l'exemple
            hommes[1] = new Homme("NACIRI", "KAMAL", "0611111112", "Adresse H2", sdf.parse("20/08/1970"));
            hommes[2] = new Homme("FASSI", "HICHAM", "0611111113", "Adresse H3", sdf.parse("11/11/1975"));
            hommes[3] = new Homme("JALAL", "MOURAD", "0611111114", "Adresse H4", sdf.parse("05/04/1980"));
            hommes[4] = new Homme("KHALIL", "YOUSSEF", "0611111115", "Adresse H5", sdf.parse("09/09/1985"));

            for (Homme h : hommes) {
                hs.create(h);
            }

            // Création des mariages pour SAFI SAID (hommes[0]) pour reproduire l'affichage attendu
            ms.create(new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, hommes[0], femmes[3])); // Echoué, KARIMA
            ms.create(new Mariage(sdf.parse("03/09/1990"), null, 4, hommes[0], femmes[0])); // En cours, SALIMA
            ms.create(new Mariage(sdf.parse("03/09/1995"), null, 2, hommes[0], femmes[1])); // En cours, AMAL
            ms.create(new Mariage(sdf.parse("04/11/2000"), null, 3, hommes[0], femmes[2])); // En cours, WAFA

            // Création de mariages supplémentaires pour tester les autres méthodes
            // Femme mariée au moins deux fois (AMAL ALI, femmes[1])
            ms.create(new Mariage(sdf.parse("01/01/2010"), null, 1, hommes[1], femmes[1]));

            System.out.println("Données insérées avec succès.\n");
            System.out.println("--------------------------------------------------");

            // 3. Afficher la liste des femmes
            System.out.println("=> Liste de toutes les femmes :");
            for (Femme f : fs.findAll()) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }

            // 4. Afficher la femme la plus âgée
            System.out.println("\n=> La femme la plus âgée :");
            Femme plusAgee = fs.getFemmeLaPlusAgee();
            if (plusAgee != null) {
                System.out.println(plusAgee.getNom() + " " + plusAgee.getPrenom() + " (Née le " + sdf.format(plusAgee.getDateNaissance()) + ")");
            }

            // 5. Afficher les épouses d'un homme donné (ex: SAFI SAID)
            System.out.println("\n=> Épouses de SAFI SAID entre 1980 et 2020 :");
            Date d1 = sdf.parse("01/01/1980");
            Date d2 = sdf.parse("31/12/2020");
            List<Femme> epouses = hs.getEpousesEntreDates(hommes[0], d1, d2);
            for (Femme f : epouses) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }

            // 6. Afficher le nombre d'enfants d'une femme entre deux dates (ex: SALIMA RAMI)
            System.out.println("\n=> Nombre d'enfants de SALIMA RAMI entre 1980 et 2020 :");
            int nbrEnfants = fs.getNombreEnfantsEntreDates(femmes[0], d1, d2);
            System.out.println(nbrEnfants + " enfant(s).");

            // 7. Afficher les femmes mariées deux fois ou plus
            System.out.println("\n=> Femmes mariées deux fois ou plus :");
            List<Femme> multiMariees = fs.getFemmesMarieesAuMoinsDeuxFois();
            for (Femme f : multiMariees) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }

            // 8. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n=> Nombre d'hommes mariés à 4 femmes entre 1980 et 2020 :");
            int countHommes4 = ms.countHommesMariesQuatreFoisEntre(d1, d2);
            System.out.println(countHommes4 + " homme(s).");

            System.out.println("\n--------------------------------------------------");
            // 9. Afficher les mariages d'un homme avec tous les détails
            System.out.println("=> Détails des mariages de l'homme :\n");
            hs.afficherMariagesDetails(hommes[0]);

        } catch (ParseException e) {
            System.err.println("Erreur de parsing des dates : " + e.getMessage());
        } finally {

            ma.projet.util.HibernateUtil.getSessionFactory().close();
        }
    }
}