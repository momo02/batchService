package com.hiair.app.scheduler.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.calendar.CronCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.scheduler.model.QrtzCalendar;
import com.hiair.app.scheduler.model.QrtzJob;
import com.hiair.app.scheduler.model.QrtzTrigger;
import com.hiair.app.scheduler.service.SchedulerService;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController // @Controller + @ResponseBody
@RequestMapping(value = "/rest/schedule", method = RequestMethod.POST)
@Api(tags = "쿼츠 스케줄 컨트롤러")
public class SchedulerRestController {

	@Autowired
	private SchedulerService scheduleService;

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/start")
	public ResponseEntity<RestResponse> start() {
		RestResponse restResponse = new RestResponse();

		try {
			scheduleService.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/stop")
	public ResponseEntity<RestResponse> stop() {
		RestResponse restResponse = new RestResponse();

		try {
			scheduleService.stop();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/shutdown")
	public ResponseEntity<RestResponse> shutdown() {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	// @RequestMapping("/scheduleJob")
	// public ResponseEntity<RestResponse> scheduleJob(@RequestBody
	// ScheduleJobVO vo){
	// System.out.println("SCHEDULE JOB CONTROLLER");
	// RestResponse restResponse = new RestResponse();
	//
	// try {
	// if(null == vo.getTriggers()) {
	// scheduleService.addJob(vo.getJob());
	// }else if(null == vo.getJob()){
	// scheduleService.addTrigger(vo.getTriggers());
	// }else {
	// scheduleService.scheduleJob(vo);
	// }
	// } catch(ObjectAlreadyExistsException e) {
	//
	// if(e.getMessage().contains("Unable to store Job")){ //Job 중복
	// restResponse.setErrorCode("-1");
	// restResponse.setErrorMessage(e.getMessage());
	//
	// return new ResponseEntity<>(restResponse,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// }else if(e.getMessage().contains("Unable to store Trigger")){ //Trigger
	// 중복
	// restResponse.setErrorCode("-1");
	// restResponse.setErrorMessage(e.getMessage());
	//
	// return new ResponseEntity<>(restResponse,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	//
	// } catch (SchedulerException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// restResponse.setErrorCode("-1");
	// restResponse.setErrorMessage(e.getMessage());
	//
	// return new ResponseEntity<>(restResponse,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// restResponse.setErrorCode("-1");
	// restResponse.setErrorMessage(e.getMessage());
	//
	// return new ResponseEntity<>(restResponse,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// } catch (Exception e) {
	// restResponse.setErrorCode("-1");
	// restResponse.setErrorMessage(e.getMessage());
	//
	// return new ResponseEntity<>(restResponse,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	//
	// return new ResponseEntity<>(restResponse, HttpStatus.OK);
	// }

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/rescheduleJob")
	public ResponseEntity<RestResponse> rescheduleJob(@RequestBody QrtzTrigger vo) {
		RestResponse restResponse = new RestResponse();

		try {
			scheduleService.rescheduleJob(vo);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "작업 추가")
	@RequestMapping("/addJob")
	public ResponseEntity<RestResponse> addJob(@RequestBody QrtzJob vo) {
		RestResponse restResponse = new RestResponse();

		try {
			scheduleService.addJob(vo);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "작업 삭제")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/deleteJob")
	public ResponseEntity<RestResponse> deleteJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.deleteJob(jobName, jobGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/deleteJobAll")
	public ResponseEntity<RestResponse> deleteJobAll() {
		RestResponse restResponse = new RestResponse();
		try {

			scheduleService.deleteJobAll();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Job 중지 => 해당 Job의 모든 Trigger를 중지")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
	})
	@RequestMapping("/pauseJob")
	public ResponseEntity<RestResponse> pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.pauseJob(jobName, jobGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Trigger 중지")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/pauseTrigger")
	public ResponseEntity<RestResponse> pauseTrigger(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.pauseTrigger(triggerName, triggerGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 트리거 삭제")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/deleteTrigger")
	public ResponseEntity<RestResponse> deleteTrigger(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.deleteTrigger(triggerName, triggerGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/pauseAll")
	public ResponseEntity<RestResponse> pauseAll() {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.pauseAll();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Job 재시작 =>> 해당 Job의 모든 Trigger를 재시작", hidden = true)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
	})
	@RequestMapping("/resumeJob")
	public ResponseEntity<RestResponse> resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.resumeJob(jobName, jobGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Trigger 재시작")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/resumeTrigger")
	public ResponseEntity<RestResponse> resumeTrigger(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.resumeTrigger(triggerName, triggerGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/resumeAll")
	public ResponseEntity<RestResponse> resumeAll() {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.resumeAll();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = true)
	@RequestMapping("/selectScheduleAll")
	public ResponseEntity<RestResponse> selectScheduleAll() {
		RestResponse restResponse = new RestResponse();

		try {

			scheduleService.selectScheduleAll();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "addExcludedDate", notes = "예외 일자 추가", hidden = true)
	@RequestMapping("/addExcludedDate")
	public ResponseEntity<RestResponse> addExcludedDate(@RequestBody QrtzTrigger vo) {
		RestResponse restResponse = new RestResponse();

		try {
			scheduleService.rescheduleJob(vo);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "작업 리스트 조회(그룹)")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹명", required = false, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/getJobList")
	public ResponseEntity<RestResponse> getJobList(@RequestParam(name = "jobGroup", required = false) String jobGroup) {
		RestResponse restResponse = new RestResponse();
		List<QrtzJob> jobList = null;
		
		try {
			if (null == jobGroup) {
				jobList = scheduleService.getJobAll();
			} else {
				jobList = scheduleService.getJobsByGroup(jobGroup);
			}
			
			restResponse.getItems().add(jobList);
		} catch (Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 작업의 트리거 리스트 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
	})
	@RequestMapping("/getTriggerList")
	public ResponseEntity<RestResponse> getTriggerList(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		List<QrtzTrigger> triggerList = null;

		try {
			triggerList = scheduleService.getTriggersOfJob(jobName, jobGroup);
			restResponse.getItems().add(triggerList);
			
		} catch (Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			restResponse.getItems().add(triggerList);

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 작업에 트리거 추가")
	@RequestMapping("/addTrigger")
	public ResponseEntity<RestResponse> addTrigger(@RequestBody QrtzTrigger trigger) {
		RestResponse restResponse = new RestResponse();
		QrtzTrigger vo = null;
		try {
			vo = scheduleService.addTrigger(trigger);
			restResponse.getItems().add(vo);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "트리거의 세부정보")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/getTriggerDetail")
	public ResponseEntity<RestResponse> getTriggerDetail(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			QrtzTrigger triggerVO = scheduleService.getTriggerDetail(triggerName, triggerGroup);

			restResponse.getItems().add(triggerVO);

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "예외처리 리스트")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "캘린더 명", required = false, dataType = "string", paramType = "query"),
	})
	@RequestMapping("/getCalendarList")
	public ResponseEntity<RestResponse> getCalendarList(@RequestParam(name = "name", required = false) String name) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if (null == name){
				List<QrtzCalendar> calendarList = scheduleService.getCalendarList();
				restResponse.getItems().add(calendarList);
			}else{
				QrtzCalendar calendarVO = scheduleService.getCalendarDetail(name);
				restResponse.getItems().add(calendarVO);
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
	@RequestMapping("/addCalendar")
	public ResponseEntity<RestResponse> addCalendar(@RequestBody QrtzCalendar calendarVO) {
		RestResponse restResponse = new RestResponse();
		
		try {
			scheduleService.addCalendar(calendarVO);
			
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
	@RequestMapping("/deleteCalendar")
	public ResponseEntity<RestResponse> deleteCalendar(@RequestParam("calendarName") String calendarName) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.deleteCalendar(calendarName);

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
	@RequestMapping("/addCalendarToTrigger")
	public ResponseEntity<RestResponse> addCalendarToTrigger(@RequestParam("calendarName") String calendarName,
			@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		
		try {
			scheduleService.addCalendarToTrigger(calendarName, triggerName, triggerGroup);
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
	@RequestMapping("/removeCalendarFromTrigger")
	public ResponseEntity<RestResponse> removeCalendarFromTrigger(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		
		try {
			scheduleService.removeCalendarFromTrigger(triggerName, triggerGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
}
