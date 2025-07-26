import app from "./app.js";
import dotenv from "dotenv";
import redisClient, { connectRedis } from "./services/redis.js";

dotenv.config();

const PORT = process.env.API_PORT || 5000;
const ENV = process.env.NODE_ENV || "development";

const ENV_URL = process.env.ENV_URL || `http://localhost:${PORT}`;

connectRedis()
  .then(() => {
    console.log("✅ Redis connected successfully");

    app.listen(PORT, () => {
      console.log(`🚀 Server listening in ${ENV} mode on ${ENV_URL}`);
    });
  })
  .catch((err) => {
    console.error("❌ Failed to connect to Redis:", err.message);
    process.exit(1);
  });
