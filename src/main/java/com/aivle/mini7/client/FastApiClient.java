package com.aivle.mini7.client;

import com.aivle.mini7.dto.EmergencyRequest;
import com.aivle.mini7.dto.EmergencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pythonApi", url = "${hospital.api.host}")
public interface FastApiClient {

    @PostMapping("/api/hello")
    EmergencyResponse sendData(@RequestBody EmergencyRequest requestDTO);

}
