package com.hiair.app.sample.fileupload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiair.app.sample.fileupload.vo.SampleFileVO;

@Controller
@RequestMapping(value="/sample/file")
public class FileController {
	
	
	@Value("${file.path.save}")
	private String rootPath;
	
	@RequestMapping(value="/kendoFile.do")
	public String viewKendoFile() {
		return "/sample/file/kendoFile.dash";
	}
	@RequestMapping(value="/ajaxFile.do")
	public String viewAjaxFile() {
		return "/sample/file/ajaxFile.dash";
	}
	
	@RequestMapping(value="/upload")
	@ResponseBody
	public SampleFileVO fileUpload(@RequestParam List<MultipartFile> files, String metadata) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		long totalChunks = 0;
		long chunkIndex = 0;
		String uploadUid = "";
		String fileName = "";
		// map json to student

		if (metadata == null) {
			SampleFileVO metaIsNull = new SampleFileVO();
			return metaIsNull;
		}

		JsonNode rootNode = mapper.readTree(metadata);
		totalChunks = rootNode.path("totalChunks").longValue();
		chunkIndex = rootNode.path("chunkIndex").longValue();
		uploadUid = rootNode.path("uploadUid").textValue();
		fileName = rootNode.path("fileName").textValue();

//		OutputStream output = null;
		// Save the files

		for (MultipartFile file : files) {
			byte[] bytes = file.getBytes();
			File dir = new File(rootPath);
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile, true));
			stream.write(bytes);
			stream.close();
		}

		SampleFileVO resultVO = new SampleFileVO();
		resultVO.setUploaded(totalChunks - 1 <= chunkIndex);
		resultVO.setFileUid(uploadUid);
		resultVO.setFileId(82821004);
		return resultVO;
	}
	
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public SampleFileVO fileRemove(SampleFileVO vo) {
//		System.out.println(fileNames.length);
		if(vo != null) {
			System.out.println(vo.toString());
			
		}else {
			System.out.println("vo is null");
		}
//		for(String v : vo) {
//			System.out.println(v);
//		}
//		for(String fileName : fileNames) {
//			System.out.println("### fileName : "+fileName);
//			File file = new File(rootPath+File.separator+fileName);
//			System.out.println(file.getName());
//			file.delete();
//		}
		// {"uid":null,"uploaded":true,"fileUid":"679259ce-b328-4400-9bcf-e80860b3c84f"}
		SampleFileVO reulstVO = new SampleFileVO();
		return reulstVO;
	}
	
}
