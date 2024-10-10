package com.spring.jwt.inspectionReport.repo;

import com.spring.jwt.inspectionReport.entity.InspectionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalInspectionReportRepository extends JpaRepository<InspectionReport, Integer> {



}
