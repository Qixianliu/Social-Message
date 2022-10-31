package com.example.kallz2u.bean;

public class UserBean {
   private String email;
   private String pwd;
   private String token;

   public UserBean(String email, String pwd, String token) {
      this.email = email;
      this.pwd = pwd;
      this.token = token;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPwd() {
      return pwd;
   }

   public void setPwd(String pwd) {
      this.pwd = pwd;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}
