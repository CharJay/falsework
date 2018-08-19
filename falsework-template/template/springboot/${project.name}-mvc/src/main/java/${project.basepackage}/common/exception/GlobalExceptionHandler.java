<#assign basepackage = project.basepackage>
package ${basepackage}.common.exception;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xm.core.utils.bean.RetMsg;
import com.xm.core.utils.helper.BusinessException;
import com.xm.core.utils.helper.CodeEnum;
import com.xm.core.utils.helper.WebAssertException;
import ${basepackage}.common.aop.CtrlAop;

@ControllerAdvice
class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CtrlAop.class); 
	
    public static final String DEFAULT_ERROR_VIEW = "common/error";

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName(DEFAULT_ERROR_VIEW);
//        return mav;
//    }
//    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RetMsg<Object>> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if(e instanceof BusinessException){
            logger.error("businessExceptionHandler", e);
            BusinessException ee = (BusinessException)e;
            return new ResponseEntity<>(new RetMsg<>(ee.getErrCode(), ee.getLocalizedMessage()), HttpStatus.OK);
        }else if(e instanceof WebAssertException){
            logger.error("webAssertExceptionHandler", e);
            WebAssertException wae = (WebAssertException)e;
            return new ResponseEntity<>(new RetMsg<>(wae.getErrCode(), wae.getLocalizedMessage()), HttpStatus.OK);
        }else{
            logger.error("exceptionHandler", e);
            CodeEnum.ERROR.getCode();
            return new ResponseEntity<>(new RetMsg<>(CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getDescr()), HttpStatus.OK);
        }
    }

}
