package no.fasmer.orderapplication.controller;

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
import no.fasmer.orderapplication.ejb.OrderBean;
import no.fasmer.orderapplication.entity.CustomerOrder;
import static no.fasmer.orderapplication.utils.ExceptionUtils.getRootErrorMessage;

@Model
public class OrderService {
    
    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private CustomerOrder newCustomerOrder;
    
    @Inject
    private OrderBean orderBean;
    
    @Inject
    private List<CustomerOrder> orders; // cf. OrderProducer
    
    @Inject
    private Event<CustomerOrder> newOrderEvent;
    
    @PostConstruct
    public void initNewCustomerOrder() {
        newCustomerOrder = new CustomerOrder();
    }
    
    public void addNewOrder() {
        try {
            newCustomerOrder.setLastUpdate(new Date());
            orderBean.createOrder(newCustomerOrder);
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Order created");
            facesContext.addMessage(null, m);
            initNewCustomerOrder();
            newOrderEvent.fire(newCustomerOrder);
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
}
