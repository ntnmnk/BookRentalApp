package com.bookrental.bookrentalapp.Controllers;

import lombok.Data;

@Data
public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;

	public UserInfoResponse(Long id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

}
