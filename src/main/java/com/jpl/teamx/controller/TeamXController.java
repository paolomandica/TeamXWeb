package com.jpl.teamx.controller;

import com.jpl.teamx.form.AddTeamForm;
import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;
import com.jpl.teamx.service.ImageStorageService;
import com.jpl.teamx.service.TeamService;
import com.jpl.teamx.service.UserService;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

	/** restituisce tutti i team */
	@GetMapping("/teams")
	public String getTeams(Model model, HttpSession session) {
		if(session.getAttribute("user")==null){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user",user);
			userService.createUser(user);
			session.setAttribute("user",user);
		}

		List<Team> teams = teamService.getAllTeams();
		model.addAttribute("teams", teams);
		return "teams";
	}


	/** Trova il team con teamId. */
	@GetMapping("/teams/{teamId}")
	public String getTeam(Model model, @PathVariable(name = "teamId") Long teamId) {
		Team team = teamService.getTeam(teamId);
		model.addAttribute("team", team);
		return "team";
	}

	
	/** Crea un nuovo team (form). */
	@GetMapping(value = "/teams", params = { "add" })
	public String getTeamForm(Model model) {
		AddTeamForm form = new AddTeamForm();
		model.addAttribute("form", form);
		return "addTeamForm";
	}

	/** Crea un nuovo team. */
	@PostMapping("/teams")
	public String addTeam(HttpSession session, Model model, @ModelAttribute("form") @Valid AddTeamForm form, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file) {

		User user = (User) session.getAttribute("user");

		if (!bindingResult.hasErrors()) {
			String urlImage = null;
			if(!file.isEmpty()) {
				urlImage = imageStorageService.storeImage(file,
						user.getName().toLowerCase()
								+ form.getName().toLowerCase());
			}
			Team team = teamService.createTeam(user, form.getName(),
					form.getDescription(),form.getLocation(), urlImage);
			model.addAttribute("user", user);
			return "redirect:/teams/"+team.getId().toString();
		} else
			return "addTeamForm";
	}

	/** cancella un team . */
	@GetMapping(value = "/teams/{teamId}", params = { "delete" })
	public String deleteTeam(Model model, @PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		// if(team.getAdmin() == new User()) {// da introdurre user corrente
		teamService.deleteTeam(team);
		model.addAttribute("message", "team eliminato con successo");
		return "redirect:/teams";
		// }
		/*
		 * model.addAttribute("team", team); model.addAttribute("error",
		 * "ci hai provato!!"); return "team";
		 */
	}

	/** join in un team */
	@PostMapping(value = "/teams/{teamId}", params = { "join" })
	public String joinTeam(Model model, @PathVariable Long teamId) throws Exception {
		Team team = teamService.getTeam(teamId);
		String message = "da modificare";
		//TODO: send email
		//User u = new User("da", "cambiare", "non so come");
		//userService.sendEmail(u, u, message);
		model.addAttribute("team", team);
		model.addAttribute("message", "richiesta inviata con successo");
		return "team";
	}
	

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public ModelAndView handleFileException(HttpServletRequest request, Throwable ex) {
	    //return your json insted this string.
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", request.getRequestURL());
	    //mav.setViewName("errorPage");
	    return mav;
	  }

}
