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
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;
import no.fasmer.orderapplication.vo.LineItemVO;

@Model
public class LineItemService {
    
    @Inject
    private FacesContext facesContext;
    
    @Inject
    private List<LineItemVO> lineItems; // cf. LineItemProducer
    
    @Produces
    @Named
    private LineItemVO newLineItem;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private Event<LineItemVO> newLineItemEvent;
    
    @PostConstruct
    public void initNewVendor() {
        newLineItem = new LineItemVO();
    }
    
    public void addNewLineItem() {
        try {
            orderBean.addLineItem(newLineItem.getOrderId(), newLineItem.getPartNumber(), newLineItem.getRevision(), newLineItem.getQuantity());
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Vendor created");
            facesContext.addMessage(null, m);
            initNewVendor();
            newLineItemEvent.fire(newLineItem);
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
}
