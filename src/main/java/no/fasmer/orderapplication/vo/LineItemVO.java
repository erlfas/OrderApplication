package no.fasmer.orderapplication.vo;

import java.io.Serializable;

public class LineItemVO implements Serializable {
    
    private int quantity;
    
    // id for Part
    private String partNumber;
    private int revision;
    
    // id for CustomerOrder
    private int orderId;

    public LineItemVO() {
    }

    public LineItemVO(int quantity, String partNumber, int revision, int orderId) {
        this.quantity = quantity;
        this.partNumber = partNumber;
        this.revision = revision;
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
