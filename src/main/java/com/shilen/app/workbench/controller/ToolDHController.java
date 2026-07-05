package com.shilen.app.workbench.controller;

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
import com.shilen.app.workbench.dao.ToolDHMapper;
import com.shilen.app.workbench.helper.Utils;
import com.shilen.app.workbench.model.Note;
import com.shilen.app.workbench.model.ro.Search;
import com.shilen.app.workbench.model.tool.DeepHole;
import com.shilen.app.workbench.model.tool.ToolSearchForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("STATUS_LIST")
public class ToolDHController {

	private final int MODULE_ID = Tokens.MODULE_DEEPHOLE;
	private final ToolDHMapper mapper;
	private final FileStorageMapper fileMapper;
	private final NoteStorageMapper noteMapper;

	public ToolDHController(ToolDHMapper mapper, FileStorageMapper fileMapper, NoteStorageMapper noteMapper) {
		this.mapper = mapper;
		this.fileMapper = fileMapper;
		this.noteMapper = noteMapper;
	}

	@GetMapping("/tool/dh")
	public String home(Model model) {
		ToolSearchForm searchForm = new ToolSearchForm();
		model.addAttribute("SEARCH_FORM", searchForm);
		return "tool/deephole_home";
	}

	@SuppressWarnings("null")
	@GetMapping("/tool/dh/read")
	public String read(@RequestParam(name = "id", required = true) int id,
			@RequestParam(name = "message", required = false, defaultValue = "") String message,
			HttpServletResponse response, Model model) throws Exception {
		DeepHole deephole = mapper.Read(id);
		deephole.setNotes(noteMapper.List(id, MODULE_ID));
		deephole.setFiles(fileMapper.getList(Tokens.MODULE_DEEPHOLE, id));

		if (message != null & !message.isEmpty()) {
			model.addAttribute("MESSAGE", message);
		}

		model.addAttribute("FORM", deephole);
		return "tool/deephole";
	}

	@GetMapping("/tool/dh/new")
	public String newButton(@ModelAttribute Search search, Model model) {
		model.addAttribute("FORM", new DeepHole());
		return "tool/deephole";
	}

	@PostMapping("/tool/dh/update")
	public ModelAndView update(@Valid @ModelAttribute("FORM") DeepHole form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("redirect:/tool/dh/read");

		if (form.getDos_str().isEmpty()) {
			form.setDos(null);
		} else {
			form.setDos(java.sql.Date.valueOf(form.getDos_str()));
		}

		if (form.getDom_str().isEmpty()) {
			form.setDom(null);
		} else {
			form.setDom(java.sql.Date.valueOf(form.getDom_str()));
		}

		if (form.getEos_str().isEmpty()) {
			form.setEos(null);
		} else {
			form.setEos(java.sql.Date.valueOf(form.getEos_str()));
		}

		if (form.getRetip_date_str().isEmpty()) {
			form.setRetip_date(null);
		} else {
			form.setRetip_date(java.sql.Date.valueOf(form.getRetip_date_str()));
		}

		if (form.getId() == 0) {
			form.setUpdated_by(request.getRemoteUser());
			form.setCreated_by(request.getRemoteUser());
			mapper.Insert(form);
			mapper.updateToolIdentifier("DH" + String.format("%0" + 4 + "d", form.getId()), form.getId());
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

	@RequestMapping("/tool/dh/search")
	public String read(@ModelAttribute ToolSearchForm form, Model model) {
		model.addAttribute("RESULTS", mapper.Search(form));
		model.addAttribute("SEARCH_FORM", form);
		return "tool/deephole_home";
	}
}
