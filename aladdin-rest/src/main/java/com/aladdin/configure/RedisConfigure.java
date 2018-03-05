package com.aladdin.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.aladdin.member.entity.Member;

@Configuration
public class RedisConfigure {

	@Bean
    public RedisTemplate<String, Member> redisTemplate(RedisConnectionFactory factory) {
       RedisTemplate<String, Member> redisTemplate = new RedisTemplate<String, Member>();
       redisTemplate.setConnectionFactory(factory);
       redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Member>(Member.class));
       return redisTemplate;
    }
	
}
