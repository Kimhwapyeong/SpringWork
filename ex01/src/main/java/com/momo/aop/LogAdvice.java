package com.momo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.momo.service.LogService;
import com.momo.vo.LogVO;

import lombok.extern.log4j.Log4j;

/**
 * AOP(Aspect-Oriented Programming)
 * 		관점 지향 프로그래밍
 * 		핵심비즈니스 로직과 부가적인 관심사를 분리하여 개발하는 방법론
 * 
 * 		코드의 중복을 줄이고 유지보수성을 향상시킬 수 있습니다.
 * 
 * 부가적인 관심사
 * 		로깅, 보안, 트랜젝션관리 등
 * 		애플리케이션에서 공통적으로 처리해야 하는 기능
 * 		오류 발생 시 데이터 베이스에 저장
 * 
 * Aspect
 * 		부가적인 관심사를 모듈화한 단위
 * 		(Advice를 그룹화)Cross Concern : 횡단관심사
 * 		주 업무로직 이외의 부가적인 로직
 * 
 * Advice
 * 		부가적인 관심사
 * 
 * Pointcut
 * 		부가기능이 적용되는 지점
 * 
 * Target
 * 		핵심 기능을 구현한 객체
 * 		(Core concern: 핵심 관심사)
 * 
 * Proxy
 * 		Target + Advice
 * 
 * @author user
 *
 */
@Aspect   /// advice 어노테이션
@Log4j
@Component
public class LogAdvice {

	/*
	 * 포인트컷 : 언제 어디에 적용할 건지 기술
	 * 
	 * Before
	 * 		타겟 객체의 메소드가 실행되기 전에 호출되는 어드바이스
	 * 		JoinPonit를 통해 파라미터 정보 참조 가능
	 * 			
	 */
	@Before("execution(* com.momo.service.Board*.*(..))")
	public void logBefore() {
		log.info("===============================");
	}
	
	/**
	 * joinPoint
	 * 		타겟에 대한 정보와 상태를 담고 있는 객체로 매개변수로 받아서 사용
	 * @param joinpoint
	 */
	@Before("execution(* com.momo.service.Reply*.*(..))")
	public void logBeforeParams(JoinPoint joinpoint) {
		log.info("================ AOP ==============");
		log.info("Param : " + Arrays.toString(joinpoint.getArgs()));
		log.info("Target : " + joinpoint.getTarget());
		log.info("Method : " + joinpoint.getSignature().getName());   /// 메서드 정보
	}
	
	/**
	 * Around
	 * 		타겟의 메소드가 호출되기 이전 시점과 이후 시점에 모두 처리해야 할
	 * 		필요가 있는 부가 기능 정의
	 * 
	 * 		주업무로직을 실행하기 위해 JoinPoint의 하위 클래스인
	 * 		ProceedingJoinPoint타입의 파라미터를 필수적으로 선언해야 함
	 * 		타겟메소드를 실행하고 결과를 반환하기 위해!!
	 */
//	@Around("execution(* com.momo.service.Board*.*(..))")
//	public Object logTime(ProceedingJoinPoint pjp) {
//		
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		
//		Object res = "";
//		// 주 업무로직 실행(타겟 메서드의 실행시점을 정할 수 있다)
//		try {
//			res = pjp.proceed();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		stopWatch.stop();
//		
//		log.info("========================");
//		log.info(pjp.getTarget() + "." + pjp.getSignature().getName());
//		log.info("수행시간 : " + stopWatch.getTotalTimeMillis() + "(ms)초");
//		log.info("========================");
//		
//		return res;
//		
//	}
	
	@Autowired
	LogService logService;
	
	/**
	 * AfterThrowing
	 * 		타겟 메소드 실행 중 예외가 발생한 뒤에 실행할 부가 기능
	 * 		오류 발생내역을 테이블에 등록
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut="execution(* com.momo.service.*.*(..))", throwing="exception")
	public void logException(JoinPoint joinPoint, Exception exception) {
		// 예외 발생시 예외 내용을 테이블에 저장합니다.
		
		try {
			LogVO vo = new LogVO();
			
			vo.setClassName(joinPoint.getTarget().getClass().toString());
			vo.setMethodName(joinPoint.getSignature().getName());
			vo.setParams(Arrays.toString(joinPoint.getArgs()));
			vo.setErrmsg(exception.getMessage());
			
			logService.insert(vo);
			
		} catch (Exception e) {
			log.info("로그테이블 저장중 예외 발생");
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
















