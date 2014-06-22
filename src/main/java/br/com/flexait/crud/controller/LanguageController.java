package br.com.flexait.crud.controller;

import static br.com.caelum.vraptor.view.Results.http;
import static br.com.caelum.vraptor.view.Results.referer;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.flexait.crud.interceptor.LocaleInterceptor;

@Controller
public class LanguageController {

	private static final String LANGUAGE_CONFIG_SUCCESS = "language.config.success";
	@Inject private Result result;
	@Inject private MutableResponse response;
	@Inject private ResourceBundle resourceBundle;
	
	public LanguageController() {
	}
	
	@Path("/{languageTag}")
	public void set(String languageTag) {
		setCookie(languageTag);
		redirect();
	}

	private void redirect() {
		try {
			result.use(referer()).redirect();
		} catch (Exception e) {
			result.use(http()).body(resourceBundle.getString(LANGUAGE_CONFIG_SUCCESS));
		}
	}

	private void setCookie(String languageTag) {
		Cookie cookie = new Cookie(LocaleInterceptor.VRAPTOR_LOCALE, languageTag);
		cookie.setPath("/");
		cookie.setMaxAge(LocaleInterceptor.MONTH);
		response.addCookie(cookie);
	}
	
}