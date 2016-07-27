/**
* TODO
* @Project: esframe
* @Title: ActivitiUtils.java
* @Package com.lmstudio.framework.activiti
* @author jason.liu
* @Date 2015年12月24日 上午9:19:52
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

/**
 * TODO
 * 
 * @ClassName: ActivitiUtils
 * @author jason.liu
 */
public class ActivitiUtils {

	private static Logger log = Logger.getLogger(ActivitiUtils.class);

	static ProcessEngine processEngine = null;
	static ManagementService managementService = null;
	static FormService formService = null;

	static {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		managementService = processEngine.getManagementService();
		formService = processEngine.getFormService();
		
	}

	/**
	 * TODO 流程部署
	 * 
	 * @Title: deployProcess
	 * @param fileUrl
	 */
	public void deployProcess(String fileUrl) {

		RepositoryService repositoryService = processEngine.getRepositoryService();

		log.info("before,Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
		repositoryService.createDeployment().addClasspathResource("flow/VacationRequest.bpmn20.xml").deploy();

		log.info("after,Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());

	}

	public void startProInstance() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");

		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);

		// Verify that we started a new process instance
		log.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());

	}

	public void completeTask() {
		// Fetch all tasks for the management group
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		for (Task task : tasks) {
			log.info("Task available: " + task.getName());
		}

		Task task = tasks.get(0);

		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApprove， d", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);//将本次任务中填报的数据，让下一个任务执行人可以查询到，，，，如果引用一个外部表单ID，可以设置为流程实例的变量

		// recommend
		List<Task> taskss = taskService.createNativeTaskQuery().sql(
				"SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName}")
				.parameter("taskName", "gonzoTask").list();

		long count = taskService.createNativeTaskQuery()
				.sql("SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T1, "
						+ managementService.getTableName(VariableInstanceEntity.class)
						+ " V1 WHERE V1.TASK_ID_ = T1.ID_")
				.count();
		//form
		TaskFormData data =  formService.getTaskFormData(task.getId());
		
		
		//任务节点的执行人，可以在节点上添加监听器，来动态设置节点由谁执行。
	}
}
