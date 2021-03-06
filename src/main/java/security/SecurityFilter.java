package security;

import com.epam.strings.StringEncryptUtil;
import dao.PersonDao;
import model.Person;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

//@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

  private static String KEY = "key";

  private PersonDao personDao;

  @Override
  public void init(FilterConfig filterConfig) {
    ServletContext servletContext = filterConfig.getServletContext();
    personDao = (PersonDao) servletContext.getAttribute("personDao");
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpSession session = request.getSession(true);

    if (session.getAttribute(KEY) != null)
      chain.doFilter(request, response);

    Map<String, String[]> parameterMap = request.getParameterMap();
    if (parameterMap.containsKey("j_password") && parameterMap.containsKey("j_username")) {
      // TODO: 22/10/2016

      Optional<Person> authorize = authorize(parameterMap);
      if (authorize.isPresent()) {
        session.setAttribute(KEY, authorize.get());
        chain.doFilter(request, response);
      } else
        request.getRequestDispatcher("/error.html").forward(request, response);

    } else {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.html");
      // TODO: 22/10/2016 посмотреть что можно сделать что бы не терять информацию о странице куда пользователь зашёл
      requestDispatcher.forward(request, response);
    }
  }

  private Optional<Person> authorize(Map<String, String[]> parameterMap) {
    String login = parameterMap.get("j_username")[0];
    String password = parameterMap.get("j_password")[0];
    String hash = StringEncryptUtil.encrypt(password);

    return personDao.findByLoginAndPasswordHash(login, hash);
  }
}
