package no.fasmer.orderapplication.dao;

import java.util.List;
import javax.ejb.Stateless;
import no.fasmer.orderapplication.entity.LineItem;

@Stateless
public class LineItemDao extends AbstractDao<LineItem> {
    
    public LineItemDao() {
        super(LineItem.class);
    }
    
    public List<LineItem> getLineItems() {
        return em.createNamedQuery(
                    "findAllLineItems")
                    .getResultList();
    }
    
    public int getNumLineItems() {
        return em.createNamedQuery(
                    "findAllLineItems")
                    .getResultList()
                    .size();
    }
    
    public List<LineItem> getLineItems(int orderId) {
        return em.createNamedQuery("findLineItemsByOrderId")
                    .setParameter("orderId", orderId)
                    .getResultList();
    }
    
}
