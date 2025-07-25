import { NextResponse } from 'next/server';
import redisClient, { connectRedis } from '@/libs/redis/redis.js';

export async function GET() {
  try {
    await connectRedis();
    await redisClient.ping();
    return NextResponse.json({ status: 'ok', redis: 'connected' });
  } catch (err: any) {
    return NextResponse.json(
      { status: 'error', redis: 'disconnected', error: err.message },
      { status: 500 }
    );
  }
}
