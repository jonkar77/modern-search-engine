package Cache.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    private final JedisPool jedisPool;

    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public boolean isRedisHealthy() {
        try (Jedis jedis = jedisPool.getResource()) {
            return "PONG".equals(jedis.ping());
        } catch (Exception e) {
            return false;
        }
    }
}
