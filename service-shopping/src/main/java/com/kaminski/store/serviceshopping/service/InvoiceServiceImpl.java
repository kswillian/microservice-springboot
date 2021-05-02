package com.kaminski.store.serviceshopping.service;

import com.kaminski.store.serviceshopping.client.CustomerClient;
import com.kaminski.store.serviceshopping.client.ProductClient;
import com.kaminski.store.serviceshopping.entity.Invoice;
import com.kaminski.store.serviceshopping.entity.InvoiceItem;
import com.kaminski.store.serviceshopping.model.Customer;
import com.kaminski.store.serviceshopping.model.Product;
import com.kaminski.store.serviceshopping.repository.InvoiceItemsRepository;
import com.kaminski.store.serviceshopping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    @Override
    public List<Invoice> findAll() {
        return  invoiceRepository.findAll();
    }

    @Override
    public Invoice create(Invoice invoice) {

        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );

        if (invoiceDB !=null){
            return  invoiceDB;
        }

        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);

        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct(invoiceItem.getId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceDB;

    }

    @Override
    public Invoice update(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice delete(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).get();

        if(invoice != null){

            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);

            List<InvoiceItem> invoiceItems = invoice.getItems().stream().map(item -> {
                Product product = productClient.getProduct(item.getProductId()).getBody();
                item.setProduct(product);
                return item;
            }).collect(Collectors.toList());

            invoice.setItems(invoiceItems);

        }

        return invoice;
    }

}
