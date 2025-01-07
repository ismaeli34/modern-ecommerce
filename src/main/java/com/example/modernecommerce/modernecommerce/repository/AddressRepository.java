package com.example.modernecommerce.modernecommerce.repository;

import com.example.modernecommerce.modernecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
