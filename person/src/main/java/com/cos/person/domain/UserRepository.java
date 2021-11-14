package com.cos.person.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	public List<User> findAll(){
		List<User> users = new ArrayList<>();
		users.add(new User(1, "ssar", "12345", "0101111"));
		users.add(new User(2, "ccos", "12345", "0101111"));
		users.add(new User(3, "love", "12345", "0101111"));
		return users;
	}
	
	public User findById(int id){
		return new User(1, "ssar", "12345", "0101111");
	}
	
	public void save(JoinReqDto joinReqDto){
		System.out.println("DB에 insert하기");
	}
	
	public void delete(int id) {
		System.out.println("DB에 delete하기");
	}
	
	public void update(int id, UpdateReqDto updateReqDto) {
		throw new IllegalArgumentException("wrong arguments");
		//System.out.println("DB에 update하기");
	}
}
