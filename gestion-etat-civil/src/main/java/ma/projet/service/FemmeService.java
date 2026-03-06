package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class FemmeService extends AbstractFacade<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    public int getNombreEnfantsEntreDates(Femme femme, Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Number> query = session.getNamedNativeQuery("Femme.countEnfantsNative");
            query.setParameter("idFemme", femme.getId());
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            Number result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        }
    }

    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Femme> query = session.createNamedQuery("Femme.marieesDeuxFoisOuPlus", Femme.class);
            return query.list();
        }
    }

    public Femme getFemmeLaPlusAgee() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Femme f ORDER BY f.dateNaissance ASC";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        }
    }
}