package com.shilen.app.workbench;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shilen.app.workbench.dao.LookupMapper;
import com.shilen.app.workbench.dao.RunoutMapper;
import com.shilen.app.workbench.helper.Utils;
import com.shilen.app.workbench.model.ro.Pivot;
import com.shilen.app.workbench.model.ro.Runout;
import com.shilen.app.workbench.model.ro.Search;

@Controller
@SessionAttributes("SEARCH_FORM")
public class RunoutController {

	private final LookupMapper lookupMapper;
	private final RunoutMapper runoutMapper;

	public RunoutController(LookupMapper lookupMapper, RunoutMapper runoutMapper) {
		this.lookupMapper = lookupMapper;
		this.runoutMapper = runoutMapper;
	}

	@GetMapping("/ro/home")
	public String home(@ModelAttribute Search search, Model model, HttpSession session) {
		Search searchForm = (Search) session.getAttribute("SEARCH_FORM");

		if (searchForm != null) {
			searchForm.setView("data");
			session.setAttribute("SEARCH_FORM", searchForm);
		} else {
			searchForm = new Search();
			searchForm.setView("data");
			searchForm.setFromDateInput(Utils.getDateBasedOnCurrent(0));
			searchForm.setToDateInput(Utils.getDateBasedOnCurrent(1));
			searchForm.setPivot_field("o.operator");
			session.setAttribute("SEARCH_FORM", searchForm);
		}

		populateLookups(model, false);
		model.addAttribute("SEARCH_FORM", searchForm);

		return "ro/home";
	}

	@RequestMapping("/ro/refresh")
	public String refresh(Model model, HttpSession session) {
		populateLookups(model, true);

		Search searchForm = (Search) session.getAttribute("SEARCH_FORM");

		if (searchForm != null) {
			searchForm.setView("data");
			session.setAttribute("SEARCH_FORM", searchForm);

			if (searchForm.getView().equals("pivot")) {
				model.addAttribute("RESULTS", runoutMapper.pivotSearch(searchForm));
				model.addAttribute("RESULTS_TOTAL", runoutMapper.pivotSearchTotals(searchForm));
			} else {
				model.addAttribute("RESULTS", runoutMapper.getSearchRunout(searchForm));
			}
		} else {
			searchForm = new Search();
			searchForm.setView("data");
			searchForm.setFromDateInput(Utils.getDateBasedOnCurrent(0));
			searchForm.setToDateInput(Utils.getDateBasedOnCurrent(1));
			searchForm.setPivot_field("o.operator");
			session.setAttribute("SEARCH_FORM", searchForm);
		}

		model.addAttribute("SEARCH_FORM", searchForm);
		return "ro/home";
	}

	@RequestMapping("/ro/search")
	public String search(@ModelAttribute Search form, Model model, HttpSession session) {
		populateLookups(model, true);

		if (form.getWoid() == null) {
			form.setWoid(0);
		}

		if (form.getView().equals("pivot")) {
			List<Pivot> results = runoutMapper.pivotSearch(form);
			model.addAttribute("RESULTS", results);
			model.addAttribute("RESULTS_TOTAL", runoutMapper.pivotSearchTotals(form));
		} else {
			List<Runout> results = runoutMapper.getSearchRunout(form);
			model.addAttribute("RESULTS", results);
		}

		model.addAttribute("SEARCH_FORM", form);
		session.setAttribute("SEARCH_FORM", form);

		return "ro/home";
	}

	@GetMapping("/ro/edit")
	public String edit(@RequestParam(name = "id", required = true) int id, Model model) throws Exception {
		Runout runout = runoutMapper.getRunout(id);

		populateLookups(model, true);
		model.addAttribute("FORM", runout);

		return "ro/runout";
	}

	@GetMapping("/ro/add")
	public String add(Model model) throws Exception {
		populateLookups(model, true);
		model.addAttribute("FORM", new Runout());
		return "ro/runout";
	}

	@RequestMapping("/ro/update")
	public String upsert(@Valid @ModelAttribute("FORM") Runout ro, BindingResult result, Model model,
			RedirectAttributes redirAttrs) {
		if (ro.getId() == 0) {
			runoutMapper.insert(ro);
			redirAttrs.addFlashAttribute(Tokens.SUCCESS, "Record successfully inserted.");
		} else {
			runoutMapper.update(ro);
			redirAttrs.addFlashAttribute(Tokens.SUCCESS, "Record successfully updated.");
		}

		redirAttrs.addAttribute("id", ro.getId());
		return "redirect:/ro/edit";
	}

	private void populateLookups(Model model, boolean includeLengths) {
		model.addAttribute("SPINDLES", lookupMapper.getSpindles());
		model.addAttribute("OPERATORS", lookupMapper.getOperators());
		model.addAttribute("CALIBERS", lookupMapper.getCalibers());
		model.addAttribute("STEEL", lookupMapper.getSteel());
		if (includeLengths) {
			model.addAttribute("LENGTHS", lookupMapper.getLengths());
		}
		model.addAttribute("SCRAPREASONS", lookupMapper.getLkScrapReasons());
	}
}
