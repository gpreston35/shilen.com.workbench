package com.shilen.app.workbench;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.model.PickList;

@Controller
public class LookupController {

			
	@RequestMapping(value = "/lkup/process/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkProcess(Model model) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper mapper = ctx.getBean(LookupMapper.class);

		List<PickList> lkprocess = mapper.getLkProcess();
		
		ctx.close();

		return lkprocess;
	}
	
	
	@RequestMapping(value = "/lkup/scrapreasons/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkScrapReasons(Model model) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper mapper = ctx.getBean(LookupMapper.class);

		List<PickList> lkscrapreasons = mapper.getLkScrapReasons();
		
		ctx.close();

		return lkscrapreasons;
	}
	
	
	@RequestMapping(value = "/lkup/barrellength/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkBarrelLength(Model model) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper mapper = ctx.getBean(LookupMapper.class);

		List<PickList> lkbarrellength = mapper.getLkBarrelLength();
		
		ctx.close();

		return lkbarrellength;
	}
	
	@RequestMapping(value = "/lkup/equipment/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getEquipment(Model model) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper mapper = ctx.getBean(LookupMapper.class);

		List<PickList> equipment = mapper.getEquipment();
		
		ctx.close();

		return equipment;
	}
	
}
	


