package com.innovationhub.leafsense.response;

public class AuthenticationResponse {
    private String token;
    private String role;
    private String refreshToken;
    private String message;
    private String status;
    private Integer id;

    public AuthenticationResponse(String token, String role, String refreshToken) {
        this.token = token;
        this.role = role;
        this.refreshToken = refreshToken;
    }
    
    public AuthenticationResponse(String message, boolean success, String token) {
        this.message = message;
        this.token = token;
        this.role = success ? "VENDOR" : "USER";  
        this.refreshToken = null; 
    }
    

    public AuthenticationResponse(String token, String role,  String status ,Integer id, String refreshToken) {
		this.token = token;
		this.role = role;
		this.id = id;
		this.refreshToken = refreshToken;
		this.status = status;
	}

	public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
    
}
