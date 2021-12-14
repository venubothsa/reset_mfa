package com.credit.one.util;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidateUser {
    @Value("${okta.client.orgUrl}")
    private String orgUrl;
    @Value("${okta.client.token}")
    private String apiToken;

    public String validateAndGetUserId(String email, String ssn, String globalPin) {
        Client client = Clients.builder()
                .setOrgUrl(orgUrl)
                .setClientCredentials(new TokenClientCredentials(apiToken)).build();

        boolean isClientReadyStatus = client.isReady(client::listApplications);
        System.out.println("Client status ----" + isClientReadyStatus);

        String username = null;
        String uid = null;

        System.out.println("Email ID is =" + email);

        if (!email.isEmpty()) {
            UserList users = client.listUsers(null, null, "profile.login eq \"" + email + "\"", null, null);
            System.out.println(users);
            String ssn2;
            String globalpin;
            for (User u : users) {
                uid = u.getId();
                username = u.getProfile().getLogin();
                User user = client.getUser(uid);
               /* if(Objects.nonNull(username)&&username.equalsIgnoreCase(email)){
                    return uid;
                }*/
                ssn2 = (String) user.getProfile().get("ssn");
                globalpin = (String) user.getProfile().get("globalPin");
                // System.out.println("SSN = " + ssn2);
                // System.out.println("Global PIN = " + globalpin);
                boolean validated = false;
                if ((!ssn.isEmpty())) {
                    if (ssn.equalsIgnoreCase(ssn2)) {
                        System.out.println("SSN validated");
                        validated = true;
                    }
                    if (validated) {
                        return uid;
                    }

                }
                if ((!globalPin.isEmpty())) {
                    if (globalPin.equalsIgnoreCase(globalpin)) {
                        System.out.println("Global Pin validated");
                        validated = true;

                    }

                    if (validated) {
                        return uid;
                    }

                }
            }
        }

        return null;
    }
}
