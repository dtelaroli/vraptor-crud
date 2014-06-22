package br.com.flexait.crud.interceptor;

import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.http.MutableRequest;

import com.google.common.base.Objects;

@Intercepts
@RequestScoped
public class LocaleInterceptor {

	@Inject private MutableRequest request;
	@Inject private HttpServletResponse response;
	
	public LocaleInterceptor() {
	}
	
	@BeforeCall
	public void setLocale() {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie c : cookies) {
				if(c.getName().equals("vraptor-locale")) {
					Locale.setDefault(Locale.forLanguageTag(c.getValue()));
					return;
				}
			}
		}
		String parameter = Objects.firstNonNull(request.getServletContext().getInitParameter("javax.servlet.jsp.jstl.fmt.locale"), "en-US");
		Cookie cookie = new Cookie("vraptor-locale", parameter);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
}
