package br.com.flexait.crud.controller;

import static br.com.caelum.vraptor.view.Results.http;
import static br.com.caelum.vraptor.view.Results.referer;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.MutableResponse;

@Controller
public class LanguageController {

	@Inject private Result result;
	@Inject private MutableResponse response;
	@Inject private MutableRequest request;
	
	public LanguageController() {
	}
	
	@Path("/{languageTag}")
	public void set(String languageTag) {
		Cookie cookie = new Cookie("vraptor-locale", languageTag);
		cookie.setPath("/");
		response.addCookie(cookie);
		request.setParameter("tenant-name", languageTag);
		
		try {
			result.use(referer()).redirect();
		} catch (Exception e) {
			result.use(http()).body("Successfully configured language");
		}
	}
	
}