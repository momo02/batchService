/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmFileController.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-10   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.app.cmm.file.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiair.app.cmm.file.service.CmmFileService;
import com.hiair.app.cmm.file.vo.CmmFileVO;
import com.hiair.app.sample.board.vo.BoardVO;

/**
 * <pre>
 * com.hiair.app.cmm.file.controller
 * _CmmFileController.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Controller
@RequestMapping("/cmm/file")
public class CmmFileController {
	
	private final static Logger logger = LoggerFactory.getLogger(CmmFileController.class);
	
	@Resource(name="cmmFileService")
	private CmmFileService service;
	
	
	@RequestMapping(value="/file.do")
	public String viewBoard(BoardVO vo, Model model) {
		return "/sample/file/ajaxFile.dash";
	}
	
	@RequestMapping(value = "/fileUpload.do")
	@ResponseBody
    public String fileUp(MultipartHttpServletRequest multi) {
         
        // 저장 경로 설정
        String root = multi.getSession().getServletContext().getRealPath("/");
        String path = root;
         
        String newFileName = ""; // 업로드 되는 파일명
         
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
        List<MultipartFile> mfList = multi.getFiles("file");
        System.out.println("파일 개수: " +mfList.size());
 
//        Iterator<String> files = multi.getFileNames();
//        while(files.hasNext()){
        for(MultipartFile mf:mfList){    

        	System.out.println("파라미터이름:"+mf.getName());
        	System.out.println("파일명:"+mf.getOriginalFilename());
        	System.out.println("파일사이즈:"+mf.getSize());
        	System.out.println("실제 경로: " +path);
            


            String fileName = mf.getOriginalFilename(); //파일명 얻기

//            String uploadFile = files.next();
                         
//            MultipartFile mFile = multi.getFile(uploadFile);
            
//            newFileName = System.currentTimeMillis()+"."
//                    +fileName.substring(fileName.lastIndexOf(".")+1);
            	try {
            		if(!mf.getOriginalFilename().equals("")) { 
            			mf.transferTo(new File(path+mf.getOriginalFilename()));
            		}
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        }
        return "ajaxUpload";
    }
	
	
	
	@RequestMapping(value="/kendoUpload.do")
	@ResponseBody
	public CmmFileVO kendoUpload(@RequestParam(name="files") List<MultipartFile> files, String metadata) throws Exception {
		System.out.println("asdf");
		CmmFileVO metaVO = null;
		
		if (metadata == null) {
			CmmFileVO metaIsNull = new CmmFileVO();
			return metaIsNull;
		}else {
			ObjectMapper om = new ObjectMapper();
			metaVO = om.readValue(metadata, CmmFileVO.class);
			logger.debug(metaVO.toString());
		}
		CmmFileVO result = service.fileSave(files, metaVO);
		
		return result;
	}
	
	@RequestMapping(value="/kendoDelete.do")
	@ResponseBody
	public Map<String,Object> fileRemove(int fileId) {
		logger.debug(""+fileId);
		service.removeFile(fileId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		return map;
	}
	
	@RequestMapping(value="/download.do")
	public void fileDownload(HttpServletResponse response,int fileId) throws Exception {
		logger.debug("### fileId : "+fileId);
		try {
			service.fileDownload(response, fileId);
		} catch (Exception e) {
			logger.error("IGONORE : "+e);
			response.sendError(9000, "not found file");
		}
	}
	
}
