package no.fasmer.orderapplication.producers;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.CustomerOrderDao;
import no.fasmer.orderapplication.entity.CustomerOrder;

@RequestScoped
public class OrderProducer {
    
    @Inject
    private CustomerOrderDao customerOrderDao;
    
    private List<CustomerOrder> orders;
    
    @PostConstruct
    public void retrieveAllOrders() {
        orders = customerOrderDao.findAll();
    }
    
    @Produces
    @Named
    public List<CustomerOrder> getOrders() {
        return orders;
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final CustomerOrder member) {
        retrieveAllOrders();
    }
    
}
