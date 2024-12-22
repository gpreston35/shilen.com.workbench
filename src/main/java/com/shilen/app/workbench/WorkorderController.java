package com.shilen.app.workbench;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.WorkOrderMapper;
import com.shilen.app.workbench.helper.AjaxResponseBody;
import com.shilen.app.workbench.model.PickList;
import com.shilen.app.workbench.model.wo.Search;
import com.shilen.app.workbench.model.wo.SearchResults;
import com.shilen.app.workbench.model.wo.WoNote;
import com.shilen.app.workbench.model.wo.WoQty;
import com.shilen.app.workbench.model.wo.WoQuality;
import com.shilen.app.workbench.model.wo.WoQualityRecords;
import com.shilen.app.workbench.model.wo.WoScrap;
import com.shilen.app.workbench.model.wo.WoToolHistory;
import com.shilen.app.workbench.model.wo.WorkOrder;
import com.shilen.app.workbench.out.WorkOrderPdf;


@Controller
@CrossOrigin() 
public class WorkorderController {
	
	 @GetMapping("/wo/test")
	 public String wo_test() {

		//  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		  //  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 //	ctx.refresh();
	 	   
	 	 //   WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	    
	 	 //   SearchResults wo = mapper.getShortDetail(id);
	 	//    model.addAttribute( "WO", wo );
	 	 //   model.addAttribute("WONOTE", new WoNote() );
	 	    
	 	 //   ctx.close();
	 	    
	 	    return "wo/workorder_tool";
	 }
	 
	
	@RequestMapping(value= "/wo/getByWoScrapId/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody List<WoScrap> getByIDjson(@PathVariable("id") int id)  {
			
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	   
	 	   	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	   	
	 	   //	WoQty woqty = mapper.getWoQty(id);
	 	   	
	 	   	List<WoScrap> woscraplist = mapper.getByWoScrapId(id);
	 	   	
	 	   	ctx.close();

	 	   	return woscraplist;
	
	}
	
	@RequestMapping(value= "/wo/getQualityCounts/json/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody WoQuality getQualityCounts(@PathVariable("id") int id)  {
			
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	   
	 	   	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	   	
	 	   	WoQty woqty = mapper.getWoQty(id);
	 	   	
	 	   	WoQuality qualityRecords = mapper.getQualityCounts(woqty);
	 	   	
	 	   	ctx.close();

	 	   	return qualityRecords;
	
	}
	
	 @GetMapping("/wo/short")
	 public String wo_short(@RequestParam(name="id", required=true) int id, Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
	 	   
	 	    WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	    
	 	    SearchResults wo = mapper.getShortDetail(id);
	 	    model.addAttribute( "WO", wo );
	 	    model.addAttribute("WONOTE", new WoNote() );
	 	    
	 	    ctx.close();
	 	    
	 	    return "wo/workorder_short";
	 }
	 
	 
	 @PostMapping("/wo/short/update")
	 public ResponseEntity<?> wo_short_update(@ModelAttribute WorkOrder wo,  Model model) {

		  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		  
		  ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		  ctx.refresh();
		  AjaxResponseBody result = new AjaxResponseBody();
	 	   
	 	  WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	  mapper.insertWoNote(wo);
	 	  
	 	  
	 	  ctx.close();
	 	    
	 	  result.setMsg("success");
	 	  return ResponseEntity.ok(result);
	 	  
	 }
	

		
		@GetMapping("/wo/home")   
		public String home(Model model) {
			
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
		 	   
		 //	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	LookupMapper lkMapper = ctx.getBean( LookupMapper.class );
		 	
			List<PickList> calibers = lkMapper.getCalibers();
			List<PickList> status = lkMapper.getStatuses();
				
		//	List<PickList> calibers = mapper.getCaliberList();
		//	List<PickList> status = mapper.getStatusList();
	 		
			model.addAttribute("RESULTS",new ArrayList<SearchResults>());
			model.addAttribute("FORM",new Search() );
			model.addAttribute("CALIBERS", calibers);
			model.addAttribute("STATUS", status);
			
			ctx.close();
			
			return "wo/workorder_home";
		} 
		
		
		@GetMapping("/wo/scrap/delete")
	    public ResponseEntity<?> deleteWoScrap(@RequestParam(name="id", required=true) int id, Model model) {
	    	
	    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
	 	   	ctx.refresh();
	 	    AjaxResponseBody result = new AjaxResponseBody();
	 	   
	 	   	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
	 	   	
	 	   	mapper.deleteWoScrap(id);
	 	   	
	 	   	ctx.close();
	    	
	 	    result.setMsg("success");
	 	    return ResponseEntity.ok(result);
	    }
		
		
		  @PostMapping("/wo/scrapreason_insert")
		  public ResponseEntity<?> insertWoScrap(@ModelAttribute WoScrap woscrap, Model model) {
		    	
		    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		 	   	ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	   	ctx.refresh();
		 	    AjaxResponseBody result = new AjaxResponseBody();
		 	   
		 	   	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	   	mapper.insertWoScrap(woscrap);
		 	   	
		 	   	ctx.close();
		 	   	
		 	   	result.setMsg("success");
		 	    return ResponseEntity.ok(result);
		 	   	
		  }
		
		
		@RequestMapping("/wo/quality_update")
		 public String updateQuality(@ModelAttribute WoQualityRecords wqrs,  Model model) {
			 		 
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
		 	
		 	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	
		 	for ( WoQuality wqr : wqrs.getQuality_records() )
		 		mapper.updateWoQuality(wqr);
		 		
		 	model.addAttribute("FORM", wqrs );	
		 	
	 	    ctx.close();
	 	    
	 	    return "wo/workorder_quality";
		}
		

