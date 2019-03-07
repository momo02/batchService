package com.hiair.app.scheduler.calendar.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hiair.app.scheduler.sys.util.SchedulerUtil;
import com.hiair.app.scheduler.trigger.model.QrtzTrigger;
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
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("calendar", calendarList);
			
			restResponse.setItems(map);
			
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
		@ApiImplicitParam(name = "name", value = "캘린더 명", required = true, dataType = "string", paramType = "query")
	})
	@RequestMapping("/detail")
	public ResponseEntity<RestResponse> detail(@RequestParam(name = "name") String name) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(SchedulerUtil.checkCalendarExists(name)) {
				QrtzCalendar qrtzCalendar = calendarService.detail(name);

				Map<String,Object> map = new HashMap<String,Object>();
				map.put("calendar", qrtzCalendar);
				
				restResponse.setItems(map);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 캘린더가 존재하지 않습니다. 다시 검색해 주세요.");
				return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "캘린더 추가")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cronExpression", value = "캘린더 크론표현식", required = false, dataType = "string", paramType = "query", defaultValue = "* * 0-23 ? * * *" ),
		@ApiImplicitParam(name = "description", value = "캘린더 설명", required = false, dataType = "string", paramType = "query", defaultValue = "정기 점검")
	})
	@RequestMapping("/add")
	public ResponseEntity<RestResponse> add(@RequestParam("cronExpression")String cronExpression, @RequestParam("description")String description) {
		RestResponse restResponse = new RestResponse();
		
		try {
			calendarService.add(cronExpression, description);
				
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
				return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "캘린더 - 트리거 리스트")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "calendarName", value = "캘린더 명", required = true, dataType = "string", paramType = "query", defaultValue = "yjCalendar"),
	})
	@RequestMapping("/triggerList")
	public ResponseEntity<RestResponse> triggerList(@RequestParam("calendarName") String calendarName) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(SchedulerUtil.checkCalendarExists(calendarName)) {
				List<QrtzTrigger> qrtzTriggerList = calendarService.triggerList(calendarName);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("trigger", qrtzTriggerList);
				
				restResponse.setItems(map);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 캘린더가 존재 하지 않습니다. 다시 입력해주세요.");
				return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "캘린더 - 트리거 업데이트")
	@RequestMapping("/updateTriggerCalendar")
	public ResponseEntity<RestResponse> updateTriggerCalendar(@RequestBody List<QrtzTrigger> qrtzTriggerList) {
		RestResponse restResponse = new RestResponse();
		
		try {
			for (QrtzTrigger qrtzTrigger : qrtzTriggerList) {
				if(qrtzTrigger.isTargetCalendar()) {
					calendarService.addToTrigger(qrtzTrigger);	
				} else {
					calendarService.deleteFromTrigger(qrtzTrigger.getTriggerKey());
				}
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
