package com.hiair.app.scheduler.calendar.web;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.scheduler.calendar.model.QrtzCalendar;
import com.hiair.app.scheduler.calendar.service.CalendarService;
import com.hiair.app.scheduler.util.SchedulerUtil;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController 
@RequestMapping(value = "/rest/scheduler/calendar", method = RequestMethod.POST)
@Api(tags = "3. 캘린더 컨트롤러")
public class CalendarRestController {

	@Autowired
	private CalendarService calendarService;
	
	@ApiOperation(value = "캘린더 리스트")
	@RequestMapping("/list")
	public ResponseEntity<RestResponse> list() {
		RestResponse restResponse = new RestResponse();
		
		try {
			List<QrtzCalendar> calendarList = calendarService.list();
			restResponse.getItems().add(calendarList);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "캘린더 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "캘린더 명", required = false, dataType = "string", paramType = "query"),
	})
	@RequestMapping("/detail")
	public ResponseEntity<RestResponse> detail(@RequestParam(name = "name") String name) {
		RestResponse restResponse = new RestResponse();
		
		try {
			QrtzCalendar qrtzCalendar = calendarService.detail(name);
			restResponse.getItems().add(qrtzCalendar);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "캘린더 추가")
	@RequestMapping("/add")
	public ResponseEntity<RestResponse> add(@RequestBody QrtzCalendar qrtzCalendar) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(!SchedulerUtil.checkCalendarExists(qrtzCalendar.getCalendarName())) {
				calendarService.add(qrtzCalendar);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("동일한 캘린더명이 존재합니다. 다시 입력해주세요.");
				return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "캘린더 제거")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "calendarName", value = "캘린더 명", required = true, dataType = "string", paramType = "query", defaultValue = "yjCalendar"),
	})
	@RequestMapping("/delete")
	public ResponseEntity<RestResponse> delete(@RequestParam("calendarName") String calendarName) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(SchedulerUtil.checkCalendarExists(calendarName)) {
				calendarService.delete(calendarName);			
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 캘린더가 존재 하지 않습니다. 다시 입력해주세요.");
			}


		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "트리거에 캘린더 추가")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "calendarName", value = "캘린더 명", required = true, dataType = "string", paramType = "query", defaultValue = "yjCalendar"),
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/addToTrigger")
	public ResponseEntity<RestResponse> addToTrigger(@RequestParam("calendarName") String calendarName,
			@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				calendarService.addToTrigger(calendarName, triggerName, triggerGroup);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("트리거가 존재하지 않습니다. 다시 확인해 주세요");
			}
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "트리거의 캘린더 제거")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
		@ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/deleteFromTrigger")
	public ResponseEntity<RestResponse> deleteFromTrigger(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				calendarService.deleteFromTrigger(triggerName, triggerGroup);	
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("트리거가 존재하지 않습니다. 다시 확인해 주세요");
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
}
