package com.kaminski.store.serviceshopping.repository;

import com.kaminski.store.serviceshopping.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCustomerId(Long customerId);

    Invoice findByNumberInvoice(String numberInvoice);

}
