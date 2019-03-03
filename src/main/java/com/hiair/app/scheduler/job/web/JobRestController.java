package com.hiair.app.scheduler.job.web;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.scheduler.job.model.QrtzJob;
import com.hiair.app.scheduler.job.service.JobService;
import com.hiair.app.scheduler.util.SchedulerUtil;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rest/scheduler/job", method = RequestMethod.POST)
@Api(tags = "1. 작업 컨트롤러")
public class JobRestController {

	@Autowired
	private JobService jobService;

	@ApiOperation(value = "작업 리스트 조회")
	@RequestMapping("/list")
	public ResponseEntity<RestResponse> list() {
		RestResponse restResponse = new RestResponse();
		
		try {
			List<QrtzJob> jobList = jobService.list();
			restResponse.getItems().add(jobList);
			
		} catch (Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "그룹에 따른 작업 리스트 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹명", required = false, dataType = "string", paramType = "query", defaultValue = "IBE"),
	})
	@RequestMapping("/listByGroup")
	public ResponseEntity<RestResponse> listByGroup(@RequestParam(name = "jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();

		try {
			List<QrtzJob> jobList = jobService.listByGroup(jobGroup);
			restResponse.getItems().add(jobList);
			
		} catch (Exception e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "작업 추가")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "IBE"),
        @ApiImplicitParam(name = "description", value = "작업 설명", required = false, dataType = "string", paramType = "query", defaultValue = "쿼츠 작업 : job1"),
	})
	@RequestMapping("/add")
	public ResponseEntity<RestResponse> add(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup, @RequestParam("description") String description) {
		RestResponse restResponse = new RestResponse();
		try {
			jobService.add(jobName, jobGroup, description);

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ClassNotFoundException e) {
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
	@RequestMapping("/delete")
	public ResponseEntity<RestResponse> delete(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			if(SchedulerUtil.checkJobExists(jobName, jobGroup)){
				jobService.delete(jobName, jobGroup);	
			} else {
				restResponse.setErrorCode("-1");
				restResponse.setErrorMessage("해당 작업이 존재하지 않습니다. 다시 시도해주세요.");
			}
			

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "", hidden = false)
	@RequestMapping("/deleteAll")
	public ResponseEntity<RestResponse> deleteAll() {
		RestResponse restResponse = new RestResponse();
		try {
			jobService.deleteAll();

		} catch (SchedulerException e) {
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Job 중지 (해당 Job의 모든 Trigger를 중지)", hidden = true)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
	})
	@RequestMapping("/pause")
	public ResponseEntity<RestResponse> pause(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			jobService.pause(jobName, jobGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 Job 재시작 (해당 Job의 모든 Trigger를 재시작)", hidden = true)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "jobName", value = "작업 명", required = true, dataType = "string", paramType = "query", defaultValue = "job1"),
        @ApiImplicitParam(name = "jobGroup", value = "작업 그룹", required = true, dataType = "string", paramType = "query", defaultValue = "job1_Trigger"),
	})
	@RequestMapping("/resumeJob")
	public ResponseEntity<RestResponse> resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
		RestResponse restResponse = new RestResponse();
		try {
			jobService.resume(jobName, jobGroup);

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			restResponse.setErrorCode("-1");
			restResponse.setErrorMessage(e.getMessage());

			return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(restResponse, HttpStatus.OK);
	}

}
