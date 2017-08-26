package no.fasmer.orderapplication.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "VENDOR_PART", 
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"PARTNUMBER", "PARTREVISION"}
    )
)
@NamedQueries({
    @NamedQuery(name = "findAverageVendorPartPrice", query = "SELECT AVG(vp.price) FROM VendorPart vp"),
    @NamedQuery(name = "findTotalVendorPartPricePerVendor", query = "SELECT SUM(vp.price) FROM VendorPart vp WHERE vp.vendor.vendorId = :id"),
    @NamedQuery(name = "findAllVendorParts", query = "SELECT vp FROM VendorPart vp ORDER BY vp.vendorPartNumber")
})
public class VendorPart implements Serializable {
    
    private Long vendorPartNumber;
    private String description;
    private double price;
    private Part part;
    private Vendor vendor;
    
    public VendorPart() {}
    
    public VendorPart(String description, double price, Part part) {
        this.description = description;
        this.price = price;
        this.part = part;
        part.setVendorPart(this);
    }

    @TableGenerator(
            name = "vendorPartGen", 
            table = "ORDER_SEQUENCE_GENERATOR", 
            pkColumnName = "GEN_KEY", 
            valueColumnName = "GEN_VALUE", 
            pkColumnValue = "VENDOR_PART_ID",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "vendorPartGen")
    public Long getVendorPartNumber() {
        return vendorPartNumber;
    }

    public void setVendorPartNumber(Long vendorPartNumber) {
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

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "PARTNUMBER", referencedColumnName = "PARTNUMBER"),
        @JoinColumn(name = "PARTREVISION", referencedColumnName = "REVISION")
    })
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @JoinColumn(name = "VENDORID")
    @ManyToOne
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    
    
    
}
