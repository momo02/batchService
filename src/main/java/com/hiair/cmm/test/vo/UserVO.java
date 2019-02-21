package com.hiair.cmm.test.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//사용자
public class UserVO {

	 // 사용자 일련번호 
	 private Integer userSeq;
	 
	 //아이디 
	 @JsonProperty("userId") //getter/setter 의 이름을 property 와 다른 이름을 사용할 수 있도록 설정.
	 private String id;
	
	 // 비밀번호 
	 private String password;
	
	 // 역할 
	 private String role;
	
	 // 이름 
	 private String name;
	
	 // 이메일 
	 private String email;
	 
	 public UserVO() {}
	 
	 public UserVO(/*Integer userSeq,*/ String id, String password, String role, String name, String email) {
		//this.userSeq = userSeq;
		this.id = id;
		this.password = password;
		this.role = role;
		this.name = name;
		this.email = email;
	}

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

	public String getId() {
	     return id;
	 }
	
	 public void setId(String id) {
	     this.id = id;
	 }
	
	 public String getPassword() {
	     return password;
	 }
	
	 public void setPassword(String password) {
	     this.password = password;
	 }
	
	 public String getRole() {
	     return role;
	 }
	
	 public void setRole(String role) {
	     this.role = role;
	 }
	
	 public String getName() {
	     return name;
	 }
	
	 public void setName(String name) {
	     this.name = name;
	 }
	 
	 //@JsonIgnore는 자바 객체를 JSON으로 변환할 때, 특정 변수를 변환에서 제외시킴. 
	 //일반적 어노테이션과 다르게 변수 위에 설정하지 않고 Getter 메소드 위에 설정.
	 @JsonIgnore 
	 public String getEmail() {
	     return email;
	 }
	
	 public void setEmail(String email) {
	     this.email = email;
	 }

	@Override
	public String toString() {
		return "UserVO [userSeq=" + userSeq + ", id=" + id + ", password=" + password + ", role=" + role + ", name="
				+ name + ", email=" + email + "]";
	}
}