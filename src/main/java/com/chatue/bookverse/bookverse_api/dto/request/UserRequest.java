package com.chatue.bookverse.bookverse_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {
	@NotBlank(message = "Merci de saisir une valeur!")
	private Long userId;
}
