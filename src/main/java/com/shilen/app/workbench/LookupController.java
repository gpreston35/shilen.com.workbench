package com.shilen.app.workbench;

import java.util.List;

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

	private final LookupMapper lookupMapper;

	public LookupController(LookupMapper lookupMapper) {
		this.lookupMapper = lookupMapper;
	}

	@RequestMapping(value = "/lkup/process/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkProcess(Model model) {

		return lookupMapper.getLkProcess();
	}
	
	
	@RequestMapping(value = "/lkup/scrapreasons/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkScrapReasons(Model model) {

		return lookupMapper.getLkScrapReasons();
	}
	
	
	@RequestMapping(value = "/lkup/barrellength/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getLkBarrelLength(Model model) {

		return lookupMapper.getLkBarrelLength();
	}
	
	@RequestMapping(value = "/lkup/equipment/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<PickList> getEquipment(Model model) {

		return lookupMapper.getEquipment();
	}
	
}
	

