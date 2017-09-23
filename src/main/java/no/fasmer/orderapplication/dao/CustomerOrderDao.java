package no.fasmer.orderapplication.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import no.fasmer.orderapplication.entity.CustomerOrder;
import no.fasmer.orderapplication.entity.LineItem;
import no.fasmer.orderapplication.entity.LineItem_;

@Stateless
public class CustomerOrderDao extends AbstractDao<CustomerOrder> {
    
    @Inject
    private Logger logger;
    
    public CustomerOrderDao() {
        super(CustomerOrder.class);
    }
    
    public List<CustomerOrder> getOrders() {
        return em.createNamedQuery("findAllOrders").getResultList();
    }
    
    public List<CustomerOrder> getOrdersWithCriteriaBuilder() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<CustomerOrder> criteriaQuery = cb.createQuery(CustomerOrder.class); // create a query object
        final Root<CustomerOrder> customerOrder = criteriaQuery.from(CustomerOrder.class); // set the FROM clause of the query and specify the root of the query
        criteriaQuery.select(customerOrder); // set the SELECT clause of the query
        final TypedQuery<CustomerOrder> typedQuery = em.createQuery(criteriaQuery);
        
        return typedQuery.getResultList();
    }
    
    public CustomerOrder getCustomerOrder(int orderId) {
        return em.find(CustomerOrder.class, orderId);
    }
    
    public void removeCustomerOrder(int orderId) {
        try {
            final CustomerOrder customerOrder = em.find(CustomerOrder.class, orderId);
            em.remove(customerOrder);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to remove order with id {0}", orderId);
        }
    }
    
    /**
     * SELECT   *
     * FROM     customerorder co
     * WHERE    exists (
     *              SELECT  *
     *              FROM    lineitem li
     *              WHERE   li.orderid = co.orderid
     *          )
     * 
     * @return 
     */
    public List<CustomerOrder> getCustomerOrdersWithLineItems() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        
        final CriteriaQuery<CustomerOrder> query = cb.createQuery(CustomerOrder.class);
        final Root<CustomerOrder> customerOrder = query.from(CustomerOrder.class);
        query.select(customerOrder);
        
        final Subquery<LineItem> subquery = query.subquery(LineItem.class);
        final Root<LineItem> lineItem = subquery.from(LineItem.class);
        subquery.select(lineItem);
        subquery.where(cb.equal(lineItem.get(LineItem_.customerOrder), customerOrder));
        
        query.where(cb.exists(subquery));
        
        final TypedQuery<CustomerOrder> typedQuery = em.createQuery(query);
        
        return typedQuery.getResultList();
    }
    
}