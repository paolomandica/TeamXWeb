package com.jpl.teamx.controller;

import javax.servlet.http.HttpSession;

import com.jpl.teamx.model.User;
import com.jpl.teamx.service.TeamService;
import com.jpl.teamx.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@ControllerAdvice
public class UserController {

	@Autowired
    private UserService userService;
    
    @GetMapping("/user")
	public String getUserPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		// Refreshing user informations
		model.addAttribute("user", userService.getUser(user.getId()));
		session.setAttribute("actualPage","user");
		return "user";
    }
    
    @PostMapping(value = "/user", params = { "editDescription" })
	public String updateDescription(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
		User to_persist_user = (User) httpSession.getAttribute("user");
		to_persist_user.setDescription(user.getDescription());
		userService.updateUser(to_persist_user);
		httpSession.setAttribute("user", to_persist_user);
		return "redirect:/user";
    }
    
    /** Trova user con userId. */
	@GetMapping("/users/{userId}")
	public String getUser(Model model, @PathVariable(name = "userId") String userId,
						  HttpSession session) {
		if (userId == null || userService.getUser(userId) == null)
			return "redirect:/";
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		session.setAttribute("actualPage","user");
		return "user";
	}
}