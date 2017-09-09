package no.fasmer.orderapplication.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.Transient;
import org.apache.commons.io.IOUtils;

@IdClass(PartKey.class)
@Entity
@Table(name = "PART")
@SecondaryTable(name = "PART_DETAIL", pkJoinColumns = {
    @PrimaryKeyJoinColumn(name = "PARTNUMBER", referencedColumnName = "PARTNUMBER"),
    @PrimaryKeyJoinColumn(name = "REVISION", referencedColumnName = "REVISION")
})
@NamedQueries(value = {
    @NamedQuery(name = "findAllParts", query = "SELECT p FROM Part p ORDER BY p.partNumber"),
    @NamedQuery(name = "findImage", query = "SELECT p.drawing FROM Part p WHERE p.partNumber = :pn AND p.revision = :r")
})
public class Part implements Serializable {
    
    private String partNumber;
    private int revision;
    private String description;
    private Date revisionDate;
    private byte[] drawing;
    private String specification;
    private Part bomPart;
    private List<Part> parts;
    private VendorPart vendorPart;
    
    private javax.servlet.http.Part filePart;

    public Part() {
        this.parts = new ArrayList<>();
    }

    public Part(
            String partNumber,
            int revision, 
            String description, 
            Date revisionDate, 
            byte[] drawing, 
            String specification) {
        
        this.partNumber = partNumber;
        this.revision = revision;
        this.description = description;
        this.revisionDate = revisionDate;
        this.drawing = drawing;
        this.specification = specification;
        this.parts = new ArrayList<>();
        
    }

    public Part(
            String partNumber,
            int revision, 
            String description, 
            Date revisionDate, 
            byte[] drawing, 
            String specification, 
            Part bomPart, 
            List<Part> parts, 
            VendorPart vendorPart) {
        
        this.partNumber = partNumber;
        this.revision = revision;
        this.description = description;
        this.revisionDate = revisionDate;
        this.drawing = drawing;
        this.specification = specification;
        this.bomPart = bomPart;
        this.parts = parts;
        this.vendorPart = vendorPart;
        
    }

    @Id
    @Column(nullable = false)
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Id
    @Column(nullable = false)
    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(DATE)
    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    @Column(table = "PART_DETAIL")
    @Lob
    public byte[] getDrawing() {
        return this.drawing;
    }

    public void setDrawing(byte[] drawing) {
        this.drawing = drawing;
    }

    @Column(table = "PART_DETAIL")
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BOMPARTNUMBER", referencedColumnName = "PARTNUMBER"),
        @JoinColumn(name = "BOMREVISION", referencedColumnName = "REVISION")
    })
    public Part getBomPart() {
        return bomPart;
    }

    public void setBomPart(Part bomPart) {
        this.bomPart = bomPart;
    }

    @OneToMany(mappedBy = "bomPart")
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @OneToOne(mappedBy = "part")
    public VendorPart getVendorPart() {
        return vendorPart;
    }

    public void setVendorPart(VendorPart vendorPart) {
        this.vendorPart = vendorPart;
    }
    
    @Transient
    public javax.servlet.http.Part getFilePart() {
        return filePart;
    }

    public void setFilePart(javax.servlet.http.Part filePart) {
        this.filePart = filePart;
        if (this.filePart != null) {
            try {
                final InputStream input = this.filePart.getInputStream();
                final byte[] drawingTemp = IOUtils.toByteArray(input);
                this.drawing = drawingTemp;
            } catch (IOException ex) {
                
            }
        }
    }
    
    @Transient
    public String getImageUrl() {
        return "/OrderApplication/images?partNumber=" + partNumber + "&revision=" + revision;
    }
    
}
