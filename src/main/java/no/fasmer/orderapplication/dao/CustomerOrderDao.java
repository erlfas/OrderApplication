package no.fasmer.orderapplication.dao;

import java.util.List;
import javax.ejb.Stateless;
import no.fasmer.orderapplication.entity.CustomerOrder;

@Stateless
public class CustomerOrderDao extends AbstractDao<CustomerOrder> {
    
    public CustomerOrderDao() {
        super(CustomerOrder.class);
    }
    
    public List<CustomerOrder> getOrders() {
        return em.createNamedQuery("findAllOrders").getResultList();
    }
    
}
