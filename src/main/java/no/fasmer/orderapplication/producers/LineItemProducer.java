package no.fasmer.orderapplication.producers;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import no.fasmer.orderapplication.dao.LineItemDao;
import no.fasmer.orderapplication.entity.LineItem;
import no.fasmer.orderapplication.vo.LineItemVO;

@RequestScoped
public class LineItemProducer {
    
    @Inject
    private LineItemDao lineItemDao;
    
    private List<LineItem> lineItems;
    
    @PostConstruct
    public void retrieveAllLineItems() {
        lineItems = lineItemDao.findAll();
    }
    
    @Produces
    @Named
    public List<LineItemVO> getLineItems() {
        return lineItems.stream().map(x -> {
            final LineItemVO vo = new LineItemVO();
            vo.setOrderId(x.getCustomerOrder().getOrderId());
            vo.setPartNumber(x.getVendorPart().getPart().getPartNumber());
            vo.setQuantity(x.getQuantity());
            vo.setRevision(x.getVendorPart().getPart().getRevision());
            
            return vo;
        }).collect(Collectors.toList());
    }
    
    public void onListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final LineItemVO member) {
        retrieveAllLineItems();
    }
    
}
