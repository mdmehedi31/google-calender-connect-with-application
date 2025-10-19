package com.googlecalender.controller;


import com.googlecalender.service.CalenderAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("oauth2")
public class GoogleAuthController {

    private CalenderAuthService calenderService;
    private static final Logger log= LoggerFactory.getLogger(GoogleAuthController.class);
    public GoogleAuthController(CalenderAuthService calenderService) {
        this.calenderService = calenderService;
    }

    @GetMapping("/login")
    public ResponseEntity<RedirectView> googleAuthLogin(){
     RedirectView redirectView = this.calenderService.googleLoginView();
     return new ResponseEntity<>(redirectView, HttpStatus.FOUND);
    }

    @GetMapping("/callback/google")
    public ResponseEntity<String> oAuthCallBack(@RequestParam("code") String code, HttpServletRequest httpServletRequest){
        String credential= this.calenderService.oAuthCallback(code,httpServletRequest);
       /* Credential obj= (Credential) httpServletRequest.getAttribute("googleCredential");
       log.info("Controller -> access token : {}, refresh token : {}, expire token : {}", obj.getAccessToken(), obj.getRefreshToken(), obj.getExpiresInSeconds());*/
        return new ResponseEntity<>(credential, HttpStatus.FOUND);
    }
}
