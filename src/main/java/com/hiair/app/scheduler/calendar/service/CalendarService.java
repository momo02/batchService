package com.hiair.app.scheduler.calendar.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.hiair.app.scheduler.calendar.model.QrtzCalendar;
import com.hiair.app.scheduler.trigger.model.QrtzTrigger;

public interface CalendarService {

	public List<QrtzCalendar> list() throws SchedulerException;

	public QrtzCalendar detail(String name) throws SchedulerException;

	public List<QrtzTrigger> triggerList(String calendarName) throws SchedulerException;

	public void add(QrtzCalendar calendarVO) throws ParseException, SchedulerException;

	public void delete(String calendarName) throws SchedulerException;

	public void addToTrigger(String calendarName, String triggerName, String triggerGroup) throws SchedulerException;

	public void deleteFromTrigger(String triggerName, String triggerGroup) throws SchedulerException;

}
