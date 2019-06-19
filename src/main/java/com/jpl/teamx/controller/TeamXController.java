package com.jpl.teamx.controller;

import com.jpl.teamx.form.AddTeamForm;
import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;
import com.jpl.teamx.service.ImageStorageService;
import com.jpl.teamx.service.TeamService;
import com.jpl.teamx.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;

//import org.h2.store.PageInputStream;

@Controller
@ControllerAdvice
public class TeamXController {
	@Autowired
	private TeamService teamService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageStorageService imageStorageService;

	@GetMapping("/user")
	public String getUserPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		// Refreshing user informations
		model.addAttribute("user", userService.getUser(user.getId()));
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
	public String getUser(Model model, @PathVariable(name = "userId") String userId) {
		if (userId == null || userService.getUser(userId) == null)
			return "redirect:/";
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		return "user";
	}

	/** restituisce tutti i team */
	@GetMapping("/teams")
	public String getTeams(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userService.getUser(user.getId())==null){
				userService.createUser(user);}
			session.setAttribute("user", user);
		}
		List<Team> teams = teamService.getAllTeams();
		model.addAttribute("teams", teams);
		return "teams";
	}

	/* Trova il team con teamId.
	@GetMapping("/teams/{teamId}")
	public String getTeam(Model model, @PathVariable(name = "teamId") Long teamId) {
		Team team = teamService.getTeam(teamId);
		model.addAttribute("team", team);
		return "team";
	}*/

	/** Crea un nuovo team (form). */
	@GetMapping(value = "/teams", params = { "add" })
	public String getTeamForm(Model model) {
		AddTeamForm form = new AddTeamForm();
		model.addAttribute("form", form);
		return "addTeamForm";
	}

	@GetMapping(value = "/teams", params = { "logout" })
	public String doLogout(Model model, HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	/** Crea un nuovo team. */
	@PostMapping("/teams")
	public String addTeam(HttpSession session, Model model, @ModelAttribute("form") @Valid AddTeamForm form,
			BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

		User user = (User) session.getAttribute("user");

		if (!bindingResult.hasErrors()) {
			String urlImage = null;
			if (!file.isEmpty()) {
				urlImage = imageStorageService.storeImage(file, user.getName().toLowerCase()
						+ form.getName().toLowerCase() + LocalDate.now().toString().toLowerCase());
			}
			Team team = teamService.createTeam(user, form.getName(), form.getDescription(), form.getLocation(),
					urlImage);
			model.addAttribute("user", user);
			return "redirect:/teams";
		} else
			return "addTeamForm";
	}

	/** cancella un team . */
	@GetMapping(value = "/teams/{teamId}", params = { "delete" })
	public String deleteTeam(HttpSession session, Model model, @PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		User user = (User) session.getAttribute("user");
		if(team.getAdmin().getId().equals(user.getId())) {
		teamService.deleteTeam(team);
		model.addAttribute("message", "team eliminato con successo");
		return "redirect:/teams";
		}
		else {
			return "redirect:/teams";
		}
	}

	@GetMapping(value = "/teams/{teamId}", params = {"follow"})
	public String followTeam(HttpSession session, Model model, @PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		User user = (User) session.getAttribute("user");
		team.getFollowers().add(user);
		teamService.update(team);
		return "redirect:/teams";
	}

	@GetMapping(value = "/teams/{teamId}", params = { "unfollow" })
	public String unfollowTeam(HttpSession session, Model model, @PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		User user = (User) session.getAttribute("user");
		team.getFollowers().remove(user);
		teamService.update(team);
		return "redirect:/teams";
	}

	// /** join in un team */
	// @PostMapping(value = "/teams/{teamId}", params = { "join" })
	// public String joinTeam(Model model, @PathVariable Long teamId) throws Exception {
	// 	Team team = teamService.getTeam(teamId);
	// 	String message = "da modificare";
	// 	// TODO: send email
	// 	// User u = new User("da", "cambiare", "non so come");
	// 	// userService.sendEmail(u, u, message);
	// 	model.addAttribute("team", team);
	// 	model.addAttribute("message", "richiesta inviata con successo");
	// 	return "team";
	// }

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public ModelAndView handleFileException(HttpServletRequest request, Throwable ex) {
		// return your json insted this string.
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", request.getRequestURL());
		// mav.setViewName("errorPage");
		return mav;
	}


	@GetMapping("/error")
	public String handleError(HttpServletRequest request,Model model) {
		System.out.println("ciao core0");
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		System.out.println("ciao core");
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				model.addAttribute("errorMsg", new Integer(404));
				return "error";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				model.addAttribute("errorMsg", new Integer(500));
				return "error";
			}
		}
		model.addAttribute("errorMsg", new Integer(400));
		return "error";
	}

}
