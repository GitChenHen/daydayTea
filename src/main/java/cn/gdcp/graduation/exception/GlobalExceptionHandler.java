package cn.gdcp.graduation.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//指定全局异常捕获类
public class GlobalExceptionHandler {
    //创建springboot自带的logback日志对象
    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param resp
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)//捕获异常
    @ResponseBody//将响应的数据使用json数据格式返回
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        //打印错误日志
        log.error("fatal exception, a={}, b={}",e);
        return "违反了约束，多半是外键约束";
    }

}