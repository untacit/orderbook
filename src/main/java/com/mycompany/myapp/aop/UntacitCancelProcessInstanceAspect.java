package com.mycompany.myapp.aop;

import com.mycompany.myapp.annotation.*;
import com.mycompany.myapp.domain.ProcessDefinition;
import com.mycompany.myapp.repository.ProcessDefinitionRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
public class UntacitCancelProcessInstanceAspect {

    private final Logger log = LoggerFactory.getLogger(UntacitCancelProcessInstanceAspect.class);

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Around("@annotation(com.mycompany.myapp.annotation.UntacitCancelProcessInstance)")
    public Object handleAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UntacitCreateProcessInstance annotation = method.getAnnotation(UntacitCreateProcessInstance.class);
        String processDefinitionIdentifier = annotation.value();
        log.debug("Creating processInstance of {}", processDefinitionIdentifier);
        cancelProcessInstance(processDefinitionIdentifier, joinPoint.getArgs()[0]);

        return joinPoint.proceed();
    }


    private void cancelProcessInstance(String processDefinitionIdentifier, Object entityId) {
        //TODO: cancel a process instance by a processDefinitionIdentifier and a entityId
        //log.debug("ProcessInstance canceled: {}", processInstanceId);
    }

}
