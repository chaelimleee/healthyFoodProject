package com.javateam.healthyFoodProject.config;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.javateam.healthyFoodProject.service.CustomProvider;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	@Autowired
	private CustomProvider customProvider;

	private UserDetailsService userDetailsService;

	private DataSource dataSource;

	public SecurityConfig(UserDetailsService userDetailsService, DataSource dataSource) {

		log.info("생성자 주입");
		this.dataSource = dataSource;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// security 적용 예외 URL 등록
	// bootstrap-icons/** 추가
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {

		// 게시판 : summernote 추가
		// swagger 항목 예외(열외) 추가 :
		// 참고) /v2/api-docs : swagger의 전체적인 환경설정 정보를 JSON 형식으로 보여주는 페이지
		// /v2/api-docs, /swagger-resources/**, /swagger/**, swagger-ui.html
		// axios 항목 예외 추가 
		// /swagger/** ==> /swagger-ui/** 변경함. 
		// /swagger-ui.html  ==>  /swagger-ui/index.html 변경함.  //0509 "/"추가
		return (web) -> web.ignoring().requestMatchers("/css/**", "/webjars/**", "/img/**",
				"/images/**", "/js/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui/index.html",
				"/axios/**", "/bootstrap-icons/**", "/bootstrap/**", "/lib/**",
				"/summernote/**","/jquery/**");//"/jquery/**"0519 추가함. 
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable()); // csrf 토큰 미사용

		http.userDetailsService(userDetailsService);

		http.authenticationProvider(customProvider);

		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions
				.sameOrigin()));

		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// axios 추가
				// security 적용 예외 URL 등록와의 중복 부분 제외 => "/"만 적용
				// .requestMatchers("/", "/css/**", "/webjars/**", "/images/**", "/js/**",
				// "/axios/**", "/bootstrap-icons/**").permitAll()
				.requestMatchers("/").permitAll()
				
				// /swagger/** ==> /swagger-ui/** 변경함. 
				// /swagger-ui.html  ==>  /swagger-ui/index.html 변경함. 
				.requestMatchers(
						"/swagger-resources/**", 
						"/swagger-ui/**", 
						"/swagger-ui/index.html").permitAll()
				.requestMatchers(
						"/member/hasFld/**", 
						"/member/view.do").permitAll()
				.requestMatchers(
						"/member/update.do", 
						"/member/updateProc.do").authenticated()
				.requestMatchers(
						"/member/updateSess.do", 
						"/member/updateSessProc.do").authenticated()
				.requestMatchers(
						"/member/join.do", 
						"/member/joinProc.do", 
						"/member/joinProcRest.do").permitAll()
				.requestMatchers(
						"/member/updateRoles/**", 
						"/member/changeMemberState/**",
						"/member/updateMemberByAdmin/**", 
						"/member/deleteMemberByAdmin/**",
						"/admin/adminQna.do")
				.authenticated()
				.requestMatchers("/board/replyWrite.do").permitAll()
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				//
				 // 게시판 관련 링크 추가 
        //240405-es-song- photo_board 추가(permitAll(),authenticated())
		// /board/view.do/** 비회원만 볼 수 있는 상황. 로그인 한 사용자는 볼 수 없는 것 수정해야함. 0415 leee
        		.requestMatchers(
		        		"/board/view.do/**",
		        		"/board/list.do/**",
		        		"/board/searchList.do",
		        		"/board/searchList.do/**",
		                "/board/image", 
		                "/board/image/**",
		                "/board/getRepliesAll.do",
		                "/board/download/**"
		        		).permitAll()
        		.requestMatchers(
        				"/sasang/sasang.do",
        				"/sasang/list.do/**"
        				).permitAll()
        		.requestMatchers(
		                "/photo_board/photo_view.do/**",
		                "/photo_board/list.do",
		                "/photo_board/searchList.do",
		                "/photo_board/searchList.do/**",
		                "/photo_board/image", 
		                "/photo_board/image/**",
		                "/photo_board/getRepliesAll.do"
		                ).permitAll()
        		
        		.requestMatchers(
		        		"/qna/qna_view.do",
		        		"/qna/image", 
		        		"/qna/image/**",
		                "/qna/getRepliesAll.do",
		                "/qna/qna_view.do/**",
		                "/qna/list.do/**",
		                "/qna/searchList.do",
		                "/qna/getRepliesAll.do"
		                ).permitAll()
        		
        		.requestMatchers(
		        		"/food/list.do",
		        		"/food/list.do/**",
		        		"/food/view.do/**",
		        		"/food/image", 
		        		"/food/image/**",
		        		"/food/searchList.do",
		        		"/food/searchList.do/**",
		        		"/food/sasang/searchList.do/**"
		        		).permitAll()

        		.requestMatchers(
        				"/board/member/list.do",
        				"/board/write.do",
        				"/board/writeProc.do",
                        "/board/update.do", 
                        "/board/updateProc.do",
                        "/board/replyWrite.do",
                        "/board/replyUpdate.do",
                        "/board/replyDelete.do",
                        "/board/deleteProc.do"
        				).authenticated()
                        
                .requestMatchers(
                        "/photo_board/write.do",
                        "/photo_board/writeProc.do",
                        "/photo_board/update.do", 
                        "/photo_board/updateProc.do",
                        "/photo_board/replyWrite.do",
                        "/photo_board/replyUpdate.do",
                        "/photo_board/replyDelete.do",
                        "/photo_board/deleteProc.do"
                		).authenticated()
                
                .requestMatchers(        
                        "/qna/write.do",
                        "/qna/writeProc.do",
                        "/qna/update.do", 
                        "/qna/updateProc.do",
                        "/qna/replyWrite.do",
                        "/qna/replyUpdate.do",
                        "/qna/replyDelete.do",
                        "/qna/deleteProc.do",
                        "/qna/download/**"
                        ).authenticated()
        
        .requestMatchers("/sasang/**").authenticated()
        
        .anyRequest().authenticated()); 

		http.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.usernameParameter("memberEmail")
				.passwordParameter("memberPw")
				.defaultSuccessUrl("/")
				//.defaultSuccessUrl("/member/view.do")
				.failureUrl("/loginError")
				// .successHandler(new CustomAuthenticationSuccess()) // 로그인 성공 핸들러
				// .failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러
				.permitAll());

		http.logout((logout) -> logout.permitAll());

		http.exceptionHandling(handler -> handler.accessDeniedPage("/403"));
		// .and()
		// .logout()
		// .logoutSuccessUrl("/")

		// .and()
		// .oauth2Login()
		// .userInfoEndpoint()
		// .userService(customOAuth2UserService);

		http.rememberMe((remember) -> remember
				.key("javateam")
				.userDetailsService(userDetailsService)
				.tokenRepository(getJDBCRepository())
				.tokenValiditySeconds(60 * 60 * 24)); // 24시간(1일)

		return http.build();
	} //

	// 추가된 remember-me 관련 메서드
	private PersistentTokenRepository getJDBCRepository() {

		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);

		return repo;
	} //

}