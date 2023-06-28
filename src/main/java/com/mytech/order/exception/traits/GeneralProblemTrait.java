package com.mytech.order.exception.traits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Throwables;
import com.mytech.order.exception.CustomerOrderProblemType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;

/**
 * @author SOMISV
 *     <p>To return the Error Response as per the different kind of Excetions
 */
public interface GeneralProblemTrait {

  // handle what every other trait does not
  @ExceptionHandler
  default ResponseEntity<?> handleException(Exception ex, NativeWebRequest request)
      throws JsonProcessingException {

    return Traits.responseEntity(ex, CustomerOrderProblemType.INTERNAL_ERROR_PROBLEM);
  }

  /**
   * To handle NoHandlerFoundException
   *
   * @param ex
   * @param request
   * @return
   * @throws JsonProcessingException
   */
  @ExceptionHandler
  default ResponseEntity<?> handleNoHandlerFoundException(
      NoHandlerFoundException ex, NativeWebRequest request) throws JsonProcessingException {

    return Traits.responseEntity(
        ex, CustomerOrderProblemType.RESOURCE_NOT_FOUND_PROBLEM, getErrorMessage(ex));
  }

  /**
   * To handle JsonProcessingException
   *
   * @param ex
   * @param request
   * @return
   * @throws JsonProcessingException
   */
  @ExceptionHandler
  default ResponseEntity<?> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex, NativeWebRequest request) throws JsonProcessingException {
    return Traits.responseEntity(
        ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM, getErrorMessage(ex));
  }

  /**
   * To handle JsonProcessingException
   *
   * @param ex
   * @param request
   * @return
   * @throws JsonProcessingException
   */
  @ExceptionHandler
  default ResponseEntity<?> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex, NativeWebRequest request)
      throws JsonProcessingException {

    return Traits.responseEntity(
        ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM, getErrorMessage(ex));
  }

  /**
   * To handle NativeWebRequest
   *
   * @param ex
   * @param request
   * @return
   * @throws JsonProcessingException
   */
  @ExceptionHandler
  default ResponseEntity<?> handleServletException(ServletException ex, NativeWebRequest request)
      throws JsonProcessingException {

    return Traits.responseEntity(
        ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM, getErrorMessage(ex));
  }

  @ExceptionHandler
  default ResponseEntity<?> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex, NativeWebRequest request)
      throws JsonProcessingException {

    return Traits.responseEntity(
        ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM, getErrorMessage(ex));
  }

  /**
   * This is to build the Error messages
   *
   * @param ex
   * @return
   */
  default String getErrorMessage(Exception ex) {
    String prettyMessage = null;
    Throwable rootCause = Throwables.getRootCause(ex);
    if (rootCause != null && prettyMessage == null) {
      prettyMessage = rootCause.getMessage();
    }
    return prettyMessage;
  }

  @ExceptionHandler
  default ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(
      HttpMediaTypeNotAcceptableException ex, NativeWebRequest request)
      throws JsonProcessingException {
    return Traits.responseEntity(
        ex,
        CustomerOrderProblemType.BAD_REQUEST_PROBLEM,
        "Invalid header value:: Supported Media Types: [application/json]");
  }
}
