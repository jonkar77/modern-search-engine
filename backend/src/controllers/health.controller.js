import redisClient from '../services/redis.js';

export const checkHealth = async (req, res) => {
  try {
    const ping = await redisClient.ping();
    res.status(200).json({ status: 'ok', redis: ping });
  } catch (err) {
    res.status(500).json({ status: 'error', error: err.message });
  }
};
