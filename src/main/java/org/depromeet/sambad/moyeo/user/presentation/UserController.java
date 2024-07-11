package org.depromeet.sambad.moyeo.user.presentation;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moyeo.user.application.UserService;
import org.depromeet.sambad.moyeo.user.presentation.resolver.UserId;
import org.depromeet.sambad.moyeo.user.presentation.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@UserId Long userId) {
        UserResponse response = userService.findByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
