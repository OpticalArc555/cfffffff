package com.spring.jwt.repository;

import com.spring.jwt.entity.BeadingCAR;
import com.spring.jwt.entity.CarVerified;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BeadingCarRepo extends JpaRepository<BeadingCAR, Integer>, JpaSpecificationExecutor<BeadingCAR> {

    List<BeadingCAR> findByUserId (int userId);


}
