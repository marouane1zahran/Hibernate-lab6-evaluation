package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeService implements IDao<Employe> {


    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employe e = session.get(Employe.class, id);
        session.close();
        return e;
    }

    @Override
    public List<Employe> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employe> employes = session.createQuery("from Employe", Employe.class).list();
        session.close();
        return employes;
    }




    public void afficherTachesRealisees(Employe employe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // On passe par la table d'association EmployeTache
        String hql = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :empId";
        Query<Tache> query = session.createQuery(hql, Tache.class);
        query.setParameter("empId",employe.getId());

        List<Tache> taches = query.list();
        System.out.println("Tâches réalisées par " + employe.getNom() + " :");
        for (Tache t : taches) {
            System.out.println("- " + t.getNom());
        }
        session.close();
    }


    public void afficherProjetsGeres(Employe employe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Projet p WHERE p.chefDeProjet.id = :empId";
        Query<Projet> query = session.createQuery(hql, Projet.class);
        query.setParameter("empId", employe.getId());

        List<Projet> projets = query.list();
        System.out.println("Projets gérés par " + employe.getNom() + " :");
        for (Projet p : projets) {
            System.out.println("- " + p.getNom());
        }
        session.close();
    }
}