package com.shilen.app.workbench.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shilen.app.workbench.dao.AdminUserMapper;
import com.shilen.app.workbench.model.admin.AdminUser;

@Controller
public class AdminUserController {

	private final AdminUserMapper adminUserMapper;
	private final PasswordEncoder passwordEncoder;

	public AdminUserController(AdminUserMapper adminUserMapper, PasswordEncoder passwordEncoder) {
		this.adminUserMapper = adminUserMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/admin/users")
	public String users(@RequestParam(name = "search", required = false, defaultValue = "") String search,
			@RequestParam(name = "message", required = false, defaultValue = "") String message,
			Model model) {
		model.addAttribute("SEARCH", search);
		model.addAttribute("RESULTS", adminUserMapper.searchUsers(search));
		if (StringUtils.hasText(message)) {
			model.addAttribute("MESSAGE", message);
		}
		return "admin/users";
	}

	@GetMapping("/admin/users/add")
	public String add(Model model) {
		model.addAttribute("FORM", new AdminUser());
		model.addAttribute("ROLES", adminUserMapper.listRoles());
		return "admin/user";
	}

	@GetMapping("/admin/users/read")
	public String read(@RequestParam(name = "id", required = true) int id,
			@RequestParam(name = "message", required = false, defaultValue = "") String message,
			Model model) {
		AdminUser user = adminUserMapper.readUser(id);
		user.setSelectedRoleIds(adminUserMapper.readUserRoleIds(user.getId()));
		model.addAttribute("FORM", user);
		model.addAttribute("ROLES", adminUserMapper.listRoles());
		if (StringUtils.hasText(message)) {
			model.addAttribute("MESSAGE", message);
		}
		return "admin/user";
	}

	@PostMapping("/admin/users/update")
	@Transactional
	public ModelAndView update(@ModelAttribute("FORM") AdminUser form, Model model) {
		List<String> errors = validate(form);
		if (!errors.isEmpty()) {
			model.addAttribute("FORM", form);
			model.addAttribute("ROLES", adminUserMapper.listRoles());
			model.addAttribute("ERRORS", errors);
			return new ModelAndView("admin/user");
		}

		if (form.getId() == 0) {
			form.setUsername(form.getUsername().trim());
			form.setPassword(passwordEncoder.encode(form.getPassword()));
			adminUserMapper.insertUser(form);
		} else {
			adminUserMapper.updateUser(form);
			if (StringUtils.hasText(form.getPassword())) {
				adminUserMapper.updatePassword(form.getId(), passwordEncoder.encode(form.getPassword()));
			}
		}

		saveRoles(form);

		ModelAndView modelAndView = new ModelAndView("redirect:/admin/users/read");
		modelAndView.addObject("id", form.getId());
		modelAndView.addObject("message", "User successfully saved.");
		return modelAndView;
	}

	private List<String> validate(AdminUser form) {
		List<String> errors = new ArrayList<String>();

		boolean hasUsername = StringUtils.hasText(form.getUsername());

		if (form.getId() == 0 && !hasUsername) {
			errors.add("Username is required.");
		}

		if (form.getId() == 0 && hasUsername && adminUserMapper.countByUsername(form.getUsername().trim()) > 0) {
			errors.add("Username already exists.");
		}

		if (hasUsername && form.getUsername().trim().length() > 12) {
			errors.add("Username must be 12 characters or fewer.");
		}

		if (form.getId() == 0 && !StringUtils.hasText(form.getPassword())) {
			errors.add("Password is required for new users.");
		}

		if (StringUtils.hasText(form.getPassword())
				&& !form.getPassword().equals(form.getConfirmPassword())) {
			errors.add("Password and confirmation must match.");
		}

		return errors;
	}

	private void saveRoles(AdminUser form) {
		adminUserMapper.deleteUserRoles(form.getId());

		if (form.getSelectedRoleIds() == null) {
			return;
		}

		for (Integer roleId : form.getSelectedRoleIds()) {
			if (roleId != null) {
				adminUserMapper.insertUserRole(form.getId(), roleId.intValue());
			}
		}
	}
}
