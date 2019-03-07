package com.hiair.app.scheduler.calendar.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

import com.hiair.app.scheduler.calendar.model.QrtzCalendar;
import com.hiair.app.scheduler.trigger.model.QrtzTrigger;

public interface CalendarService {

	public List<QrtzCalendar> list() throws SchedulerException;

	public QrtzCalendar detail(String name) throws SchedulerException;

	public List<QrtzTrigger> triggerList(String calendarName) throws SchedulerException;

	public void add(String calendarCronExpression, String calendarDescription) throws ParseException, SchedulerException;

	public void delete(String calendarName) throws SchedulerException;

	public void addToTrigger(QrtzTrigger qrtzTrigger) throws SchedulerException;

	public void deleteFromTrigger(TriggerKey triggerKey) throws SchedulerException;


	

}
