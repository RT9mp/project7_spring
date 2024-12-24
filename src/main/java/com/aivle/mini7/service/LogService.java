package com.aivle.mini7.service;

import com.aivle.mini7.dto.EmergencyData;
import com.aivle.mini7.dto.EmergencyResponse;
import com.aivle.mini7.entity.Log;
import com.aivle.mini7.entity.LogDetail;
import com.aivle.mini7.repository.LogDetailRepository;
import com.aivle.mini7.repository.LogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Service
@Transactional
public class LogService {

    private final LogRepository logRepository;
    private final LogDetailRepository logDetailRepository;

    public void saveLogWithDetails(EmergencyResponse response) {
        // 1. Log 저장
        Log log = new Log();
        log.setDatetime(new Date());
        log.setInputText(response.getText());
        log.setInputLatitude(response.getStart_lat());
        log.setInputLongitude(response.getStart_lng());
        log.setEmClass(response.getGrade());
        log.setTotalHospitals(response.getEmergency_data().size());
        logRepository.save(log);

        // 2. LogDetail 저장
        for (EmergencyData data : response.getEmergency_data()) {
            LogDetail logDetail = getLogDetail(data, log);
            logDetailRepository.save(logDetail);
        }
    }

    private static LogDetail getLogDetail(EmergencyData data, Log log) {
        LogDetail logDetail = new LogDetail();
        logDetail.setLog(log);
        logDetail.setHospitalName(data.getHospitalName());
        logDetail.setAddress(data.getAddress());
        logDetail.setEmergencyType(data.getEmergencyMedicalInstitutionType());
        logDetail.setPhoneNumber1(data.getPhoneNumber1());
        logDetail.setPhoneNumber3(data.getPhoneNumber3());
        logDetail.setLatitude(data.getLatitude());
        logDetail.setLongitude(data.getLongitude());
        logDetail.setDistance(data.getDistance());
        return logDetail;
    }

    public Page<Log> getLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logRepository.findAll(pageable);
    }

    public Page<Log> getLogsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        Date startDatetime = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDatetime = Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        Pageable pageable = PageRequest.of(page, size);

        return logRepository.findByDatetimeBetween(startDatetime, endDatetime, pageable);
    }

}
