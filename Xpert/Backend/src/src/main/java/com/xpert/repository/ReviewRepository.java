package com.xpert.repository;

import com.xpert.entity.Order;
import com.xpert.entity.Review;
import com.xpert.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	boolean existsByOrderAndClient(Order order, Users client);

}
