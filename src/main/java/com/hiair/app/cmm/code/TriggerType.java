package com.hiair.app.cmm.code;

public enum TriggerType {
	SIMPLE("SIMPLE"), CRON("CRON");
	
	final private String name;
	
	private TriggerType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
