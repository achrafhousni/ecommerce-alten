package com.alten.shop.controller;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;
import java.util.Map;

import com.alten.shop.domain.HttpResponse;
import com.alten.shop.domain.model.User;
import com.alten.shop.domain.UserPrincipal;
import com.alten.shop.domain.dto.UserDTO;
import com.alten.shop.exception.ApiException;
import com.alten.shop.security.form.LoginForm;
import com.alten.shop.domain.mapper.UserDTOMapper;
import com.alten.shop.security.provider.TokenProvider;
import com.alten.shop.service.UserService;
import com.alten.shop.utils.ExceptionUtil;
import com.alten.shop.utils.UserUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserResource {

	
	private final AuthenticationManager authenticationManager;

    private final HttpServletRequest request;
    private final HttpServletResponse response;
	private final UserService userService;
	private final TokenProvider tokenProvider;



	@PostMapping("/token")
	public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForm) {
 		 UserDTO userDTO=authenticate(loginForm.getEmail(), loginForm.getPassword());
 		return  sendResponse(userDTO);
 		
	}


	private ResponseEntity<HttpResponse> sendResponse(UserDTO userDTO) {

		return ResponseEntity.ok().body(HttpResponse.builder()
				.timeStamp(now().toString())
				.data(Map.of("user", userDTO,"access_token",tokenProvider.createAccessToken(getUserPrincipal(userDTO))))
				.message("Login Success")
				.status(OK)
				.statusCode(OK.value())
				.build());
	}
	private UserPrincipal getUserPrincipal(UserDTO user) {
		return new UserPrincipal(UserDTOMapper.toUser(userService.getUserByEmail(user.getEmail())));
	}

	@PostMapping("/account")
	public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid UserDTO userDto) {
		
		User user=userService.createUser(userDto);
		return ResponseEntity.created(getUri(user.getId())).body(HttpResponse.builder()
				.timeStamp(now().toString())
				.data(Map.of("user",user))
				.message("User created")
				.status(CREATED)
				.statusCode(CREATED.value())
				.build());
		
	}

	private URI getUri(Long userId) {
		return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/" + userId).toUriString());
	}


	   
	   private UserDTO authenticate(String email, String password) {
		   try {

			   Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email, password));
			   return  UserUtils.getLoggedInUser(authentication);


 	        } catch (Exception exception) {
   	        	ExceptionUtil.processError(request, response, exception);
 	            throw new ApiException(exception.getMessage());
   	        }
	    }


	    

}



















