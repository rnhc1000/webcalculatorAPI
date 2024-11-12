package br.dev.ferreiras.webcalculatorapi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HandleErrorController implements ErrorController {
  public static final Logger logger = LoggerFactory.getLogger(HandleErrorController.class);

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {

    logger.info("Something bad had happened harry!");
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    logger.info("ERROR_STATUS_CODE -> {}", Integer.valueOf(status.toString()));

    int statusCode = Integer.parseInt(status.toString());

    return switch (statusCode) {

      case 401 -> "401-error.html";
      case 403 -> "403-error.html";
      case 404 -> "404-error.html";
      case 500 -> "500-error.html";
      default -> "error.html";

    };
  }
}
