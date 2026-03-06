package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjetService implements IDao<Projet> {
    @Override
    public boolean create(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Projet findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Projet p = session.get(Projet.class, id);
        session.close();
        return p;
    }

    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Projet> projets = session.createQuery("from Projet", Projet.class).list();
        session.close();
        return projets;
    }
    public void afficherTachesPlanifiees(Projet projet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Tache t WHERE t.projet.id = :projetId";
        Query<Tache> query = session.createQuery(hql, Tache.class);
        query.setParameter("projetId", projet.getId());

        List<Tache> taches = query.list();
        System.out.println("Tâches planifiées pour le projet " + projet.getNom() + " :");
        for (Tache t : taches) {
            System.out.println("- " + t.getNom());
        }
        session.close();
    }


    public void afficherTachesRealiseesAvecDates(Projet projet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Projet : " + projet.getId() + "\tNom : " + projet.getNom() + "\tDate début : " + sdf.format(projet.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.printf("%-5s %-15s %-20s %-20s\n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");


        String hql = "FROM EmployeTache et WHERE et.tache.projet.id = :projetId";
        Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
        query.setParameter("projetId", projet.getId());

        List<EmployeTache> employeTaches = query.list();

        for (EmployeTache et : employeTaches) {
            String dateDebut = et.getDateDebutReelle() != null ? sdf.format(et.getDateDebutReelle()) : "N/A";
            String dateFin = et.getDateFinReelle() != null ? sdf.format(et.getDateFinReelle()) : "N/A";

            System.out.printf("%-5d %-15s %-20s %-20s\n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    dateDebut,
                    dateFin);
        }
        session.close();
    }
}
