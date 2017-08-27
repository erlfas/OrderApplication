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
import no.fasmer.orderapplication.entity.Vendor;
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;

@Model
public class VendorService {
    
    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private Vendor newVendor;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private List<Vendor> vendors; // cf VendorProducer
    
    @Inject
    private Event<Vendor> newVendorEvent;
    
    @PostConstruct
    public void initNewVendor() {
        newVendor = new Vendor();
    }
    
    public void addNewVendor() {
        try {
            orderBean.createVendor(newVendor);
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Vendor created");
            facesContext.addMessage(null, m);
            initNewVendor();
            newVendorEvent.fire(newVendor);
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
}
