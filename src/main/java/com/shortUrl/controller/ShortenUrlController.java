package com.shortUrl.controller;

import com.shortUrl.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rafael.alves on 20/01/2019.
 */
@Controller
@RequestMapping()
public class ShortenUrlController {

    @Autowired
    private com.shortUrl.repository.Url url;

    Long id = 2019l;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RedirectView redirectToUrl(@PathVariable Long id) {
	final Url urlToRedirect = this.url.getOne(id);

	if (url != null) {
	    RedirectView redirectView = new RedirectView();
	    redirectView.setUrl(urlToRedirect.getUrlAdress().toString());
	    return redirectView;

	} else {
	    return null;
	}
    }

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    public ModelAndView shorten(@Valid Url pUrl, BindingResult result, RedirectAttributes pAttributes,
		    HttpServletRequest httpRequest) {
	if (!pUrl.getUrlAdress().isEmpty() && !isUrlFormatted(pUrl.getUrlAdress())) {
	    ObjectError objectError = new ObjectError("url.urlAdress", "Format Url is not valid");
	    result.addError(objectError);
	}
	//In case of error return messages without lost the datas of fields.
	if (result.hasErrors()) {
	    return showShortenPage(pUrl);
	}

	//Set a ID to URL Shorted
	pUrl.setIdUrl(getidShortenUrl());
	//Save the URL at database
	this.url.save(pUrl);
	//Get the URL base of application
	String baseUrl = httpRequest.getRequestURL().toString().replaceAll(httpRequest.getServletPath(), "/");
	//Redirect to refresh the form
	ModelAndView mv = new ModelAndView("redirect:/shorten");
	//ADD the URL shorted to be show to user
	pAttributes.addFlashAttribute("shortedUrl", baseUrl + pUrl.getIdUrl());
	//Send a sucsses message
	pAttributes.addFlashAttribute("mensagem", "URL shortened successfully");
	return mv;
    }

    @GetMapping("/")
    public ModelAndView showApp(Url pUrl) {
	ModelAndView modelAndView = new ModelAndView("urlIndex");
	modelAndView.addObject("title", "urlIndex.title");
	modelAndView.addObject("nmPagina", "urlIndex.nmPagina");
	return modelAndView;
    }

    @GetMapping("/shorten")
    public ModelAndView showShortenPage(Url pUrl) {
	ModelAndView modelAndView = new ModelAndView("shortenUrl");
	modelAndView.addObject("title", "shortenUrl.title");
	modelAndView.addObject("nmPagina", "shortenUrl.nmPagina");

	return modelAndView;
    }

    private Long getidShortenUrl() {
	return ++id;
    }

    //Resposable to verify if URL is formatted
    private boolean isUrlFormatted(String url) {
	boolean isFormatted = true;
	try {
	    new URL(url);
	} catch (MalformedURLException e) {
	    isFormatted = false;
	}
	return isFormatted;
    }
}
