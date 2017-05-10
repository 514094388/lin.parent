package lin;

 

import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

 

@Configuration

@EnableWebSocketMessageBroker

public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

 

    @Override

    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic"); //订阅消息的前缀为/topic,如@SendTo("/topic/deliver")

        config.setApplicationDestinationPrefixes("/api"); //订阅服务的前缀为/api,如定义@MessageMapping("/deliver"),则前端需请求/api/deliver

    }

   

    @Override

    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/api/delvier-websocket").withSockJS(); //注册websocket endpoint为/delvier-websocket

    }

 

}
