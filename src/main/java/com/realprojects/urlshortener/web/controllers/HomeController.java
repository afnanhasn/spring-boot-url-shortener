package com.realprojects.urlshortener.web.controllers;

import com.realprojects.urlshortener.ApplicationProperties;
import com.realprojects.urlshortener.domain.entities.ShortUrl;
import com.realprojects.urlshortener.domain.entities.User;
import com.realprojects.urlshortener.domain.exceptions.ShortUrlNotFoundException;
import com.realprojects.urlshortener.domain.models.CreateShortUrlCmd;
import com.realprojects.urlshortener.domain.models.ShortUrlDto;
import com.realprojects.urlshortener.domain.services.ShortUrlService;
import com.realprojects.urlshortener.web.dtos.CreateShortUrlForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


//RENDERING DYNAMIC HTML PAGES(using thymeleaf)
@Controller
/*RestController annotation is used for Restapi endpoints which returns Json or XML data as http response
Controller annotation is used for Webapps which return data and return it as a view(string or html etc).
Also, @Controller + @ResponseBody = @RestController
 */
public class HomeController {
    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;
    private final SecurityUtils securityUtils;

    public HomeController(ShortUrlService shortUrlService, ApplicationProperties properties, SecurityUtils securityUtils) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
        this.securityUtils = securityUtils;
    }
//Thymeleaf dependency was added in pom and html pages were moved from 'resources/static' to 'resources/templates'
    @GetMapping("/")
    public String home(Model model){//Model parameter added from spring framework for adding dynamic data
        User currentUser = securityUtils.getCurrentUser();
        List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
        return "index";//.html ext isn't required with thymeleaf
//        model.addAttribute("title","URL Shortener- dynamic data with thymeleaf ");
    }

    @PostMapping("/short-urls")
    String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form, //This triggers validations inside Dtos Createshorturl
                          BindingResult bindingResult, // Used To capture errors and most important to keep right after @Valid else won't work
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicShortUrls();
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl", properties.baseUrl());
            return "index"; /*If there are any errors then index.html page will be rendered along with incorrect input in form
            and also table still needs to be displayed hence above List and model attributes are added as well*/
        }

        try { // If there are no errors while input then short url creation will happen and get stored in db
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl());
            var shortUrlDto = shortUrlService.createShortUrl(cmd);
            redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully "+
                    properties.baseUrl()+"/s/"+shortUrlDto.shortKey());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL");

        }
        return "redirect:/"; // If no errors then it get's redirected to home page, line 32
    }
    @GetMapping("/s/{shortKey}")//controller responsible for redirecting
    String redirectToOriginalUrl(@PathVariable String shortKey) {
        Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey);
        if(shortUrlDtoOptional.isEmpty()) { //To avoid if a short url with made-up short key gets hit
            throw new ShortUrlNotFoundException("Invalid short key: "+shortKey);//Java class for handling this error and also global handler
        }
        ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
        return "redirect:"+shortUrlDto.originalUrl();
    }

    @GetMapping("/login")
    String loginForm() {
        return "login";
    }
}



             //RENDERING STATIC HTML PAGES
/*
@Controller //Stating that this is a controller component and having request handler methods
public class HomeController {

  @GetMapping("/")
  // handler method which will handle routes here, local host 8080
  public String home(){
      return "index.html";
      //rendering index.html file for browser
  }

  @GetMapping("/about")
  // handler method which will handle routes here, local host 8080/about
  public String about(){
        return "about.html";
        //rendering about.html file for browser
  }

}*/



