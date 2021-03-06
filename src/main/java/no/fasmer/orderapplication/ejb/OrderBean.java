package no.fasmer.orderapplication.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import no.fasmer.orderapplication.dao.CustomerOrderDao;
import no.fasmer.orderapplication.dao.LineItemDao;
import no.fasmer.orderapplication.dao.PartDao;
import no.fasmer.orderapplication.dao.VendorDao;
import no.fasmer.orderapplication.dao.VendorPartDao;
import no.fasmer.orderapplication.entity.CustomerOrder;
import no.fasmer.orderapplication.entity.LineItem;
import no.fasmer.orderapplication.entity.Part;
import no.fasmer.orderapplication.entity.PartKey;
import no.fasmer.orderapplication.entity.Vendor;
import no.fasmer.orderapplication.entity.VendorPart;
import no.fasmer.orderapplication.vo.VendorPartVO;

@Stateless
public class OrderBean {

    @Inject
    private CustomerOrderDao customerOrderDao;

    @Inject
    private LineItemDao lineItemDao;

    @Inject
    private PartDao partDao;

    @Inject
    private VendorDao vendorDao;

    @Inject
    private VendorPartDao vendorPartDao;

    @Inject
    private Logger logger;
    
    public void createPart(Part part) {
        try {
            partDao.persist(part);
            logger.log(Level.INFO, "Persisted part");
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void createPart(
            String partNumber, 
            int revision,
            String description,
            Date revisionDate,
            String specification,
            byte[] drawing) {

        try {

            final Part part = new Part(
                    partNumber,
                    revision,
                    description,
                    revisionDate,
                    drawing,
                    specification);

            logger.log(Level.INFO, "Created part with revision {0}", new Object[]{revision});
            partDao.persist(part);
            logger.log(Level.INFO, "Persisted part revision {0}", new Object[]{revision});
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<Part> getAllParts() {
        return partDao.getAllParts();
    }

    public void addPartToBillOfMaterial(
            String bomPartNumber,
            int bomRevision,
            String partNumber,
            int revision) {

        logger.log(Level.INFO, "BOM part number: {0}", bomPartNumber);
        logger.log(Level.INFO, "BOM revision: {0}", bomRevision);
        logger.log(Level.INFO, "Part number: {0}", partNumber);
        logger.log(Level.INFO, "Part revision: {0}", revision);

        try {
            final Part bom = partDao.find(new PartKey(bomPartNumber, bomRevision));
            logger.log(Level.INFO, "BOM Part found: {0}", bom.getPartNumber());

            final Part part = partDao.find(new PartKey(partNumber, revision));
            logger.log(Level.INFO, "Part found: {0}", part.getPartNumber());

            bom.getParts().add(part);
            part.setBomPart(bom);
        } catch (EJBException e) {
            logger.log(Level.SEVERE, "Failed to add part to bill of material.", e);
        }
    }

    public void createVendor(
            String name,
            String address,
            String contact,
            String phone) {
        try {
            final Vendor vendor = new Vendor(name, address, contact, phone);
            vendorDao.persist(vendor);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }
    
    public void createVendor(Vendor vendor) {
        try {
            vendorDao.persist(vendor);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }
    
    public void createVendorPart(VendorPartVO vendorPartVO) {
        try {
            final Part part = partDao.find(new PartKey(vendorPartVO.getPartNumber(), vendorPartVO.getRevision()));

            final VendorPart vendorPart = new VendorPart(vendorPartVO.getDescription(), vendorPartVO.getPrice(), part);
            vendorPartDao.persist(vendorPart);

            final Vendor vendor = vendorDao.find(vendorPartVO.getVendorId());
            vendor.addVendorPart(vendorPart);
            vendorPart.setVendor(vendor);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void createVendorPart(
            String partNumber,
            int revision,
            String description,
            double price,
            int vendorId) {
        
        try {
            final Part part = partDao.find(new PartKey(partNumber, revision));

            final VendorPart vendorPart = new VendorPart(description, price, part);
            vendorPartDao.persist(vendorPart);

            final Vendor vendor = vendorDao.find(vendorId);
            vendor.addVendorPart(vendorPart);
            vendorPart.setVendor(vendor);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        
    }

    public void createOrder(char status, int discount, String shipmentInfo) {
        try {
            final CustomerOrder order = new CustomerOrder(status, discount, shipmentInfo);
            customerOrderDao.persist(order);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void createOrder(CustomerOrder customerOrder) {
        try {
            logger.log(Level.INFO, "Creating order ...");
            customerOrderDao.persist(customerOrder);
            logger.log(Level.INFO, "Order created.");
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<CustomerOrder> getOrders() {
        try {
            return customerOrderDao.getOrders();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void addLineItem(Integer orderId, String partNumber, int revision, int quantity) {
        try {
            final CustomerOrder order = customerOrderDao.find(orderId);
            logger.log(Level.INFO, "Found order ID {0}", orderId);

            final Part part = partDao.find(new PartKey(partNumber, revision));

            final LineItem lineItem = new LineItem(order, quantity, part.getVendorPart());
            order.addLineItem(lineItem);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Couldn't add {0} to order ID {1}.", new Object[]{partNumber, orderId});
            throw new EJBException(e.getMessage());
        }
    }

    public double getBillOfMaterialPrice(String bomPartNumber, int bomRevision, String partNumber, int revision) {
        double price = 0.0;
        try {
            final Part bom = partDao.find(new PartKey(bomPartNumber, bomRevision));
            final Collection<Part> parts = bom.getParts();
            for (Part part : parts) {
                final VendorPart vendorPart = part.getVendorPart();
                price += vendorPart.getPrice();
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

        return price;
    }
    
    public double getOrderPrice(Integer orderId) {
        double price = 0.0;
        try {
            final CustomerOrder order = customerOrderDao.find(orderId);
            price = order.calculateAmount();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return price;
    }
    
    public void adjustOrderDiscount(int adjustment) {
        try {
            final List<CustomerOrder> orders = customerOrderDao.getOrders();
            for (Iterator<CustomerOrder> it = orders.iterator(); it.hasNext();) {
                final CustomerOrder order = it.next();
                final int newDiscount = order.getDiscount() + adjustment;
                order.setDiscount((newDiscount > 0) ? newDiscount : 0);
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Double getAvgPrice() {
        try {
            return vendorPartDao.getAvgVendorPartPrice();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Double getTotalPricePerVendor(int vendorId) {
        try {
            return vendorPartDao.getTotalPricePerVendor(vendorId);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<String> locateVendorsByPartialName(String name) {
        final List<String> names = new ArrayList<>();
        
        try {
            final List<Vendor> vendors = vendorDao.getVendorsByPartialName(name);
            for (Iterator<Vendor> iterator = vendors.iterator(); iterator.hasNext();) {
                final Vendor vendor = iterator.next();
                names.add(vendor.getName());
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        
        return names;
    }
    
    public int countAllItems() {
        try {
            return lineItemDao.getNumLineItems();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<LineItem> getLineItems(int orderId) {
        try {
            return lineItemDao.getLineItems(orderId);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void removeOrder(Integer orderId) {
        try {
            final CustomerOrder order = customerOrderDao.find(orderId);
            customerOrderDao.remove(order);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public String reportVendorsByOrder(Integer orderId) {
        final StringBuilder report = new StringBuilder();
        
        try {
            final List<Vendor> vendors = vendorDao.getVendorsByOrder(orderId);
            for (Iterator<Vendor> iterator = vendors.iterator(); iterator.hasNext();) {
                final Vendor vendor = iterator.next();
                
                report.append(vendor.getVendorId())
                    .append(' ')
                    .append(vendor.getName())
                    .append(' ')
                    .append(vendor.getContact())
                    .append('\n');
            }
            
        } catch (Exception e) {
            throw new EJBException(e);
        }
        
        return report.toString();
    }

}
