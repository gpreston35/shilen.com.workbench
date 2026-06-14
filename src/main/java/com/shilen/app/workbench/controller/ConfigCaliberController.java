package com.shilen.app.workbench.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shilen.app.workbench.dao.ConfigCaliberMapper;
import com.shilen.app.workbench.dao.FileStorageMapper;
import com.shilen.app.workbench.dao.NoteStorageMapper;
import com.shilen.app.workbench.helper.Utils;
import com.shilen.app.workbench.model.Note;
import com.shilen.app.workbench.model.config.Caliber;
import com.shilen.app.workbench.model.ro.Search;
import com.shilen.app.workbench.model.tool.ToolSearchForm;

@Controller
@SessionAttributes("STATUS_LIST")
public class ConfigCaliberController {

	private final int MODULE_ID = 5;
	private final ConfigCaliberMapper mapper;
	private final FileStorageMapper fileMapper;
	private final NoteStorageMapper noteMapper;

	public ConfigCaliberController(ConfigCaliberMapper mapper, FileStorageMapper fileMapper,
			NoteStorageMapper noteMapper) {
		this.mapper = mapper;
		this.fileMapper = fileMapper;
		this.noteMapper = noteMapper;
	}

	@GetMapping("/config/caliber")
	public String home(Model model) {
		ToolSearchForm searchForm = new ToolSearchForm();
		model.addAttribute("SEARCH_FORM", searchForm);
		return "config/caliber_home";
	}

	@RequestMapping(value = "/config/caliber/read", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String read(@RequestParam(name = "id", required = true) int id,
			@RequestParam(name = "message", required = false, defaultValue = "") String message,
			HttpServletResponse response, Model model) throws Exception {
		Caliber caliber = mapper.Read(id);
		caliber.setNotes(noteMapper.List(id, MODULE_ID));
		caliber.setFiles(fileMapper.getList(MODULE_ID, id));

		if (message != null & !message.isEmpty()) {
			model.addAttribute("MESSAGE", message);
		}

		model.addAttribute("FORM", caliber);
		return "config/caliber";
	}

	@GetMapping("/config/caliber/add")
	public String add(@ModelAttribute Search search, Model model) {
		model.addAttribute("FORM", new Caliber());
		return "config/caliber";
	}

	@PostMapping("/config/caliber/update")
	public ModelAndView update(@Valid @ModelAttribute("FORM") Caliber form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("redirect:/config/caliber/read");

		if (form.getId() == 0) {
			form.setUpdated_by(request.getRemoteUser());
			form.setCreated_by(request.getRemoteUser());
			mapper.Insert(form);
			modelAndView.addObject("message", "Record successfully added.");
		} else {
			form.setUpdated_by(request.getRemoteUser());
			mapper.Update(form);
			modelAndView.addObject("message", "Record successfully updated.");

			if (!form.getNote().isEmpty()) {
				Note note = new Note();
				note.setCreated_by(request.getRemoteUser());
				note.setNote(form.getNote());
				note.setModule_id(MODULE_ID);
				note.setRecord_id(form.getId());
				noteMapper.Insert(note);
			}
		}

		modelAndView.addObject("id", Integer.valueOf(form.getId()));
		return modelAndView;
	}

	@RequestMapping("/config/caliber/search")
	public String read(@ModelAttribute ToolSearchForm form, Model model) {
		model.addAttribute("RESULTS", mapper.Search(Utils.ifNull(form.getSearch_term()).toUpperCase()));
		model.addAttribute("SEARCH_FORM", form);
		return "config/caliber_home";
	}
}
