package cn.xgq.financialsys.annotation;

import cn.xgq.financialsys.domain.Log;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.service.inter.LogSer;
import cn.xgq.financialsys.util.IpUtils;
import cn.xgq.financialsys.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
@SuppressWarnings("all")
public class SystemLogAspect {
    //注入Service用于把日志保存数据库，实际项目入库采用队列做异步
    @Resource
    private LogSer logSer;
    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
    //Service层切点
    @Pointcut("@annotation(cn.xgq.financialsys.annotation.SystemServiceLog)")
    public void serviceAspect(){
    }

    //Controller层切点
    @Pointcut("@annotation(cn.xgq.financialsys.annotation.SystemControllerLog)")
    public void controllerAspect(){
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        User user = (User) session.getAttribute("admin");

        String ip = IpUtils.getIpAddr(request);

        try {
            //*========控制台输出=========*//
            System.out.println("==============前置通知开始==============");
            System.out.println("请求方法" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()));
            System.out.println("方法描述：" + getControllerMethodDescription(joinPoint));
            System.out.println("请求人："+user.getUsername());
            System.out.println("请求ip："+ip);

            //*========数据库日志=========*//
            Log log = new Log();
            log.setLogDesc(getControllerMethodDescription(joinPoint));
            log.setLogKey((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()));
            log.setIpAddress(ip);
            log.setUsername(user.getUsername());
            log.setCreateTime(new Date());
            log.setUpdateTime(new Date());
            log.setType(0);
            //保存数据库
            if (logSer.addLog(log)){
                System.out.println("添加日志成功！");
            }else{
                System.out.println("添加日志失败！");
            }

        }catch (Exception e){
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息：{}",e.getMessage());
        }
    }

    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();//目标方法名
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method:methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length==arguments.length){
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
    /**
     * @Description  异常通知 用于拦截service层记录异常日志
     * @date 2018年9月3日 下午5:43
     */
    @AfterThrowing(pointcut = "serviceAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        User user = (User) session.getAttribute("admin");
        //获取请求ip
        String ip = IpUtils.getIpAddr(request);
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs()!=null&&joinPoint.getArgs().length>0){
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params+= JsonUtils.objectToJson(joinPoint.getArgs()[i])+";";
            }
        }
        try{
            /*========控制台输出=========*/
            System.out.println("=====异常通知开始=====");
            System.out.println("异常代码:" + e.getClass().getName());
            System.out.println("异常信息:" + e.getMessage());
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + getServiceMethodDescription(joinPoint));
            System.out.println("请求人:" + user.getUsername());
            System.out.println("请求IP:" + ip);
            System.out.println("请求参数:" + params);
            /*==========数据库日志=========*/
            Log log = new Log();
            log.setLogDesc(getControllerMethodDescription(joinPoint));
            log.setLogKey((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setIpAddress(ip);
            log.setUsername(user.getUsername());
            log.setUpdateTime(new Date());
            log.setUpdateTime(new Date());
            log.setType(0);
            //保存数据库
            if (logSer.addLog(log)){
                System.out.println("添加日志成功！");
            }else{
                System.out.println("添加日志失败！");
            }
        }catch (Exception ex){
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
    }


    /**
     * @Description  获取注解中对方法的描述信息 用于service层注解
     * @date 2018年9月3日 下午5:05
     */
    public static String getServiceMethodDescription(JoinPoint joinPoint)throws Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method:methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length==arguments.length){
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
