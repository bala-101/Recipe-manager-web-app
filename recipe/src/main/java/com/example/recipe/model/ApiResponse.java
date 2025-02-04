package com.example.recipe.model;

public class ApiResponse {
    private String message;
    private String redirect;

    public ApiResponse(String message, String redirect) {
        this.message = message;
        this.redirect = redirect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}