		 @RequestMapping("/wo/update")
		 public String update(@ModelAttribute("workorder") @Valid WorkOrder wo,  BindingResult result, Model model) {
			 		 
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
		 	
		 	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	
		 	if ( wo.getId() == 0 ) {
		 		mapper.insertWo( wo );
		 		

		 		
		 		
		 	} else { 
		 		mapper.updateWo(wo);
		 	
		 	}
		 	
	 		if ( !wo.getBntool().equals( wo.getCurrent_bntool() ) ) 
	 			mapper.insertWoToolHistory( new WoToolHistory( wo.getId() , wo.getBntool(), "BUTTON" ) );
	 		
		 	
		 	for ( WoQty woqty : wo.getWoqty() ) {
		 		
		 		woqty.setWoid( wo.getId() );
		 		
		 		if ( woqty.getAction().equals("A") ) {
		 			
		 			mapper.insertWoQty(woqty);
		 			mapper.insertWoQuality( new WoQuality( woqty.getId()) );
		 			
		 		} else if ( woqty.getAction().equals("C") )
		 			
		 			mapper.updateWoQty(woqty);
		 		
		 		else if ( woqty.getAction().equals("D")) {
		 			
		 			mapper.deleteWoQty(woqty);
		 			mapper.deleteWoQuality( woqty.getId() );
		 			
		 		}

		 	}
		 	
		 	if ( wo.getNote().length() > 3 ) 
		 		mapper.insertWoNote(wo);
		 				 		
		 	
		 	wo.setWoqty( mapper.getWorkorderQty(wo.getId()));
			wo.setWonote( mapper.getWoNotes( wo.getId() ));
			
			List<PickList> calibers = mapper.getCaliberList();
			List<PickList> status = mapper.getStatusList();
			List<PickList> rifling = mapper.getRiflingList();
			List<PickList> steel = mapper.getSteelList();
			List<PickList> groove = mapper.getGrooveList();
			List<PickList> twist = mapper.getTwistList();
			List<PickList> steeltype = mapper.getSteelTypeList();
			
			
			model.addAttribute("CALIBERS", calibers);
			model.addAttribute("STATUS", status);
			model.addAttribute("RIFLING",rifling);
			model.addAttribute("STEEL", steel);
			model.addAttribute("GROOVE",groove );
			model.addAttribute("TWIST",twist);
			model.addAttribute("STEELTYPE", steeltype );
			
			model.addAttribute("FORM", wo );
			
			
			ctx.close();
		 			
			return "wo/workorder";
			 
		 }
		
		
		 @RequestMapping("/wo/add")
		  public String wo_new(Model model) {
			 
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
		 	
		 	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
			 
			List<PickList> calibers = mapper.getCaliberList();
			List<PickList> status = mapper.getStatusList();
			List<PickList> rifling = mapper.getRiflingList();
			List<PickList> steel = mapper.getSteelList();
			List<PickList> groove = mapper.getGrooveList();
			List<PickList> twist = mapper.getTwistList();
			List<PickList> steeltype = mapper.getSteelTypeList();
			List<PickList> scrapreasons = mapper.getScrapReasons();
			
			
			model.addAttribute("CALIBERS", calibers);
			model.addAttribute("STATUS", status);
			model.addAttribute("RIFLING",rifling);
			model.addAttribute("STEEL", steel);
			model.addAttribute("GROOVE",groove );
			model.addAttribute("TWIST",twist);
			model.addAttribute("STEELTYPE", steeltype );
			model.addAttribute("SCRAP_REASON", scrapreasons );

			
			model.addAttribute("FORM", new WorkOrder() );
			
			ctx.close();
		 			
			return "wo/workorder";
			 
		 }
		  
