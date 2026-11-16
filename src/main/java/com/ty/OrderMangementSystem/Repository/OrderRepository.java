package com.ty.OrderMangementSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ty.OrderMangementSystem.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
