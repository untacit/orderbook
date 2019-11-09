package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TasklistService {

    private final Logger log = LoggerFactory.getLogger(TasklistService.class);

    @Autowired
    private org.camunda.bpm.engine.TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all the tasks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        log.debug("Request to get all Tasks");
        List<org.camunda.bpm.engine.task.Task> camundaTasks = taskService.createTaskQuery().list();

        List<Task> tasks = new ArrayList<>();
        for (org.camunda.bpm.engine.task.Task camundaTask : camundaTasks) {
            tasks.add(camundaTask2Task(camundaTask));
        }

        return tasks;
    }


    /**
     * Get my tasks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Task> myTasks() {
        log.debug("Request to my Tasks");

        User userWithAuthorities = userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        List<String> authoritiesAsString = userWithAuthorities.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
        List<org.camunda.bpm.engine.task.Task> camundaTasks = taskService.createTaskQuery()
                                                                .taskCandidateGroupIn(authoritiesAsString)
                                                                .list();

        camundaTasks.addAll(taskService.createTaskQuery()
                                .withoutCandidateGroups()
                                .list());



        List<Task> tasks = new ArrayList<>();
        for (org.camunda.bpm.engine.task.Task camundaTask : camundaTasks) {
            tasks.add(camundaTask2Task(camundaTask));
        }

        return tasks;
    }

    private Task camundaTask2Task(org.camunda.bpm.engine.task.Task camundaTask) {
        Task task = new Task();
        task.setId(camundaTask.getId());
        task.setName(camundaTask.getName());
        task.setDescription(camundaTask.getDescription());
        task.setCreateDate(camundaTask.getCreateTime());
        task.setDueDate(camundaTask.getDueDate());
        task.setProcessDefinitionId(camundaTask.getProcessDefinitionId());
        task.setProcessInstanceId(camundaTask.getProcessInstanceId());
        task.setOwner(camundaTask.getOwner());
        task.setAssignee(camundaTask.getAssignee());
        task.setExecutionId(camundaTask.getExecutionId());
        task.setCreateTime(camundaTask.getCreateTime());
        task.setTaskDefinitionKey(camundaTask.getTaskDefinitionKey());
        task.setSuspended(camundaTask.isSuspended());
        return task;
    }

}