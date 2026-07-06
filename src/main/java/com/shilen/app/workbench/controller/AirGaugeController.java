package com.shilen.app.workbench.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shilen.app.workbench.dao.AirGaugeMapper;
import com.shilen.app.workbench.model.ag.AirGaugeInspection;
import com.shilen.app.workbench.model.ag.AirGaugeSample;
import com.shilen.app.workbench.model.ag.AirGaugeSearchForm;

@Controller
public class AirGaugeController {

	private final AirGaugeMapper airGaugeMapper;

	public AirGaugeController(AirGaugeMapper airGaugeMapper) {
		this.airGaugeMapper = airGaugeMapper;
	}

	@GetMapping("/ag/home")
	public String home(Model model) {
		prepareSearchPage(model, new AirGaugeSearchForm(), Collections.emptyList());
		return "ag/home";
	}

	@RequestMapping(value = "/ag/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(@ModelAttribute("SEARCH_FORM") AirGaugeSearchForm form, Model model) {
		List<AirGaugeInspection> inspections = Collections.emptyList();
		String searchValue = normalize(form.getValue());

		if (StringUtils.hasText(searchValue)) {
			if ("serial".equalsIgnoreCase(form.getType())) {
				inspections = airGaugeMapper.findBySerialNumber(searchValue);
			} else {
				form.setType("wo");
				inspections = airGaugeMapper.findByWorkOrder(searchValue);
			}
			form.setValue(searchValue);
		}

		prepareSearchPage(model, form, inspections);
		return "ag/home";
	}

	@GetMapping("/ag/inspection/{id}")
	public String inspection(@PathVariable("id") int id,
			@RequestParam(value = "backType", required = false) String backType,
			@RequestParam(value = "backValue", required = false) String backValue,
			Model model) {
		AirGaugeInspection inspection = airGaugeMapper.findInspectionById(id);
		if (inspection == null) {
			model.addAttribute("ERROR_MESSAGE", "Air Gauge inspection not found.");
			return "error-404";
		}
		model.addAttribute("INSPECTION", inspection);
		model.addAttribute("BACK_TYPE", normalizeBackType(backType));
		model.addAttribute("BACK_VALUE", normalize(backValue));
		return "ag/inspection";
	}

	@RequestMapping(value = "/ag/inspection/{id}/samples/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AirGaugeSample> samples(@PathVariable("id") int id) {
		return airGaugeMapper.findSamplesByInspectionId(id);
	}

	private void prepareSearchPage(Model model, AirGaugeSearchForm form, List<AirGaugeInspection> inspections) {
		model.addAttribute("SEARCH_FORM", form);
		model.addAttribute("INSPECTIONS", inspections);
		model.addAttribute("SEARCH_PERFORMED", StringUtils.hasText(form.getValue()));
	}

	private String normalize(String value) {
		return value == null ? "" : value.trim();
	}

	private String normalizeBackType(String value) {
		return "serial".equalsIgnoreCase(value) ? "serial" : "wo";
	}
}
