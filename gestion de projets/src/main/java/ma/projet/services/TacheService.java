package ma.projet.services;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tache t = session.get(Tache.class, id);
        session.close();
        return t;
    }

    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Tache> taches = session.createQuery("from Tache", Tache.class).list();
        session.close();
        return taches;
    }


    public void afficherTachesPrixSuperieur(double prixMinimum) {
        Session session = HibernateUtil.getSessionFactory().openSession();


        Query<Tache> query = session.createNamedQuery("Tache.prixSuperieurA", Tache.class);
        query.setParameter("prix", prixMinimum);

        List<Tache> taches = query.list();
        System.out.println("Tâches dont le prix est supérieur à " + prixMinimum + " DH :");
        for (Tache t : taches) {
            System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)");
        }
        session.close();
    }


    public void afficherTachesRealiseesEntre(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql = "SELECT et.tache FROM EmployeTache et WHERE et.dateDebutReelle >= :debut AND et.dateFinReelle <= :fin";
        Query<Tache> query = session.createQuery(hql, Tache.class);
        query.setParameter("debut", dateDebut);
        query.setParameter("fin", dateFin);

        List<Tache> taches = query.list();
        System.out.println("Tâches réalisées entre le " + dateDebut + " et le " + dateFin + " :");
        for (Tache t : taches) {
            System.out.println("- " + t.getNom());
        }
        session.close();
    }
}