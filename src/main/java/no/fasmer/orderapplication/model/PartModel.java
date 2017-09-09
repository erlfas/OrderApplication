package no.fasmer.orderapplication.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

public class PartModel implements Serializable {

    private String partNumber;
    private Integer revision;
    private String description;
    private String specification;
    private javax.servlet.http.Part filePart;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
    
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
    }
    
    public byte[] getDrawing() {
        if (filePart != null) {
            try {
                final InputStream input = filePart.getInputStream();
                final byte[] drawing = IOUtils.toByteArray(input);
                return drawing;
            } catch (IOException ex) {
                
            }
        }
        
        return null;
    }
    
}
