package com.jpl.teamx.controller;

import com.jpl.teamx.form.AddTeamForm;
import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;
import com.jpl.teamx.service.ImageStorageService;
import com.jpl.teamx.service.TeamService;
import com.jpl.teamx.service.UserService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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

	@GetMapping("/")
	public String index() {
		return "index";
	}

	/** restituisce tutti i team */
	@GetMapping("/teams")
	public String getTeams(Model model, Principal principal) {
		if (!model.containsAttribute("currentUser") & !(principal == null)) {
			model.addAttribute("currentUser", this.getUserFromPrincipal(principal));
		}
		List<Team> teams = teamService.getAllTeams();
		model.addAttribute("teams", teams);
		return "teams";
	}

	private User getUserFromPrincipal(Principal principal) {
		OidcUser oidcUser = (OidcUser) principal;
		Map attributes = oidcUser.getAttributes();
		// TODO: se non trova l'user throw Exception
		// String email = (String) attributes.get("email");
		// User user = userService.getUserByEmail(email);
		User user = new User();
		user.setEmail((String) attributes.get("email"));
		user.setImageUrl((String) attributes.get("picture"));
		user.setName((String) attributes.get("name"));
		return user;
	}

	@GetMapping("/custom-login")
	public String loadLogin() {
		return "login";
	}

	/** Trova il team con teamId. */
	@GetMapping("/teams/{teamId}")
	public String getTeam(Model model, @PathVariable(name = "teamId") Long teamId) {
		Team team = teamService.getTeam(teamId);
		System.out.println("Questo è il nome:");
		System.out.println(team.getName());
		model.addAttribute("team", team);
		return "team";
	}

	/** Crea un nuovo team (form). */
	@GetMapping(value = "/teams", params = { "add" })
	public String getTeamForm(Model model) {
		model.addAttribute("form", new AddTeamForm());
		return "addTeamForm";
	}

	/** Crea un nuovo team. */
	@PostMapping("/teams")
	public String addTeam(Model model, @ModelAttribute("form") @Valid AddTeamForm form, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file) {

		if (!bindingResult.hasErrors() && !file.isEmpty()) {
			String urlImage = imageStorageService.storeImage(file,
					"dacambiare" /* form.getAdmin().getName().toLowerCase() */ + form.getName().toLowerCase());

			Team team = teamService.createTeam(form.getAdmin(), form.getName(), form.getDescription(),
					form.getLocation(), urlImage);
			model.addAttribute("team", team);

			return "team";
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
		return "teams";
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
		User u = new User("da", "cambiare", "non so come");
		userService.sendEmail(u, u, message);
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
