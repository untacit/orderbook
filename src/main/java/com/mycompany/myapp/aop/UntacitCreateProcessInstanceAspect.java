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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
public class UntacitCreateProcessInstanceAspect {

    private final Logger log = LoggerFactory.getLogger(UntacitCreateProcessInstanceAspect.class);

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Around("@annotation(com.mycompany.myapp.annotation.UntacitCreateProcessInstance)")
    public Object handleAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UntacitCreateProcessInstance annotation = method.getAnnotation(UntacitCreateProcessInstance.class);
        String bpmnProcessDefinitionId = annotation.value();
        log.debug("Creating processInstance of {}", bpmnProcessDefinitionId);
        createProcessInstance(bpmnProcessDefinitionId, joinPoint.getArgs()[0]);

        return joinPoint.proceed();
    }


    private void createProcessInstance(String bpmnProcessDefinitionId, Object entity) {
        String businessKey = getProcessInstanceBusinessKey(entity);
        ProcessDefinition processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(bpmnProcessDefinitionId);
        ProcessInstance camundaProcessInstance = runtimeService.createProcessInstanceById(processDefinition.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariable("processInstance", entity)
            .setVariable("pi", entity)
            .execute();
        log.debug("ProcessInstance created: {}", camundaProcessInstance.getId());
        setCamundaDeploymentId(entity, processDefinition.getCamundaDeploymentId());
        setCamundaProcessDefinitionId(entity, processDefinition.getCamundaProcessDefinitionId());
        setCamundaProcessInstanceId(entity, camundaProcessInstance.getId());
    }

    private String getProcessInstanceBusinessKey(Object entity) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UntacitProcessInstanceBusinessKey.class)) {
                try {
                    field.setAccessible(true);
                    return (String) field.get(entity);
                } catch (IllegalAccessException e) {
                    log.error("Error retrieving field {}: {}", field, e);
                    return null;
                }
            }
        }
        return null;
    }

    private void setCamundaDeploymentId(Object entity, String deploymentId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UntacitDeploymentId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, deploymentId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

    private void setCamundaProcessDefinitionId(Object entity, String processDefinitionId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UntacitProcessDefinitionId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, processDefinitionId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

    private void setCamundaProcessInstanceId(Object entity, String processInstanceId) {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UntacitProcessInstanceId.class)) {
                try {
                    field.setAccessible(true);
                    field.set(entity, processInstanceId);
                } catch (IllegalAccessException e) {
                    log.error("Error setting field {}: {}", field, e);
                }
            }
        }
    }

}
