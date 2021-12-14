package com.credit.one.client;

import com.credit.one.model.EnrollFactorResponse;
import com.credit.one.model.ResetFactor;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "ResetMFA", url = "${okta.client.orgUrl}", path = "/api/v1/users/")
@Headers({"Content-Type: application/json", "Accept: application/json"})
public interface IFeignClient {

    @PostMapping(path = "{userId}/lifecycle/reset_factors")
    public Map<String, String> resetMfa(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("userId") String userId);

    @GetMapping(path = "{userId}/factors")
    public List<ResetFactor> getFactors(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("userId") String userId);

    @DeleteMapping(path = "{userId}/factors/{factorId}")
    public ResponseEntity<String> resetByFactorId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("userId") String userId, @PathVariable("factorId") String factorId);

    @PostMapping(path = "{userId}/factors")
    public ResponseEntity<EnrollFactorResponse> enrollFactor(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("userId") String userId, @RequestParam("tokenLifetimeSeconds") Integer tokenLifetimeSeconds, @RequestBody ResetFactor resetFactor);

}
