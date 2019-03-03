package com.hiair.app.scheduler.web;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hiair.app.scheduler.service.SchedulerService;
import com.hiair.cmm.model.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController // @Controller + @ResponseBody
@RequestMapping(value = "/rest/scheduler", method = RequestMethod.POST)
@Api(tags = "0. 쿼츠 스케줄 컨트롤러")
public class SchedulerRestController {

	@Autowired
	private SchedulerService scheduleService;

	@ApiOperation(value = "스케줄러 시작(재시작)")
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

	@ApiOperation(value = "스케줄러 중지(일시중지)")
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


}
