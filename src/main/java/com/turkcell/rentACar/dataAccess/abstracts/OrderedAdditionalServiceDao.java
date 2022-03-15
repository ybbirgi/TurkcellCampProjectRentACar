package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> {
    List<OrderedAdditionalService> getAllByInvoice_InvoiceNo(Integer id);
    boolean existsByQuantityAndAdditionalService_ServiceId(Integer quantity,Integer serviceId);
}
