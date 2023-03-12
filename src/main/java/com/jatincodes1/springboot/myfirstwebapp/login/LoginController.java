package com.jatincodes1.springboot.myfirstwebapp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name") // this will ensure a session is maintained when you are changing the html pages
public class LoginController {

    // below is used in debugging
    //private Logger logger = LoggerFactory.getLogger(getClass());
    // login => com.in28minutes.springboot.myfirstwebapp.login.LoginController => login.jsp

    //http://localhost:8080/login?name=Ranga
    //Model
//    @RequestMapping("login")
//    public String gotoLoginPage(@RequestParam String name , ModelMap model ) {
//        model.put("name",name); // it display value in html pages
//
//        // Below is used in debugging
//        /*
//        logger.debug("Request param is {}", name);
//        logger.info("I want this printed at info level");
//        logger.warn("I want this printed at warn level");
//        System.out.println("Request param is " + name); //NOT RECOMMENDED FOR PROD CODE // will be seen in debugger
//
//         */
//        return "login";
//    }


    private AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService){
        super();
        this.authenticationService=authenticationService;
    }

    @RequestMapping(value="login",method = RequestMethod.GET)
    public String gotoLoginPage() {

        return "login";
    }

    @RequestMapping(value="login",method = RequestMethod.POST)
    //login?name=Ranga RequestParam
    public String gotoWelcomePage(@RequestParam String name,
                                  @RequestParam String password, ModelMap model) {

        if(authenticationService.authenticate(name,password)){
            model.put("name", name);
            //model.put("password", password);  // dont put password on page
            //Authentication
            //name - in28minutes
            //password - dummy

            return "welcome";
        }

        model.put("errorMessage","Invalid Credentials! Please try again.");
        return "login";

    }
}
