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
	public String getTeams(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user",user);
		userService.createUser(user);
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
	public String getTeamForm(@ModelAttribute User user, Model model) {
		AddTeamForm form = new AddTeamForm();
		form.setAdmin(user);
		model.addAttribute("form", new AddTeamForm());
		return "addTeamForm";
	}

	/** Crea un nuovo team. */
	@PostMapping("/teams")
	public String addTeam(Model model, @ModelAttribute("form") @Valid AddTeamForm form, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file) {

		if (!bindingResult.hasErrors()) {
			String urlImage = null;
			if(!file.isEmpty()) {
				urlImage = imageStorageService.storeImage(file,
						form.getAdmin().getName().toLowerCase()
								+ form.getName().toLowerCase());
			}
			Team team = teamService.createTeam(form.getAdmin(), form.getName(),
					form.getDescription(),form.getLocation(), urlImage);
			//model.addAttribute("team", team);
			return "redirect:/teams/"+team.getId();
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
