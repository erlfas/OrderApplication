package no.fasmer.orderapplication.mapper;

import java.util.Date;
import no.fasmer.orderapplication.entity.Part;
import no.fasmer.orderapplication.model.PartModel;

public class PartMapper {

    public static Part map(PartModel partModel) {
        final Part part = new Part();
        part.setPartNumber(partModel.getPartNumber());
        part.setRevision(partModel.getRevision());
        part.setDescription(partModel.getDescription());
        part.setDrawing(partModel.getDrawing());
        part.setRevisionDate(new Date());
        part.setSpecification(partModel.getSpecification());
        
        return part;
    }
    
}
