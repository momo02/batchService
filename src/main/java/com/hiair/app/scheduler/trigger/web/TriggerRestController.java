package com.hiair.app.scheduler.trigger.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.scheduler.sys.util.SchedulerUtil;
import com.hiair.app.scheduler.trigger.model.QrtzTrigger;
import com.hiair.app.scheduler.trigger.service.TriggerService;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rest/scheduler/trigger", method = RequestMethod.POST)
@Api(tags = "2. 트리거 컨트롤러")
public class TriggerRestController {

	@Autowired
	private TriggerService triggerService;
	
	
	@ApiOperation(value = "특정 작업의 트리거 리스트 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/list")
	public ResponseEntity<RestResponse> list(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();

		try {
			List<QrtzTrigger> triggerList = triggerService.list(jobName, jobGroup);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("trigger", triggerList);
			
			restResponse.getItems().add(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "트리거 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "triggerName", value = "트리거 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
        @ApiImplicitParam(name = "triggerGroup", value = "트리거 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/detail")
	public ResponseEntity<RestResponse> detail(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				QrtzTrigger qrtzTrigger = triggerService.detail(triggerName, triggerGroup);

				Map<String,Object> map = new HashMap<String,Object>();
				map.put("trigger", qrtzTrigger);

				restResponse.getItems().add(map);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 트리거가 없습니다. 다시 검색해 주세요.");
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
	
	@ApiOperation(value = "특정 작업에 트리거 추가")
	@RequestMapping("/add")
	public ResponseEntity<RestResponse> add(@RequestBody QrtzTrigger qrtzTrigger) {
		RestResponse restResponse = new RestResponse();
		
		try {
			if(CronExpression.isValidExpression(qrtzTrigger.getCronExpression())) {
				triggerService.add(qrtzTrigger);
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("크론 표현식이 잘못되었습니다.");
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ParseException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
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
	@RequestMapping("/delete")
	public ResponseEntity<RestResponse> delete(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				triggerService.delete(triggerName, triggerGroup);	
				
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 트리거가 존재하지 않습니다.");
			}

		} catch (SchedulerException e) {
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
	@RequestMapping("/resume")
	public ResponseEntity<RestResponse> resume(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				triggerService.resume(triggerName, triggerGroup);	
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 트리거가 존재하지 않습니다.");
			}
			

		} catch (SchedulerException e) {
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
	@RequestMapping("/pause")
	public ResponseEntity<RestResponse> pause(@RequestParam("triggerName") String triggerName, @RequestParam("triggerGroup") String triggerGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			if(SchedulerUtil.checkTriggerExists(triggerName, triggerGroup)) {
				triggerService.pause(triggerName, triggerGroup);	
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 트리거가 존재하지 않습니다.");
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
