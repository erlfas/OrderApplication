package no.fasmer.orderapplication.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.PartDao;
import no.fasmer.orderapplication.ejb.OrderBean;
import no.fasmer.orderapplication.entity.Part;
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;

@Model
public class PartService {
    
    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private Part newPart;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private PartDao partDao;
    
    @Inject
    private List<Part> parts; // cf. PartProducer
    
    @Inject
    private Event<Part> newPartEvent;
    
    @PostConstruct
    public void initNewPart() {
        newPart = new Part();
    }
    
    public void addNewPart() {
        try {
            newPart.setRevisionDate(new Date());
            orderBean.createPart(newPart);
            
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
    
    public InputStream getImage(String revisionNumber, int revision) {
        final byte[] image = partDao.getImage(revisionNumber, revision);
        return new ByteArrayInputStream(image);
    }
    
}
