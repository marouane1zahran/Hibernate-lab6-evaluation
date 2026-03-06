package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class MariageService extends AbstractFacade<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }

    // Utilisation de l'API Criteria
    public int countHommesMariesQuatreFoisEntre(Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> root = cq.from(Mariage.class);

            // Filtre sur les dates
            Predicate datePredicate = cb.between(root.get("dateDebut"), d1, d2);

            // Group by homme, count(femme) == 4
            cq.select(cb.countDistinct(root.get("homme")))
                    .where(datePredicate)
                    .groupBy(root.get("homme"))
                    .having(cb.equal(cb.count(root.get("femme")), 4L));

            List<Long> result = session.createQuery(cq).getResultList();
            return result.size();
        }
    }
}