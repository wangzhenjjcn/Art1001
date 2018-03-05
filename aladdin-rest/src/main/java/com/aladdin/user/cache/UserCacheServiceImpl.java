package com.aladdin.user.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import com.aladdin.member.entity.Member;

@Service
public class UserCacheServiceImpl implements UserCacheService {

	@Autowired
	private RedisTemplate<String, Member> redisTemplate;

	@Override
	public void set(final String key, final Member member, final int exp) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Jackson2JsonRedisSerializer<Member> jackson2JsonRedisSerializer = (Jackson2JsonRedisSerializer<Member>) redisTemplate
						.getValueSerializer();
				connection.set(redisTemplate.getStringSerializer().serialize(key),
						jackson2JsonRedisSerializer.serialize(member));
				if (exp!=0) {
					connection.expire(redisTemplate.getStringSerializer().serialize(key), exp);
				}
				return null;
			}
		});
	}

	@Override
	public Member get(final String key) {
		return redisTemplate.execute(new RedisCallback<Member>() {
			@SuppressWarnings("unchecked")
			@Override
			public Member doInRedis(RedisConnection connection) throws DataAccessException {
				Jackson2JsonRedisSerializer<Member> jackson2JsonRedisSerializer = (Jackson2JsonRedisSerializer<Member>) redisTemplate
						.getValueSerializer();
				return jackson2JsonRedisSerializer.deserialize(connection.get(redisTemplate.getStringSerializer().serialize(key)));
			}
		});
	}

	@Override
	public boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(redisTemplate.getStringSerializer().serialize(key));
			}
		});
	}

	@Override
	public void del(final String key) {
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(redisTemplate.getStringSerializer().serialize(key));
			}
		});
	}

}
