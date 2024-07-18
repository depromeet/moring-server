package org.depromeet.sambad.moring.auth.presentation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Profile("!prod")
@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String login() {
		return "login";
	}
}
