package com.mycompany.myapp.aop;

import com.mycompany.myapp.annotation.UntacitProcessInstanceBusinessKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.mycompany.myapp.repository.ProcessDefinitionRepository;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class UntacitTaskAspect {

    private final Logger log = LoggerFactory.getLogger(UntacitTaskAspect.class);

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Around("@annotation(com.mycompany.myapp.annotation.UntacitTask)")
    public Object handleUntacitTask(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String untacitTaskId = request.getParameter("untacitTaskId");
        String untacitTaskAction = request.getParameter("untacitTaskAction");

        /**
         * No untacitTaskId parameter provided. Thus, stop with this aspect. There is nothing to check or to do.
         */
        if (untacitTaskId == null) {
            return joinPoint.proceed();
        }

        /**
         * TODO: Checking whether it is allowed to execute this task
         */

        Object ret = joinPoint.proceed();

        if ("concludeTask".equals(untacitTaskAction)) {
            concludeTask(untacitTaskId, joinPoint.getArgs()[0]);
        }

        return ret;
    }

    private void concludeTask(String untacitTaskId, Object processInstance) {
        log.debug("Concluding taskId: ", untacitTaskId);

        Map<String, Object> params = new HashMap<>();
        params.put("processInstance", processInstance);
        params.put("pi", processInstance);
        
        taskService.complete(untacitTaskId, params);
    }
}
