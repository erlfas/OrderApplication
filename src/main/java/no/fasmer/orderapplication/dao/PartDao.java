package no.fasmer.orderapplication.dao;

import java.util.List;
import javax.ejb.Stateless;
import no.fasmer.orderapplication.entity.Part;

@Stateless
public class PartDao extends AbstractDao<Part> {
    
    public PartDao() {
        super(Part.class);
    }
    
    public List<Part> getAllParts() {
        return em.createNamedQuery("findAllParts").getResultList();
    }
    
    public byte[] getImage(String revisionNumber, int revision) {
        return (byte[]) em.createNamedQuery("findImage")
                .setParameter("pn", revisionNumber)
                .setParameter("r", revision)
                .getSingleResult();
    }
    
}
