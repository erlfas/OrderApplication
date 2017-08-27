package no.fasmer.orderapplication.ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import no.fasmer.orderapplication.entity.CustomerOrder;
import no.fasmer.orderapplication.entity.LineItem;
import no.fasmer.orderapplication.entity.Part;

@Remote
public interface OrderRemote {
    
    void createPart(int revision,
            String description,
            Date revisionDate,
            String specification,
            Serializable drawing);
    
    List<Part> getAllParts();
    
    void addPartToBillOfMaterial(String bomPartNumber,
            int bomRevision,
            String partNumber,
            int revision);
    
    void createVendor(String name,
            String address,
            String contact,
            String phone);
    
    void createVendorPart(String partNumber,
            int revision,
            String description,
            double price,
            int vendorId);
    
    void createOrder(char status, int discount, String shipmentInfo);
    
    void createOrder(CustomerOrder customerOrder);
    
    List<CustomerOrder> getOrders();
    
    void addLineItem(Integer orderId, String partNumber, int revision, int quantity);
    
    double getBillOfMaterialPrice(String bomPartNumber, int bomRevision, String partNumber, int revision);
    
    double getOrderPrice(Integer orderId);
    
    void adjustOrderDiscount(int adjustment);
    
    Double getAvgPrice();
    
    Double getTotalPricePerVendor(int vendorId);
    
    List<String> locateVendorsByPartialName(String name);
    
    int countAllItems();
    
    List<LineItem> getLineItems(int orderId);
    
    void removeOrder(Integer orderId);
    
    String reportVendorsByOrder(Integer orderId);
    
}
