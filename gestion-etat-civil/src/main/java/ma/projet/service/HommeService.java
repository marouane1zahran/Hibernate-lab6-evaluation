package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HommeService extends AbstractFacade<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    public List<Femme> getEpousesEntreDates(Homme homme, Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT m.femme FROM Mariage m WHERE m.homme = :h AND m.dateDebut BETWEEN :d1 AND :d2";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setParameter("h", homme);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            return query.list();
        }
    }

    public void afficherMariagesDetails(Homme homme) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Mariage m WHERE m.homme = :h ORDER BY m.dateDebut ASC";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("h", homme);
            List<Mariage> mariages = query.list();

            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());

            System.out.println("\nMariages En Cours :");
            int indexEnCours = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.printf("%d. Femme : %s %s \t Date Début : %s \t Nbr Enfants : %d\n",
                            indexEnCours++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                            new SimpleDateFormat("dd/MM/yyyy").format(m.getDateDebut()), m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages échoués :");
            int indexEchoue = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.printf("%d. Femme : %s %s \t Date Début : %s \n   Date Fin : %s \t Nbr Enfants : %d\n",
                            indexEchoue++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                            new SimpleDateFormat("dd/MM/yyyy").format(m.getDateDebut()),
                            new SimpleDateFormat("dd/MM/yyyy").format(m.getDateFin()), m.getNbrEnfant());
                }
            }
        }
    }
}