		 @GetMapping("/wo/quality")
		 public String wo_quality(@RequestParam(name="id", required=true) int id, Model model) {

			  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			  
			    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
			 	ctx.refresh();
		 	   
		 	    WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	    
		 	    List<WoQuality> wo_quality = mapper.getWoQualityByWoId(id);
		 	    
		 	    
		 	    WoQualityRecords quality_records = new WoQualityRecords(wo_quality);
		 	    model.addAttribute( "FORM", quality_records );
		 	    model.addAttribute( "WOID", id );
		 	    
		 	    ctx.close();
		 	    
		 	    return "wo/workorder_quality";
		 }
		  
		 
		 @GetMapping("/wo/edit")
		 public String appreport(@RequestParam(name="id", required=true) int id, Model model) {

			  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
			 	ctx.refresh();
		 	   
		 	    WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	    
		 	    WorkOrder wo = mapper.getWorkorder(id);
				wo.setWonote( mapper.getWoNotes( id ));
				wo.setWotoolhistory( mapper.getWoToolHistory(id));
				
				List<WoQty> woqty = mapper.getWorkorderQty(id);
				wo.setWoqty(woqty);
		 	    
		 	    List<PickList> calibers = mapper.getCaliberList();
				List<PickList> status = mapper.getStatusList();
				List<PickList> rifling = mapper.getRiflingList();
				List<PickList> steel = mapper.getSteelList();
				List<PickList> groove = mapper.getGrooveList();
				List<PickList> twist = mapper.getTwistList();
				List<PickList> steeltype = mapper.getSteelTypeList();
				List<PickList> scrapreasons = mapper.getScrapReasons();
				
				model.addAttribute("CALIBERS", calibers);
				model.addAttribute("STATUS", status);
				model.addAttribute("RIFLING",rifling);
				model.addAttribute("STEEL", steel);
				model.addAttribute("GROOVE",groove );
				model.addAttribute("TWIST",twist);
				model.addAttribute("STEELTYPE", steeltype );
				model.addAttribute("SCRAP_REASON", scrapreasons );
		 	    model.addAttribute("FORM",wo );
				model.addAttribute("WOID",wo.getId());
		 	    
		 	    ctx.close(); 
		 	    
		 	    return "wo/workorder";
		 }
		 	
		 
		 @GetMapping("/wo/view")
		 public String wo_print(@RequestParam(name="id", required=true) int id, Model model) throws Exception {
			 
			    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
			 	ctx.refresh();
			 	
			 	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);

			 	String createdDate = mapper.getWorkorderDate(id);
			 	
			 	model.addAttribute("ID", id);
		 	    model.addAttribute("CREATED", createdDate );
			 	
			    ctx.close();
			    
		 	    return "wo/workorder_view";
		 }
		 
		 
		 
		 @GetMapping("/wo/pdf")
		 public String wo_pdf(@RequestParam(name="id", required=true) int id, HttpServletResponse response) throws Exception {

			  AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
			 	ctx.refresh();
		 	   
		 	    WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 	    
		 	    WorkOrder wo = mapper.getWorkorderPdf(id);
		 	    List<WoQty> woqty = mapper.getWoQtyByWoid(id);
		 	    
		 	    WorkOrderPdf wopdf = new WorkOrderPdf();
		 	    
		 	    response.setContentType("application/pdf");
		 	    response.setHeader("Content-Disposition", "inline; filename=\"workorder.pdf\"");
		 	    
		 	    
		 	    wopdf.build(response, wo, woqty);
		 			   
		 	    
		 	    ctx.close();
		 	    
		 	    return null;
		 }
		 
		 
		 	  
		
		
		 @RequestMapping("/wo/search")
		 public String read(@ModelAttribute Search form, Model model) {
			 
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			
		    ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		 	ctx.refresh();
		 	
		 	List<SearchResults> results = new ArrayList<SearchResults>();
		 	   
		 	WorkOrderMapper mapper = ctx.getBean( WorkOrderMapper.class);
		 
		 	if ( ifNull( form.getWoid()).equals("") )
		 		form.setWoid("0");
		 	
		 	

		 	if ( !ifNull(form.getFromDateInput()).equals("") ) {
		 		
		 		try {
		 		
			 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			 		
			 		if ( ifNull(form.getToDateInput()).equals("") ) 
			 			form.setToDateInput( df.format( new Date() ));

			 		
			 		Date _parsedToDate = df.parse( form.getToDateInput() );
					Date _parsedFromDate = df.parse( form.getFromDateInput() );
					
					form.setToDate( new Timestamp( _parsedToDate.getTime() ) );
					form.setFromDate( new Timestamp ( _parsedFromDate.getTime() ));
					
					form.setConsiderDate(1);
					
					results = mapper.search(form);
					
		 		
		 		} catch ( Exception e ) {
		 			results = new ArrayList<SearchResults>();
		 			
		 		}
		 		
		 	} else {
		 		
		 		results = mapper.search(form);
		 	}
		 	
			
			List<PickList> calibers = mapper.getCaliberList();
			List<PickList> status = mapper.getStatusList();
			
			model.addAttribute("RESULTS",results);
			model.addAttribute("FORM",form );
			model.addAttribute("CALIBERS", calibers);
			model.addAttribute("STATUS", status);
			
			ctx.close();
		 			
			return "wo/workorder_home";
			 
		 }
		 
		 
		 private String ifNull( String s ) {
			 
			 if ( s == null)
				 s = "";
			 return s;
		 }

}
