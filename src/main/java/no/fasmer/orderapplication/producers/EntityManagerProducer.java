package no.fasmer.orderapplication.producers;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// https://stackoverflow.com/questions/27506727/inject-persistencecontext-with-cdi
public class EntityManagerProducer {
    
    @Produces
    @PersistenceContext
    private EntityManager em;
    
}
