package com.jpl.teamx.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jpl.teamx.form.AddTeamForm;
import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;
import com.jpl.teamx.service.TeamService;
import com.jpl.teamx.service.UserService;

@Controller
public class TeamXController {
	private TeamService teamService = new TeamService();
	private UserService userService = new UserService();
	
	
	/* restituisce tutti i team */
	@GetMapping("/teams")
	public @ResponseBody String getTeams(Model model) {
		Map<Long, Team> teams = teamService.getAllTeams();
		model.addAttribute("teams", teams);
		return "get-teams";
	}

	/* Trova il team con teamId. */
	@GetMapping("/teams/{teamId}")
	public String getRestaurant(Model model, @PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		model.addAttribute("team", team);
		return "get-team";
	}

	/* Crea un nuovo team (form). */
	@GetMapping(value = "/teams", params = { "add" })
	public String getTeamForm(Model model) {
		model.addAttribute("form", new AddTeamForm());
		return "add-team-form";
	}
	
	/* Crea un nuovo team. */
	@PostMapping("/teams")
	public String addTeam(Model model, @ModelAttribute("form") AddTeamForm form) {
	Team team = teamService.createTeam(form.getAdmin(), form.getName(), form.getDescription(), form.getLocation());
	model.addAttribute("team", team);
	return "get-team";
	}
	
	/* cancella un team . */
	@GetMapping(value = "/teams/{teamId}", params = { "delete" })
	public String deleteTeam(Model model,@PathVariable Long teamId) {
		Team team = teamService.getTeam(teamId);
		teamService.deleteTeam(team);
		return "delete-team";
	}
	
	/* join in  un team . */
	@GetMapping(value = "/teams/{teamId}", params = { "join" })
	public String joinTeam(Model model,@PathVariable Long teamId) throws Exception {
		Team team = teamService.getTeam(teamId);
		String message = "da modificare";
		User u = new User("da","cambiare","non so come", "gestire la sessione");
;		userService.sendEmail(u, u, message);
		teamService.deleteTeam(team);
		return "join-team";
	}

}
