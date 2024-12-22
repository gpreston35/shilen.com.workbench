package com.shilen.app.workbench.controller;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shilen.app.workbench.Tokens;
import com.shilen.app.workbench.dao.FileStorageMapper;
import com.shilen.app.workbench.dao.NoteStorageMapper;
import com.shilen.app.workbench.dao.ToolCRMapper;
import com.shilen.app.workbench.helper.Utils;
import com.shilen.app.workbench.model.Note;
import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.ro.Search;
import com.shilen.app.workbench.model.tool.ChamberReamer;
import com.shilen.app.workbench.model.tool.ToolSearchForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("STATUS_LIST")
public class ToolCRController {
	
	private final int MODULE_ID = 1;

	
	@GetMapping("/tool/cr")   
	public String home(Model model) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		
	    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	ctx.refresh();
	 	
	 	ToolSearchForm searchForm = new ToolSearchForm();
	 	model.addAttribute("SEARCH_FORM", searchForm );
	 	
		ctx.close();
		
		return "tool/chamber_reamer_home";
	} 
	
	
	@SuppressWarnings("null")
	@GetMapping("/tool/cr/read")
	public String readChamberReamer(@RequestParam(name="id", required=true) int id, @RequestParam(name="message", required=false, defaultValue = "") String message, 
			HttpServletResponse response,  Model model ) throws Exception {
		
    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   	
 	    ToolCRMapper mapper = ctx.getBean( ToolCRMapper.class);
 	    FileStorageMapper fileMapper = ctx.getBean(FileStorageMapper.class);
 	    NoteStorageMapper noteMapper = ctx.getBean(NoteStorageMapper.class);
		
		ChamberReamer reamer = mapper.Read( id );
		
		reamer.setFiles( fileMapper.getList(Tokens.MODULE_CHAMBER_REAMER, id));
		reamer.setNotes( noteMapper.List( id , MODULE_ID) );
		
		List<PickList> statusList = mapper.GetStatusList(Tokens.TOOL_STATUS_DSET);	

		if (message != null & !message.isEmpty() )
			model.addAttribute("MESSAGE", message);
		
 	   	model.addAttribute("FORM", reamer );
 	   	model.addAttribute("STATUS_LIST", statusList );
 	   	
		ctx.close();
		
		return "tool/chamber_reamer";
		
	}
	
	@GetMapping("/tool/cr/new")
	 public String newChamberReamer(@ModelAttribute Search search,  Model model) {

    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   
 	    ToolCRMapper mapper = ctx.getBean( ToolCRMapper.class);
 	    
 	    List<PickList> statusList = mapper.GetStatusList(Tokens.TOOL_STATUS_DSET);
 	    
 	   	model.addAttribute("FORM", new ChamberReamer() );
 	    model.addAttribute("STATUS_LIST", statusList );
 	   
 	   	
		ctx.close();
		
		return "tool/chamber_reamer";
	 	  
	 }
	
	

	@PostMapping("/tool/cr/update")
	public ModelAndView updateChamberReamer(@Valid @ModelAttribute("FORM") ChamberReamer form, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, Model model,  HttpServletRequest request) {
		

    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
 	   	ctx.refresh();
 	   	
 	    ModelAndView modelAndView = new ModelAndView("redirect:/tool/cr/read");
 	    
 	   	
		if ( form.getDos_str().isEmpty() ) 
			form.setDos( null );
		else
			form.setDos( java.sql.Date.valueOf( form.getDos_str() ) );
			
		
		if ( form.getDom_str().isEmpty() )
			form.setDom( null );
		else
			form.setDom( java.sql.Date.valueOf( form.getDom_str() ) );
			
		if ( form.getEos_str().isEmpty() ) 
			form.setEos( null );
		else
			form.setEos( java.sql.Date.valueOf( form.getEos_str() ) );
		
		
 	   	ToolCRMapper mapper = ctx.getBean( ToolCRMapper.class);
 	    NoteStorageMapper noteMapper = ctx.getBean(NoteStorageMapper.class);
 	   	
 	   	if ( form.getId() == 0 ) {
 	 	   	form.setUpdated_by(request.getRemoteUser() );
 	 	   	form.setCreated_by( request.getRemoteUser() );
 	 	    mapper.Insert( form );
 	 	    mapper.updateToolIdentifier( "CR" + form.getId(), form.getId() );
 	 	    modelAndView.addObject("message", "Record successfully added.");
 	 	    
 	   	} else {
 	   		// update
 	 	   	form.setUpdated_by(request.getRemoteUser() );
 	 	   	mapper.Update(form);
	 	   	modelAndView.addObject("message", "Record successfully updated.");
 	   		
 	 	   	if ( !form.getNote().isEmpty() ) {
 	 	   		Note note = new Note();
 	 	   		note.setCreated_by( request.getRemoteUser() );
 	 	   		note.setNote( form.getNote() );
 	 	   		note.setModule_id(MODULE_ID);
 	 	   		note.setRecord_id(form.getId());
 	 	   		noteMapper.Insert(note);
 	 	   		
 	 	   	}
 	   	}
 	   
 	   	modelAndView.addObject("id", new Integer( form.getId() ) );
 	   	
 		ctx.close();
 	   	 		
		return modelAndView;

	}

	
	@RequestMapping("/tool/cr/search")
	 public String read(@ModelAttribute ToolSearchForm form, Model model) {
		 
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		
	    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	ctx.refresh();
	 	
	 	ToolCRMapper mapper = ctx.getBean( ToolCRMapper.class );
	 	
	 	model.addAttribute("RESULTS", mapper.Search( Utils.ifNull( form.getSearch_term() ).toUpperCase() ) );
	 	model.addAttribute("SEARCH_FORM", form);
	 	
		ctx.close();
		
		return "tool/chamber_reamer_home";
	 	
	
	}	

	
}
