package com.aivle.mini7.service;

import com.aivle.mini7.client.FastApiClient;
import com.aivle.mini7.dto.EmergencyRequest;
import com.aivle.mini7.dto.EmergencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyService {
    private final FastApiClient fastApiClient;

    public EmergencyResponse sendData(
            EmergencyRequest requestDTO) {
        return fastApiClient.sendData(requestDTO);
    }

}