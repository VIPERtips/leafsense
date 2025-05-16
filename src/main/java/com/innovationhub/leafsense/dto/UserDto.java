package com.innovationhub.leafsense.dto;

public class UserDto {
    private int id;
    private String password;
    private String confirmPassword;
    private String email;
   

    public UserDto() {}

    public UserDto(int id, String email) {
        this.id = id;
        this.email = email;
        
    }
    
  

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
