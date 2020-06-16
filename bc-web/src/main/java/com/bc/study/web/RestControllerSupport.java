package com.bc.study.web;


import com.bc.study.exception.ServiceException;
import com.bc.study.status.Status;
import com.bc.study.web.rest.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Abstract base class for <code>rest</code> controller to be extends.All of rest controller should consider extend from this class.
 *
 * @author zw
 */
public abstract class RestControllerSupport extends ControllerSupport {

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public RestResponse handleException(Exception ex) {
    logger.error("", ex);
    if (ex instanceof ServiceException) {
      ServiceException svcEx = (ServiceException) ex;
      Status status = svcEx.getStatus();
      return restResponses.build2(status, svcEx.getArgs());
    }
//    } else if (ex instanceof UnauthenticatedException) {
//      Status status = new Status() {
//        @Override
//        public int getCode() {
//          return 401;
//        }
//
//        @Override
//        public String getMessage() {
//          return "无权访问(Unauthorized)，请先登录";
//        }
//      };
//      return restResponses.build2(status, null);
//    } else if (ex instanceof UnauthorizedException) {
//      Status status = new Status() {
//        @Override
//        public int getCode() {
//          return 40101;
//        }
//
//        @Override
//        public String getMessage() {
//          return "无权访问";
//        }
//      };
//      return restResponses.build2(status, null);
//    }
    return restResponses.error();
  }


}
