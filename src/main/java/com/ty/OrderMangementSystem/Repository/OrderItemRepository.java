package com.ty.OrderMangementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ty.OrderMangementSystem.entity.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
