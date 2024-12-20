package com.shilen.app.workbench.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.shilen.app.workbench.dao.FileStorageMapper;
import com.shilen.app.workbench.helper.AjaxResponseBody;
import com.shilen.app.workbench.model.FileMeta;
import com.shilen.app.workbench.model.FileStorage;
import com.shilen.app.workbench.model.Referrer;

import jakarta.activation.MimetypesFileTypeMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin() 
@SessionAttributes("REFERRER")
public class FileUploadController {
	
	
	@GetMapping("/delete-file/{id}")
    public ResponseEntity<?> delete(@PathVariable int id ) {
    	
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		AjaxResponseBody result = new AjaxResponseBody();

		FileStorageMapper mapper = ctx.getBean(FileStorageMapper.class);

		mapper.delete(id);

		ctx.close();

		result.setMsg("success");
		return ResponseEntity.ok(result);
    }
	
	
	
	
	@RequestMapping(value = "/list-files/module/{module_id}/record/{record_id}", method = RequestMethod.GET,
			  produces = MediaType.APPLICATION_JSON_VALUE ) 
	public @ResponseBody List<FileMeta> list( @PathVariable int module_id, @PathVariable int record_id ) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();		
		
		FileStorageMapper mapper = ctx.getBean(FileStorageMapper.class);
		
		List<FileMeta> list = mapper.getList( module_id, record_id );

		ctx.close();
		
		return list;

	}
	
	

	@RequestMapping(value = "/manage-files", method = RequestMethod.GET)
	public String fileUpload(Model model, final HttpServletRequest request, @RequestParam(name="module_record_id", required=true) int module_record_id,
			@RequestParam(name="referring_module_id", required=true) int referring_module_id) {
		
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();
		
		
		Referrer referrer = new Referrer( module_record_id,request.getHeader("referer"), referring_module_id );	
		model.addAttribute("REFERRER", referrer );
		
		//FileStorageMapper mapper = ctx.getBean(FileStorageMapper.class);
		

		model.addAttribute("MODULE_ID", referring_module_id);
		model.addAttribute("RECORD_ID", module_record_id);
		//model.addAttribute("FILE_LIST", mapper.getList(referring_module_id , module_record_id) );

		return "fileupload";

	}
	
	@RequestMapping(value = "/get-file", method = RequestMethod.GET)
	public @ResponseBody byte[] getFile( @RequestParam(name="id", required=true) int id, HttpServletResponse response ) throws IOException {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		FileStorageMapper mapper = ctx.getBean(FileStorageMapper.class);

		FileStorage fileStorage = mapper.getFile(id);
		
	    MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

	    response.setContentType(fileTypeMap.getContentType(fileStorage.getFile_name()));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileStorage.getFile_name() + "\"");
		
		
		ctx.close();

		return fileStorage.getFile_blob();

	}	
	
	
	 @PostMapping("/store-files")
	 public String updateQuality(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		  ctx.refresh();
		  
	 	  FileStorageMapper mapper = ctx.getBean( FileStorageMapper.class);
	 	    		  
		  Referrer referrer = (Referrer)request.getSession().getAttribute("REFERRER");

          Arrays.asList(files).stream().forEach(file -> {
              
        	  byte[] bytes = new byte[0];
              
              try {
            	  
            	  FileStorage fileStorage = new FileStorage();
            	  
                  bytes = file.getBytes();
                  fileStorage.setFile_blob(bytes);
                  mapper.store(fileStorage);
                  
                  FileMeta fileMeta = new FileMeta();
                  fileMeta.setFile_storage_id( fileStorage.getId() );
                  fileMeta.setFile_name(  file.getOriginalFilename() );
                  fileMeta.setFile_size( bytes.length );
                  fileMeta.setUploaded_by( request.getRemoteUser() );
                  fileMeta.setModule_id( referrer.getReferring_module_id() );
                  fileMeta.setModule_record_id( referrer.getReferrer_module_record_id() );
                  mapper.storeFileMeta(fileMeta);
                  
                  System.out.println( file.getOriginalFilename() );
                  
                  
              } catch (Exception e) {

            	  e.printStackTrace();
            	  
              }
          });		  
		  
	 	  ctx.close();

	 	  return "fileupload";
	 	  
	 }
	 
		@RequestMapping(value = "/download", method = RequestMethod.GET)
		public ResponseEntity getFile( @RequestParam(name="id", required=true) int id )  {
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
			ctx.refresh();

			FileStorageMapper mapper = ctx.getBean(FileStorageMapper.class);

			FileStorage fileStorage = mapper.getFile(id);
			
		    MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();


			ctx.close();

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(fileTypeMap.getContentType(fileStorage.getFile_name() ) ) )
					.header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=\"" + fileStorage.getFile_name() + "\"")
					.body( fileStorage.getFile_blob() );

		}	
		

}
