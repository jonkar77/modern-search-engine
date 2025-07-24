// redis/redisHealthCheck.js
import { createClient } from 'redis';

const client = createClient({
  url: 'redis://localhost:6379',
});

client.on('error', (err) => {
  console.error('Redis Error:', err);
  process.exit(1); // unhealthy
});

(async () => {
  try {
    await client.connect();
    const pong = await client.ping();
    console.log('✅ Redis Healthy:', pong); // should be PONG
    await client.disconnect();
    process.exit(0); // healthy
  } catch (err) {
    console.error('❌ Redis Health Check Failed:', err);
    process.exit(1); // unhealthy
  }
})();
