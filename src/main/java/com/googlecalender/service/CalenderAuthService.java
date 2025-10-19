package com.googlecalender.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;

@Service
public class CalenderAuthService {

    private static final Logger log= LoggerFactory.getLogger(CalenderAuthService.class);

    @Value("${google.client_id}")
    private String clientId;
    @Value("${google.secret_id}")
    private String clientSecret;
    @Value("${google.redirect_uri}")
    private String redirectUri;

    public RedirectView googleLoginView(){
        try{
            log.info("Google -> Client id : {}, Client secret : {}, redirect uri : {}", clientId, clientSecret, redirectUri);
            GoogleAuthorizationCodeFlow codeFlow = buildFlow();
            String url = codeFlow.newAuthorizationUrl().setRedirectUri(this.redirectUri).setAccessType("offline").build();
            log.info("Generate url : {}", url);
            return new RedirectView(url);
        } catch (Exception e) {
            throw e;
        }
    }

    //  "{\"web\":{\"client_id\":\"%s\",\"client_secret\":\"%s\"}}",
    private GoogleAuthorizationCodeFlow buildFlow() {
        try{
        String clientSecrateJson= String.format("{\"web\":{\"client_id\":\"%s\",\"client_secret\":\"%s\"}}", clientId, clientSecret);
            GoogleClientSecrets secrets=GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new StringReader(clientSecrateJson));

            return new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    secrets,
                    Collections.singleton(CalendarScopes.CALENDAR)).
                    setAccessType("offline")
                    .build();
        }catch (IOException e) {
            log.error("An Exception thrown from IOException : ",e);
            return null;
           // throw e;
        } catch (Exception e) {
            log.error("An Exception thrown : ",e);
            //throw e;
            return null;
        }
    }

    public String oAuthCallback(String code, HttpServletRequest httpServletRequest) {
        try {

            log.info("The redirect url code is : {}", code);
            GoogleAuthorizationCodeFlow flow = buildFlow();

            Credential credential = null;
            if (flow != null) {
                log.info("When flow is not null");
                GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
                log.info("Access token : {}, Refress token : {}, Expire token : {}", tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), tokenResponse.getExpiresInSeconds());
                credential = flow.createAndStoreCredential(tokenResponse, "user1");
            }
            log.info("The credential is : " + credential);

            ObjectMapper objectMapper = new ObjectMapper();
            String creedentials = objectMapper.writeValueAsString(credential);
            httpServletRequest.getSession().setAttribute("googleCredential", credential);
            log.info("----------------------------------------------------------------------------------------");
            Credential obj = (Credential) httpServletRequest.getAttribute("googleCredential");
            log.info("Controller -> access token : {}, refresh token : {}, expire token : {}", obj.getAccessToken(), obj.getRefreshToken(), obj.getExpiresInSeconds());
            return "Success";
        } catch (Exception e) {
            log.error("An Exception thrown : ", e);
            //throw e;
            return e.getMessage();
        }
    }
}
