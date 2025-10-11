package com.shilen.app.workbench;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.RunoutMapper;
import com.shilen.app.workbench.helper.Utils;
import com.shilen.app.workbench.model.ro.Runout;
import com.shilen.app.workbench.model.ro.Search;

@Controller
public class RunoutController {

	@GetMapping("/ro/home")
	public String home(@ModelAttribute Search search, Model model, HttpSession session) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper lkupmapper = ctx.getBean(LookupMapper.class);
		
		Search search_form = (Search) session.getAttribute("SEARCH_FORM");

		if ( search_form != null) {
			
			search_form.setView("data");	
			session.setAttribute("SEARCH_FORM", search_form);
			
		} else {
			
			search_form = new Search();
			search_form.setView("data");	
			search_form.setFromDateInput( Utils.getDateBasedOnCurrent(0));
			search_form.setToDateInput( Utils.getDateBasedOnCurrent(1));
			search_form.setPivot_field("o.operator");
			session.setAttribute("SEARCH_FORM", search_form);

		}
		
		
		model.addAttribute("SPINDLES", lkupmapper.getSpindles());
		model.addAttribute("OPERATORS", lkupmapper.getOperators());
		model.addAttribute("CALIBERS", lkupmapper.getCalibers());
		model.addAttribute("STEEL", lkupmapper.getSteel());
		model.addAttribute("LENGTHS", lkupmapper.getLengths());
		model.addAttribute("SCRAPREASONS", lkupmapper.getLkScrapReasons());

		model.addAttribute("SEARCH_FORM", search_form);

		ctx.close();

		return "ro/home";

	}

	@RequestMapping("/ro/search")
	public String search(@ModelAttribute Search form, Model model, HttpSession session) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		
		RunoutMapper mapper = ctx.getBean(RunoutMapper.class);
		LookupMapper lkupmapper = ctx.getBean(LookupMapper.class);

		model.addAttribute("SPINDLES", lkupmapper.getSpindles());
		model.addAttribute("OPERATORS", lkupmapper.getOperators());
		model.addAttribute("CALIBERS", lkupmapper.getCalibers());
		model.addAttribute("STEEL", lkupmapper.getSteel());
		model.addAttribute("LENGTHS", lkupmapper.getLengths());
		model.addAttribute("SCRAPREASONS", lkupmapper.getLkScrapReasons());
		
		if ( form.getWoid() == null )
			form.setWoid(0);
			
		

		if ( form.getView().equals("pivot") )
			model.addAttribute("RESULTS", mapper.pivotSearch(form));
		else
			model.addAttribute("RESULTS", mapper.getSearchRunout(form));
			
		
		model.addAttribute("SEARCH_FORM", form);
		session.setAttribute("SEARCH_FORM", form);

		ctx.close();

		return "ro/home";

	}

	@GetMapping("/ro/edit")
	public String edit(@RequestParam(name = "id", required = true) int id, Model model) throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		RunoutMapper mapper = ctx.getBean(RunoutMapper.class);
		LookupMapper lkupmapper = ctx.getBean(LookupMapper.class);

		Runout runout = mapper.getRunout(id);

		model.addAttribute("FORM", new Search());
		model.addAttribute("SPINDLES", lkupmapper.getSpindles());
		model.addAttribute("OPERATORS", lkupmapper.getOperators());
		model.addAttribute("CALIBERS", lkupmapper.getCalibers());
		model.addAttribute("STEEL", lkupmapper.getSteel());
		model.addAttribute("LENGTHS", lkupmapper.getLengths());
		model.addAttribute("SCRAPREASONS", lkupmapper.getLkScrapReasons());

		model.addAttribute("FORM", runout);

		ctx.close();

		return "ro/runout";
	}

	@GetMapping("/ro/add")
	public String add(Model model) throws Exception {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		LookupMapper lkupmapper = ctx.getBean(LookupMapper.class);

		model.addAttribute("FORM", new Search());
		model.addAttribute("SPINDLES", lkupmapper.getSpindles());
		model.addAttribute("OPERATORS", lkupmapper.getOperators());
		model.addAttribute("CALIBERS", lkupmapper.getCalibers());
		model.addAttribute("STEEL", lkupmapper.getSteel());
		model.addAttribute("LENGTHS", lkupmapper.getLengths());
		model.addAttribute("SCRAPREASONS", lkupmapper.getLkScrapReasons());

		model.addAttribute("FORM", new Runout());

		ctx.close();

		return "ro/runout";
	}

	@RequestMapping("/ro/update")
	public String upsert(@Valid @ModelAttribute("FORM") Runout ro, BindingResult result, Model model,
			RedirectAttributes redirAttrs) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(com.shilen.app.workbench.dao.AppConfig.class);
		ctx.refresh();

		RunoutMapper mapper = ctx.getBean(RunoutMapper.class);

		if (ro.getId() == 0) {
			mapper.insert(ro);
			redirAttrs.addFlashAttribute(Tokens.SUCCESS, "Record successfully inserted.");

		} else {
			mapper.update(ro);
			redirAttrs.addFlashAttribute(Tokens.SUCCESS, "Record successfully updated.");

		}

		redirAttrs.addAttribute("id", ro.getId());

		ctx.close();

		return "redirect:/ro/edit";

	}

}
