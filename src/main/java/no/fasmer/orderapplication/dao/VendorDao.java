package no.fasmer.orderapplication.dao;

import java.util.List;
import javax.ejb.Stateless;
import no.fasmer.orderapplication.entity.Vendor;

@Stateless
public class VendorDao extends AbstractDao<Vendor> {
    
    public VendorDao() {
        super(Vendor.class);
    }
    
    public List<Vendor> getVendorsByPartialName(String name) {
        return em.createNamedQuery(
                    "findVendorsByPartialName")
                    .setParameter("name", name)
                    .getResultList();
    }
    
    public List<Vendor> getVendorsByOrder(Integer orderId) {
        return em.createNamedQuery(
                    "findVendorByOrder")
                    .setParameter("id", orderId)
                    .getResultList();
    }
    
}
