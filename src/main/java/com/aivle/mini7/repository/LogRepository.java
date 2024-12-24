package com.aivle.mini7.repository;

import com.aivle.mini7.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findByDatetimeBetween(Date startDatetime, Date endDatetime, Pageable pageable);
}