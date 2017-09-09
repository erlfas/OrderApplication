package no.fasmer.orderapplication.control;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.PartDao;
import no.fasmer.orderapplication.entity.Part;

@RequestScoped
public class PartProducer {
    
    @Inject
    private PartDao partDao;
    
    private List<Part> parts;
    
    @PostConstruct
    public void retrieveAllParts() {
        parts = partDao.findAll();
    }
    
    @Produces
    @Named
    public List<Part> getParts() {
        return parts;
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Part member) {
        retrieveAllParts();
    }
    
}
