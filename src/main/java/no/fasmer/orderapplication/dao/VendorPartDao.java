package no.fasmer.orderapplication.dao;

import javax.ejb.Stateless;
import no.fasmer.orderapplication.entity.VendorPart;

@Stateless
public class VendorPartDao extends AbstractDao<VendorPart> {
    
    public VendorPartDao() {
        super(VendorPart.class);
    }
    
    public Double getAvgVendorPartPrice() {
        return (Double) em.createNamedQuery(
                    "findAverageVendorPartPrice")
                    .getSingleResult();
    }
    
    public Double getTotalPricePerVendor(int vendorId) {
        return (Double) em
                .createNamedQuery("findTotalVendorPartPricePerVendor")
                .setParameter("id", vendorId)
                .getSingleResult();
    }
    
}
