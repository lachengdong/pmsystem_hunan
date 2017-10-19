package com.sinog2c.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.sinog2c.mvc.webchat.EchoHandler;
import com.sinog2c.mvc.webchat.HandshakeInterceptor;



@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig extends WebMvcConfigurerAdapter implements
WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry arg0) {
		arg0.addHandler(systemWebSocketHandler(),
				"/chat.action")
				.addInterceptors(new HandshakeInterceptor());
		arg0.addHandler(systemWebSocketHandler(), "/sockjs/chat.action").addInterceptors(new HandshakeInterceptor()).withSockJS();	
	
		
	}
	
	@Bean
	public EchoHandler systemWebSocketHandler() {
		return new EchoHandler();
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
