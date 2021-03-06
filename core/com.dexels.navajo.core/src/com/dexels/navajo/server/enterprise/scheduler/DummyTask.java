package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.UserException;

public class DummyTask implements TaskInterface {

	private static final long serialVersionUID = 4518273204586299917L;
	private TriggerInterface trigger;
	private String workflowDefinition;

	public TaskInterface getInstance() {
		return new DummyTask();
	}

	public void setNavajo(Navajo n) {
		
	}

	public void setTrigger(String s) throws UserException {
		
	}

	public String getId() {
		return null;
	}

	public void setKeepRequestResponse(boolean keepRequestResponse) {
	}

	public void setPersisted(boolean b) {
	}

	public void run() {
		
	}

	public String getTriggerDescription() {
		return null;
	}

	public String getUsername() {
		return null;
	}

	public String getWebservice() {
		return null;
	}

	public boolean isActive() {
		return false;
	}

	public boolean isRunning() {
		return false;
	}

	public void runTask() {
		
	}

	@Override
	public void setTrigger(TriggerInterface trigger) {
		this.trigger = trigger;
	}

	@Override
	public TriggerInterface getTrigger() {
		return this.trigger;
	}

	@Override
	public String getWorkflowId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	@Override
	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	@Override
	public void setForceSync(boolean b) {
//		this.forceSync = b;
		
	}

	@Override
	public boolean isProxy() {
		return false;
	}

}
