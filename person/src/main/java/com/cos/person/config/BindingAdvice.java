package com.cos.person.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cos.person.domain.ResponseDto;

import io.sentry.Sentry;

// dispatcher : @Controller, @RestController, @Component, @Configuration
// Component : Controller가 뜨고 난 뒤에 뜸
// Configuration : Controller가 뜨기 전(진입전에 어떤것을 설정할 때)
@Component
@Aspect
public class BindingAdvice {
	
	private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);
	
	// 함수 앞 뒤
	// 함수 앞(username이 안들어왔으면 내가 강제로 넣어주고 실행)
	// 함수 뒤(응답만 관리)
	
	// 어떤함수가 언제 몇번 실행됐는지 횟수같은거 로그 남기기
	@Before("execution(* com.cos.person.web..*Controller.*(..))")
	public void testCheck() {
		//request 처리하는법
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		System.out.println("전처리 로그를 남겼습니다");
	}
	
	@After("execution(* com.cos.person.web..*Controller.*(..))")
	public void testCheck2() {
		System.out.println("후처리 로그를 남겼습니다");
		
	}
	
	//@Before
	//@After
	@Around("execution(* com.cos.person.web..*Controller.*(..))")//web 폴더 안에 있는 모든 것들중에 이름이 controller로 끝나는 것 안에 모든 함수(인자 상관없음)
	public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String method = proceedingJoinPoint.getSignature().getName();
		
		System.out.println("type : " + type);
		System.out.println("method : " + method);//ㅇ0ㅇb
		
		Object[] args = proceedingJoinPoint.getArgs();
		
		for (Object arg : args) {//args의 arg 중에
			if (arg instanceof BindingResult) {//arg가 BindingResult 타입이면
				BindingResult bindingResult = (BindingResult) arg; //여기에 담아줌
				
				// 서비스 : 정상적인 화면 -> 사용자 요청 -> 일반적이지 않은 방법으로 요청했을 경우에 문제가 생길 수 있음
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap= new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						// 로그 레벨 error, warn, info, debug (info로 설정하면 error, warn, info만뜸)
						log.warn(type+"."+method+"() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
						log.debug(type+"."+method+"() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
						Sentry.captureMessage(type+"."+method+"() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
						//getField에 키값, getDefaultMessage에 메시지
					}
					
					return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap); // 비정상이면 리턴되므로 함수 스택 안탐
				}
			}
		}
		
		return proceedingJoinPoint.proceed(); // 정상적이면 함수 스택 다시 실행
	}
}
