package com.training.orderfood.repository;

import com.training.orderfood.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorProfileRepository  extends JpaRepository<VendorProfile, Long> {


 }
