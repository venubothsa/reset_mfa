package com.credit.one.services;

import com.credit.one.model.*;

import java.util.List;

public interface IResetMFAService {
    public List<FactorType> getResetFactors(ResetMFA resetMFA);

    public String resetFactor(String[] factors);

    public String enrollMfa(EnrollMfa enrollMfa);

    public String enrollSms(EnrollMobile enrollMobile);

    public String enrollEmail(EnrollEmail enrollEmail);
}
