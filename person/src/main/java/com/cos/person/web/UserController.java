package com.cos.person.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.person.domain.JoinReqDto;
import com.cos.person.domain.ResponseDto;
import com.cos.person.domain.UpdateReqDto;
import com.cos.person.domain.User;
import com.cos.person.domain.UserRepository;

import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@RestController
public class UserController {
	
	//Autowired는 안씀 -> 옛날방식 reqarg + final을 쓰거나 생성자 직접 써주는식으로 씀
	//final은 컴파일 될때 초기화가 되있어야함
	private UserRepository userRepository;
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	// http://localhost:8080/user
	@GetMapping("/user")
	public ResponseDto<List<User>> findAll() {
		System.out.println("findAll()");
		return new ResponseDto<>(HttpStatus.OK.value(), userRepository.findAll());	//MessageConverter => java Object 를 json String으로 변경
	}
	
	// http://localhost:8080/user/1
	@GetMapping("/user/{id}")
	public ResponseDto<User> findById(@PathVariable int id) {
		System.out.println("findById() and {id} = " + id);
		return new ResponseDto<>(HttpStatus.OK.value(), userRepository.findById(id));
	}
	
	@CrossOrigin //외부에서도 요청 가능 (CORS정책 안씀)
	// http://localhost:8080/user
	@PostMapping("/user")
	// x-www-form-urlencoded => request.getParameter()
	// text/plain => @RequestBody 어노테이션으로 받을 수 있음
	// application/json => @RequestBody + 오브젝트로 받아야함
	public ResponseDto<String> save(@RequestBody JoinReqDto dto) {
		System.out.println("save()");
		System.out.println("user : " + dto);
		userRepository.save(dto);
		
//		System.out.println("data : " + data);
//		System.out.println("username : " + username);
//		System.out.println("password : " + password);
//		System.out.println("phone : " + phone);
		
		return new ResponseDto<>(HttpStatus.CREATED.value(), "ok");
	}
	
	// http://localhost:8080/user/1
	@DeleteMapping("/user/{id}")
	public ResponseDto<String> delete(@PathVariable int id) {
		System.out.println("delete()");
		userRepository.delete(id);
		return new ResponseDto<>(HttpStatus.OK.value(), null);
	}
	
	// http://localhost:8080/user/1
	@PutMapping("/user/{id}")
	public ResponseDto<String> update(@PathVariable int id, @RequestBody UpdateReqDto dto) {
		System.out.println("update()");
		
		userRepository.update(id, dto);
		
		return new ResponseDto<>(HttpStatus.OK.value(), null);
	}
}
