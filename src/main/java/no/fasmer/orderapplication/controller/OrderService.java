package no.fasmer.orderapplication.controller;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.ejb.OrderBean;
import no.fasmer.orderapplication.entity.CustomerOrder;

@Model
public class OrderService {
    
    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private CustomerOrder newCustomerOrder;
    
    @Inject
    private OrderBean orderBean;
    
    @PostConstruct
    public void initNewCustomerOrder() {
        newCustomerOrder = new CustomerOrder();
    }
    
    public void addNewOrder() {
        try {
            newCustomerOrder.setStatus('a');
            newCustomerOrder.setLastUpdate(new Date());
            orderBean.createOrder(newCustomerOrder);
            
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Done!", "Order created");
            facesContext.addMessage(null, m);
            initNewCustomerOrder();
        } catch (Exception e) {
            final String errorMessage = getRootErrorMessage(e);
            final FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Error while saving data");
            facesContext.addMessage(null, m);
        }
    }
    
    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }
        
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        
        return errorMessage;
    }
    
}
