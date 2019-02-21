package com.hiair.app.schedule.web;

import java.text.ParseException;
import java.util.List;

import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.schedule.service.ScheduleService;
import com.hiair.app.schedule.vo.JobDetailsVO;
import com.hiair.app.schedule.vo.ScheduleJobVO;
import com.hiair.app.schedule.vo.TriggerVO;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController  //@Controller + @ResponseBody
@RequestMapping(value="/rest/schedule", method = RequestMethod.POST)
@Api(tags = "쿼츠 스케줄 컨트롤러")
public class ScheduleRestController {
	
	@Autowired
	private ScheduleService scheduleService;
	

	@RequestMapping("/start")
	public ResponseEntity<RestResponse> start(){
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
	
	@RequestMapping("/stop")
	public ResponseEntity<RestResponse> stop(){
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
	
	
	@RequestMapping("/shutdown")
	public ResponseEntity<RestResponse> shutdown(){
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
	
	@RequestMapping("/scheduleJob")
	public  ResponseEntity<RestResponse> scheduleJob(@RequestBody ScheduleJobVO vo){
		RestResponse restResponse = new RestResponse();
		
		try {
			if(null == vo.getTriggers()) {
				scheduleService.addJob(vo.getJob());
			}else if(null == vo.getJob()){
				scheduleService.addTrigger(vo.getTriggers());
			}else {
				scheduleService.scheduleJob(vo);
			}
		} catch(ObjectAlreadyExistsException e) {
			
			if(e.getMessage().contains("Unable to store Job")){ //Job 중복
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage(e.getMessage());
				
				return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}else if(e.getMessage().contains("Unable to store Trigger")){ //Trigger 중복
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage(e.getMessage());
				
				return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
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
		} catch (Exception e) {
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/rescheduleJob")
	public ResponseEntity<RestResponse> rescheduleJob(@RequestBody TriggerVO vo){
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
	
	@RequestMapping("/deleteJob")
	public ResponseEntity<RestResponse> deleteJob(@RequestBody JobDetailsVO vo){
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.deleteJob(vo);
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/deleteJobAll")
	public ResponseEntity<RestResponse> deleteJobAll(){
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
	
	@RequestMapping("/pauseJob")
	public ResponseEntity<RestResponse> pauseJob(@RequestBody JobDetailsVO vo){
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.pauseJob(vo);
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/pauseTrigger")
	public ResponseEntity<RestResponse> pauseTrigger(@RequestBody TriggerVO vo){
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.pauseTrigger(vo);
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/pauseAll")
	public ResponseEntity<RestResponse> pauseAll(){
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
	
	@RequestMapping("/resumeJob")
	public ResponseEntity<RestResponse> resumeJob(@RequestBody JobDetailsVO vo){
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.resumeJob(vo);
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/resumeTrigger")
	public ResponseEntity<RestResponse> resumeTrigger(@RequestBody TriggerVO vo){
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.resumeTrigger(vo);
		
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/resumeAll")
	public ResponseEntity<RestResponse> resumeAll(){
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
	
	@RequestMapping("/selectScheduleAll")
	public ResponseEntity<RestResponse> selectScheduleAll(){
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



	@ApiOperation(value="addExcludedDate", notes = "예외 일자 추가")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "jobName", value = "job 이름", required = true, dataType = "string", paramType = "paramType"),
    	@ApiImplicitParam(name = "calendarName", value = "calendar 이름", required = false, dataType = "string", paramType = "paramType"),
	})
	@RequestMapping("/addExcludedDate")
	public ResponseEntity<RestResponse> addExcludedDate(@RequestBody TriggerVO vo) {
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
	
	@RequestMapping("/getJobList")
	public ResponseEntity<RestResponse> getJobList(@RequestBody(required=false)JobDetailsVO vo){ 
		RestResponse restResponse = new RestResponse();
		List<JobDetailsVO> jobList = null;
		
		try {
			if(null == vo) {
				jobList = scheduleService.getJobAll();
			}else {
			    jobList = scheduleService.getJobsByGroup(vo.getJobGroup());
			}
		} catch(Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			restResponse.getItems().add(jobList);
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		restResponse.getItems().add(jobList);
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping("/getTriggerList")
	public ResponseEntity<RestResponse> getTriggerList(@RequestBody(required=true)JobDetailsVO vo){ 
		RestResponse restResponse = new RestResponse();
		List<TriggerVO> triggerList = null;
		
		try {
			triggerList = scheduleService.getTriggersOfJob(vo);
		} catch(Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			restResponse.getItems().add(triggerList);
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		restResponse.getItems().add(triggerList);
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
//	@RequestMapping("/addTrigger")
//	public void addTrigger(@RequestBody Set<TriggerVO> triggers) {
//		try {
//			scheduleService.addTrigger(triggers);
//		} catch (SchedulerException e) {
//			// TODOJ Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
