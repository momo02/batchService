package com.hiair.app.schedule.web;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.schedule.service.ScheduleService;
import com.hiair.app.schedule.vo.CronTriggerVO;
import com.hiair.app.schedule.vo.JobDetailsVO;
import com.hiair.app.schedule.vo.SimpleTriggerVO;
import com.hiair.app.schedule.vo.TriggerVO;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController // @Controller + @ResponseBody
@RequestMapping(value = "/rest/schedule", method = RequestMethod.POST)
@Api(tags = "쿼츠 스케줄 컨트롤러")
public class ScheduleRestController {

	@Autowired
	private ScheduleService scheduleService;

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
	public ResponseEntity<RestResponse> rescheduleJob(@RequestBody TriggerVO vo) {
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

	@ApiOperation(value = "작업 추가", notes = "필수: jobName, jobGroup, jobClassName")
	@RequestMapping("/addJob")
	public ResponseEntity<RestResponse> addJob(@RequestBody JobDetailsVO vo) {
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

	@ApiOperation(value = "작업 삭제", notes = "required: jobName, jobGroup")
	@RequestMapping("/deleteJob")
	public ResponseEntity<RestResponse> deleteJob(@RequestBody JobDetailsVO vo) {
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

	@ApiOperation(value = "특정 Job 중지 => 해당 Job의 모든 Trigger를 중지", notes = "required: jobName, jobGroup")
	@RequestMapping("/pauseJob")
	public ResponseEntity<RestResponse> pauseJob(@RequestBody JobDetailsVO vo) {
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

	@ApiOperation(value = "특정 Trigger 중지", notes = "required = triggerName, triggerGroup")
	@RequestMapping("/pauseTrigger")
	public ResponseEntity<RestResponse> pauseTrigger(@RequestBody TriggerVO vo) {
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

	@ApiOperation(value = "특정 트리거 삭제", notes = "required = triggerName, triggerGroup")
	@RequestMapping("/deleteTrigger")
	public ResponseEntity<RestResponse> deleteTrigger(@RequestBody TriggerVO vo) {
		RestResponse restResponse = new RestResponse();
		try {
			scheduleService.deleteTrigger(vo);

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

	@ApiOperation(value = "특정 Job 재시작 => 해당 Job의 모든 Trigger를 재시작", hidden = true)
	@RequestMapping("/resumeJob")
	public ResponseEntity<RestResponse> resumeJob(@RequestBody JobDetailsVO vo) {
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

	@ApiOperation(value = "특정 Trigger 재시작", notes = "required = triggerName, triggerGroup")
	@RequestMapping("/resumeTrigger")
	public ResponseEntity<RestResponse> resumeTrigger(@RequestBody TriggerVO vo) {
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

	@ApiOperation(value = "작업 리스트 조회(그룹)", notes = "특정 작업그룹의 작업리스트 조회시 jobGroup 필수")
	@RequestMapping("/getJobList")
	public ResponseEntity<RestResponse> getJobList(@RequestBody(required = false) JobDetailsVO vo) {
		RestResponse restResponse = new RestResponse();
		List<JobDetailsVO> jobList = null;

		try {
			if (null == vo.getJobGroup()) {
				jobList = scheduleService.getJobAll();
			} else {
				jobList = scheduleService.getJobsByGroup(vo.getJobGroup());
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

	@ApiOperation(value = "특정 작업의 트리거 리스트 조회", notes = "required: jobName, jobGroup")
	@RequestMapping("/getTriggerList")
	public ResponseEntity<RestResponse> getTriggerList(@RequestBody(required = true) JobDetailsVO vo) {
		RestResponse restResponse = new RestResponse();
		List<TriggerVO> triggerList = null;

		try {
			triggerList = scheduleService.getTriggersOfJob(vo);
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

	@ApiOperation(value = "특정 작업에 트리거 추가", notes = "필수: jobName, jobGroup")
	@RequestMapping("/addTrigger")
	public ResponseEntity<RestResponse> addTrigger(@RequestBody TriggerVO trigger) {
		RestResponse restResponse = new RestResponse();
		TriggerVO vo = null;
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

	@ApiOperation(value = "트리거의 세부정보", notes = "필수: triggerName, triggerGroup")
	@RequestMapping("/getTriggerDetail")
	public ResponseEntity<RestResponse> getTriggerDetail(@RequestBody TriggerVO trigger) {
		RestResponse restResponse = new RestResponse();
		try {
			TriggerVO vo = scheduleService.getTriggerDetail(trigger);

			if (vo instanceof CronTriggerVO) {
				restResponse.getItems().add((CronTriggerVO) vo);
			} else if (vo instanceof SimpleTriggerVO) {
				restResponse.getItems().add((SimpleTriggerVO) vo);
			}

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "특정 트리거 변경", notes = "필수: triggerName, triggerGroup")
	@RequestMapping("/updateTrigger")
	public ResponseEntity<RestResponse> updateTrigger(@RequestBody TriggerVO trigger) {
		RestResponse restResponse = new RestResponse();
		try {
			TriggerVO vo = scheduleService.updateTrigger(trigger);
			
			if (vo instanceof CronTriggerVO) {
				restResponse.getItems().add((CronTriggerVO) vo);
			} else if (vo instanceof SimpleTriggerVO) {
				restResponse.getItems().add((SimpleTriggerVO) vo);
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
