package no.fasmer.orderapplication.producers;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.VendorPartDao;
import no.fasmer.orderapplication.entity.VendorPart;
import no.fasmer.orderapplication.vo.VendorPartVO;

@RequestScoped
public class VendorPartProducer {
    
    @Inject
    private VendorPartDao vendorPartDao;
    
    private List<VendorPart> vendorParts;
    
    @PostConstruct
    public void retrieveAllParts() {
        vendorParts = vendorPartDao.findAll();
    }
    
    @Produces
    @Named
    public List<VendorPartVO> getVendorParts() {
        return vendorParts.stream().map(x -> {
            final VendorPartVO vo = new VendorPartVO();
            vo.setDescription(x.getDescription());
            vo.setPartNumber(x.getPart().getPartNumber());
            vo.setPrice(x.getPrice());
            vo.setRevision(x.getPart().getRevision());
            vo.setVendorId(x.getVendor().getVendorId());
            
            return vo;
        }).collect(Collectors.toList());
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final VendorPartVO member) {
        retrieveAllParts();
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final VendorPart member) {
        retrieveAllParts();
    }
    
}