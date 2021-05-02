package com.kaminski.store.serviceshopping.repository;

import com.kaminski.store.serviceshopping.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {
}
