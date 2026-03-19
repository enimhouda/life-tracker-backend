package com.life.tracker.repo;


import com.life.tracker.model.ZielEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZielRepository extends JpaRepository<ZielEntity, Integer> {

    List<ZielEntity> findByEmail(String email);
}
