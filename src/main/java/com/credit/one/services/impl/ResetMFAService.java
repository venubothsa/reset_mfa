package com.credit.one.services.impl;

import com.credit.one.client.IFeignClient;
import com.credit.one.model.*;
import com.credit.one.services.IResetMFAService;
import com.credit.one.util.ValidateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResetMFAService implements IResetMFAService {
    @Autowired
    private ValidateUser validateUser;
    @Autowired
    private IFeignClient iFeignClient;
    @Value("${okta.client.token}")
    private String token;
    @Value("${token.prefix}")
    private String prefix;
    private static String userId;
    private static String PROVIDER = "OKTA";


    @Override
    public List<FactorType> getResetFactors(ResetMFA resetMFA) {
        List<FactorType> factorTypes = new ArrayList<>();
        userId = validateUser.validateAndGetUserId(resetMFA.getUserName(), resetMFA.getSsn(), resetMFA.getGlobalPin());
        if (Objects.nonNull(userId)) {
            List<ResetFactor> resetFactors = iFeignClient.getFactors(prefix + " " + token, userId);
            resetFactors.forEach(resetFactor -> {
                FactorType factorType = new FactorType();
                if ("sms".equalsIgnoreCase(resetFactor.getFactorType()) && Objects.nonNull(resetFactor.getProfile()) && Objects.nonNull(resetFactor.getProfile().getPhoneNumber())) {
                    factorType.setId(resetFactor.getId());
                    int length = resetFactor.getProfile().getPhoneNumber().length();
                    String phoneNo = resetFactor.getProfile().getPhoneNumber().substring(length - 4, length - 1);
                    factorType.setLabel("+1XXXXXXX" + phoneNo);
                    factorTypes.add(factorType);
                }
                if ("email".equalsIgnoreCase(resetFactor.getFactorType()) && Objects.nonNull(resetFactor.getProfile()) && Objects.nonNull(resetFactor.getProfile().getEmail())) {
                    factorType.setId(resetFactor.getId());
                    int length = resetFactor.getProfile().getEmail().substring(0, resetFactor.getProfile().getEmail().indexOf(
                            "@")).length();
                    String email = resetFactor.getProfile().getEmail().substring(length - 4, length - 1) + resetFactor.getProfile().getEmail().substring(resetFactor.getProfile().getEmail().indexOf(
                            "@"), resetFactor.getProfile().getEmail().length() - 1);
                    factorType.setLabel("XXXXXXX" + email);
                    factorTypes.add(factorType);
                }

            });

            /*Map<String, String> responseObject = iFeignClient.resetMfa(token, userId);
            if (Objects.nonNull(responseObject)) {
                return true;
            }*/
        }
        return factorTypes;
    }

    @Override
    public String resetFactor(String[] factors) {
        int i = 0;
        for (String factor : factors) {
            ResponseEntity<String> responseEntity = iFeignClient.resetByFactorId(prefix + " " + token, userId, factor);
            if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT || responseEntity.getStatusCode() == HttpStatus.OK) {
                i++;
            }
        }
        if (factors.length == i) {
            return "Reset factors successfully processed..!";
        }
        return "Either SMS or E-mail reset factor is failed..!";
    }

    @Override
    public String enrollMfa(EnrollMfa enrollMfa) {
        ResetFactor resetFactor = new ResetFactor();
        resetFactor.setProvider(PROVIDER);
        String status = "";
        if (Objects.nonNull(enrollMfa.getMobileNumber()) && !enrollMfa.getMobileNumber().isEmpty()) {
            resetFactor.setFactorType("sms");
            Profile profile = new Profile();
            profile.setPhoneNumber(enrollMfa.getMobileNumber());
            resetFactor.setProfile(profile);
            ResponseEntity<EnrollFactorResponse> enrollFactor = iFeignClient.enrollFactor(prefix + " " + token, userId, 0, resetFactor);
            if (enrollFactor.getStatusCode() == HttpStatus.OK) {
                status = "Sms enroll :" + Objects.requireNonNull(enrollFactor.getBody()).getStatus();
            }
        }

        if (Objects.nonNull(enrollMfa.getEmailId()) && !enrollMfa.getEmailId().isEmpty()) {
            resetFactor.setFactorType("email");
            Profile profile = new Profile();
            profile.setEmail(enrollMfa.getEmailId());
            resetFactor.setProfile(profile);
            ResponseEntity<EnrollFactorResponse> enrollFactor = iFeignClient.enrollFactor(prefix + " " + token, userId, 0, resetFactor);
            if (enrollFactor.getStatusCode() == HttpStatus.OK) {
                return  status + " Email enroll :" + Objects.requireNonNull(enrollFactor.getBody()).getStatus();
            }
        }


        return "Enroll failed.";
    }

    @Override
    public String enrollSms(EnrollMobile enrollMobile) {
        ResetFactor resetFactor = new ResetFactor();
        resetFactor.setProvider(PROVIDER);

        if (Objects.nonNull(enrollMobile.getMobileNumber()) && !enrollMobile.getMobileNumber().isEmpty()) {
            resetFactor.setFactorType("sms");
            Profile profile = new Profile();
            profile.setPhoneNumber(enrollMobile.getMobileNumber());
            resetFactor.setProfile(profile);
            ResponseEntity<EnrollFactorResponse> enrollFactor = iFeignClient.enrollFactor(prefix + " " + token, userId, 0, resetFactor);
            if (enrollFactor.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(enrollFactor.getBody()).getStatus();
            }
        }
        return "Enroll failed.";
    }

    @Override
    public String enrollEmail(EnrollEmail enrollEmail) {
        ResetFactor resetFactor = new ResetFactor();
        resetFactor.setProvider(PROVIDER);
        if (Objects.nonNull(enrollEmail.getEmail()) && !enrollEmail.getEmail().isEmpty()) {
            resetFactor.setFactorType("email");
            Profile profile = new Profile();
            profile.setEmail(enrollEmail.getEmail());
            resetFactor.setProfile(profile);
            ResponseEntity<EnrollFactorResponse> enrollFactor = iFeignClient.enrollFactor(prefix + " " + token, userId, 0, resetFactor);
            if (enrollFactor.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(enrollFactor.getBody()).getStatus();
            }
        }
        return "Enroll failed.";
    }
}
