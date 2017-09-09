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
import no.fasmer.orderapplication.mapper.PartMapper;
import no.fasmer.orderapplication.model.PartModel;
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;

@Model
public class PartService {
    
    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private PartModel newPart;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private List<Part> parts; // cf. PartProducer
    
    @Inject
    private Event<PartModel> newPartEvent;
    
    @PostConstruct
    public void initNewPart() {
        newPart = new PartModel();
    }
    
    public void addNewPart() {
        try {
            final Part part = PartMapper.map(newPart);
            orderBean.createPart(part);
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Vendor created");
            facesContext.addMessage(null, m);
            initNewPart();
            newPartEvent.fire(newPart);
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
}
