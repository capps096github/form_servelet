
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class FormServelet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // Get the parameter from the request
    String selectedValue = request.getParameter("options");

    // writetr
    PrintWriter writer = response.getWriter();

    // sesssions
    HttpSession session = request.getSession();

    // cookies
    Cookie[] cookies = request.getCookies();

    // Send the response back to the user
    try {
      response.setContentType("text/html");

      // switch
      switch (selectedValue) {

        case "create_cookie":
          createCookies(request, response);
          break;
        case "check_if_cookie_exists":
          checkIfCookieExists(cookies, writer);
          break;
        case "remove_session_variables":
          removeSessionVariables(session);
          break;
        case "show_cookies_session_attributes":
          showAttributes(request, response, writer, session, cookies);

          break;

        default:
          // create session
          createSession(request, response, session);
          break;
      }

      writer.println("<html><body>");
      writer.println("You Selected, " + selectedValue);
      writer.println("</body></html>");
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // function to create a cookie
  public void createCookies(HttpServletRequest request, HttpServletResponse response) {
    int height = 20;
    String gender = "Male";

    Cookie cookie = new Cookie("gender", gender);
    Cookie cookie2 = new Cookie("height", String.valueOf(height));
    response.addCookie(cookie);
    response.addCookie(cookie2);
  }

  public void createSession(HttpServletRequest request, HttpServletResponse response,
      HttpSession session)
      throws IOException {
    int age = 20;
    session.setAttribute("color", "Blue");
    session.setAttribute("age", age);

  }

  public void checkIfCookieExists(Cookie[] cookies, PrintWriter writer) {

    // loop
    for (int i = 0; i < cookies.length; i++) {
      Cookie c = cookies[i];
      String name = c.getName();

      if (name != null && name.length() > 0) {
        writer.println("Cookie Doesn't Exist");
        break;
      }

    }
  }

  public void removeSessionVariables(HttpSession session) {
    session.removeAttribute("color");
    session.removeAttribute("age");
  }

  public void showAttributes(HttpServletRequest request, HttpServletResponse response, PrintWriter writer,
      HttpSession session, Cookie[] cookies) {
    for (int i = 0; i < cookies.length; i++) {
      Cookie c = cookies[i];
      String name = c.getName();
      String value = c.getValue();
      writer.println(name + " = " + value);
    }

    // print session contents

    Enumeration<String> e = session.getAttributeNames();
    while (e.hasMoreElements()) {
      String name = (String) e.nextElement();
      String value = session.getAttribute(name).toString();
      writer.println(name + " = " + value);
    }

  }

}