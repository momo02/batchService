package com.hiair.app.scheduler.model;

import java.util.TimeZone;

import io.swagger.annotations.ApiModelProperty;

public class QrtzCalendar {

	@ApiModelProperty(notes = "예외 일자 : 캘린더 명", example = "excludeCalendar")
	private String calendarName;
	
	@ApiModelProperty(notes = "예외 일자 : 크론 표현식", example = "0-0 0-10 0-10 ? * * *")
	private String calendarCronExpression;
	
	@ApiModelProperty(notes = "예외 일자 : 설명", example = "0분 부터 10분까지 예외 시간처리")
	private String calendarDescription;
	
	@ApiModelProperty(notes = "타임 존 : ASIA/SEOUL", hidden = true)
	private final TimeZone TIMEZONE = TimeZone.getTimeZone("Asia/Seoul");
	
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getCalendarCronExpression() {
		return calendarCronExpression;
	}
	public void setCalendarCronExpression(String calendarCronExpression) {
		this.calendarCronExpression = calendarCronExpression;
	}
	public String getCalendarDescription() {
		return calendarDescription;
	}
	public void setCalendarDescription(String calendarDescription) {
		this.calendarDescription = calendarDescription;
	}
	public TimeZone getTIMEZONE() {
		return TIMEZONE;
	}
	
	@Override
	public String toString() {
		return "CalendarVO [calendarName=" + calendarName + ", calendarCronExpression=" + calendarCronExpression
				+ ", calendarDescription=" + calendarDescription + ", TIMEZONE=" + TIMEZONE + "]";
	}
	
}
