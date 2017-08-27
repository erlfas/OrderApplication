package no.fasmer.orderapplication.control;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.VendorDao;
import no.fasmer.orderapplication.entity.Vendor;

@RequestScoped
public class VendorProducer {
    
    @Inject
    private VendorDao vendorDao;
    
    private List<Vendor> vendors;
    
    @PostConstruct
    public void retrieveAllVendors() {
        vendors = vendorDao.findAll();
    }
    
    @Produces
    @Named
    public List<Vendor> getVendors() {
        return vendors;
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Vendor member) {
        retrieveAllVendors();
    }
    
}
