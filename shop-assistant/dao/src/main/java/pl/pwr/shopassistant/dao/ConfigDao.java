package pl.pwr.shopassistant.dao;


import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.Config;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConfigDao extends AbstractDao<Config, Integer> {

    public ConfigDao() {
        super(Config.class);
    }

    public Config getConfigByName(String name) {
        TypedQuery<Config> query = entityManager.createQuery("SELECT c FROM Config AS c WHERE c.name = :name", Config.class);
        query.setParameter("name", name);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}