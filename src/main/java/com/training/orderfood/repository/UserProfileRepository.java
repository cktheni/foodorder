package com.training.orderfood.repository;

import com.training.orderfood.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository  extends JpaRepository<UserProfile, Long> {
}
