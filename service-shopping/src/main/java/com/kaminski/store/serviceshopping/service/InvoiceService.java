package com.kaminski.store.serviceshopping.service;

import com.kaminski.store.serviceshopping.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> findAll();

    Invoice create(Invoice invoice);

    Invoice update(Invoice invoice);

    Invoice delete(Invoice invoice);

    Invoice getInvoice(Long id);

}
