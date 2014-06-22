package br.com.flexait.crud.interceptor;

import static com.google.common.base.Objects.firstNonNull;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.http.MutableRequest;

@Intercepts
@RequestScoped
public class LocaleInterceptor {

	public static final String VRAPTOR_LOCALE = "vraptor-locale";
	public static final int MONTH = 60 * 60 * 24 * 30;
	@Inject private MutableRequest request;
	@Inject private HttpServletResponse response;
	
	public LocaleInterceptor() {
	}
	
	@BeforeCall
	public void setLocale() {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie c : cookies) {
				if(c.getName().equals(VRAPTOR_LOCALE)) {
					Locale.setDefault(Locale.forLanguageTag(c.getValue()));
					return;
				}
			}
		}
		
		setCookie();
	}

	private void setCookie() {
		String parameter = firstNonNull(getInitLocale(), "en-US");
		Cookie cookie = new Cookie(VRAPTOR_LOCALE, parameter);
		cookie.setPath("/");
		cookie.setMaxAge(MONTH);
		response.addCookie(cookie);
	}

	private String getInitLocale() {
		return request.getServletContext().getInitParameter(Config.FMT_LOCALE);
	}
	
}
