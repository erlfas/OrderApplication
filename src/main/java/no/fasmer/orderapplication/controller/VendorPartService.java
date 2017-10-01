package no.fasmer.orderapplication.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.ejb.OrderBean;
import no.fasmer.orderapplication.entity.Part;
import no.fasmer.orderapplication.entity.Vendor;
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;
import no.fasmer.orderapplication.vo.VendorPartVO;

@Model
public class VendorPartService {
    
    @Inject
    private FacesContext facesContext;
    
    @Inject
    private List<Vendor> vendors; // cf VendorProducer
    
    @Inject
    private List<Part> parts; // cf. PartProducer
    
    @Inject
    private List<VendorPartVO> vendorParts; // cf. VendorPartProducer
    
    @Produces
    @Named
    private VendorPartVO newVendorPart;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private Event<VendorPartVO> newVendorPartEvent;
    
    @PostConstruct
    public void initNewVendor() {
        newVendorPart = new VendorPartVO();
    }
    
    public void addNewVendorPart() {
        try {
            orderBean.createVendorPart(newVendorPart);
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Vendor created");
            facesContext.addMessage(null, m);
            initNewVendor();
            newVendorPartEvent.fire(newVendorPart);
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
}
