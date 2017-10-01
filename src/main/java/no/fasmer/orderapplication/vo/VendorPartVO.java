package no.fasmer.orderapplication.vo;

import java.io.Serializable;

public class VendorPartVO implements Serializable {

    private long vendorPartNumber;
    private String description;
    private double price;
    private String partNumber;
    private int revision;
    private int vendorId;

    public VendorPartVO() {
    }

    public VendorPartVO(long vendorPartNumber, String description, double price, String partNumber, int revision, int vendorId) {
        this.vendorPartNumber = vendorPartNumber;
        this.description = description;
        this.price = price;
        this.partNumber = partNumber;
        this.revision = revision;
        this.vendorId = vendorId;
    }

    public long getVendorPartNumber() {
        return vendorPartNumber;
    }

    public void setVendorPartNumber(long vendorPartNumber) {
        this.vendorPartNumber = vendorPartNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }
    
    

}
