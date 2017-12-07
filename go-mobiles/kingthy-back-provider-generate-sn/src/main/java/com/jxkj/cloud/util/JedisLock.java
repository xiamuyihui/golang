package com.jxkj.cloud.util;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:41 on 2017/6/20.
 * @Modified by:
 */
public class JedisLock {
    private static final Lock NO_LOCK = new Lock(new UUID(0l,0l), 0l);

    private static final int ONE_SECOND = 1000;

    public static final int DEFAULT_EXPIRY_TIME_MILLIS = Integer.getInteger("com.github.jedis.lock.expiry.millis", 60 * ONE_SECOND);
    public static final int DEFAULT_ACQUIRE_TIMEOUT_MILLIS = Integer.getInteger("com.github.jedis.lock.acquiry.millis", 10 * ONE_SECOND);
    public static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = Integer.getInteger("com.github.jedis.lock.acquiry.resolution.millis", 100);

    private final StringRedisTemplate jedis;

    private final String lockKeyPath;

    private final int lockExpiryInMillis;
    private final int acquiryTimeoutInMillis;
    private final UUID lockUUID;

    private Lock lock = null;

    protected static class Lock {
        private UUID uuid;
        private long expiryTime;

        protected Lock(UUID uuid, long expiryTimeInMillis) {
            this.uuid = uuid;
            this.expiryTime = expiryTimeInMillis;
        }

        protected static Lock fromString(String text) {
            try {
                String[] parts = text.split(":");
                UUID theUUID = UUID.fromString(parts[0]);
                long theTime = Long.parseLong(parts[1]);
                return new Lock(theUUID, theTime);
            } catch (Exception any) {
                return NO_LOCK;
            }
        }

        public UUID getUUID() {
            return uuid;
        }

        public long getExpiryTime() {
            return expiryTime;
        }

        @Override
        public String toString() {
            return uuid.toString()+":"+expiryTime;
        }

        boolean isExpired() {
            return getExpiryTime() < System.currentTimeMillis();
        }

        boolean isExpiredOrMine(UUID otherUUID) {
            return this.isExpired() || this.getUUID().equals(otherUUID);
        }
    }


    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock
     * expiration of 60000 msecs.
     *
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     */
    public JedisLock(StringRedisTemplate jedis, String lockKey) {
        this(jedis, lockKey, DEFAULT_ACQUIRE_TIMEOUT_MILLIS, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     */
    public JedisLock(StringRedisTemplate jedis, String lockKey, int acquireTimeoutMillis) {
        this(jedis, lockKey, acquireTimeoutMillis, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor.
     *
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     * @param expiryTimeMillis
     *            lock expiration in miliseconds (default: 60000 msecs)
     */
    public JedisLock(StringRedisTemplate jedis, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis) {
        this(jedis, lockKey, acquireTimeoutMillis, expiryTimeMillis, UUID.randomUUID());
    }

    /**
     * Detailed constructor.
     *
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     * @param expiryTimeMillis
     *            lock expiration in miliseconds (default: 60000 msecs)
     * @param uuid
     *            unique identification of this lock
     */
    public JedisLock(StringRedisTemplate jedis, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis, UUID uuid) {
        this.jedis = jedis;
        this.lockKeyPath = lockKey;
        this.acquiryTimeoutInMillis = acquireTimeoutMillis;
        this.lockExpiryInMillis = expiryTimeMillis+1;
        this.lockUUID = uuid;
    }

    /**
     * @return lock uuid
     */
    public UUID getLockUUID() {
        return lockUUID;
    }

    /**
     * @return lock key path
     */
    public String getLockKeyPath() {
        return lockKeyPath;
    }

    /**
     * Acquire lock.
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public synchronized boolean acquire() throws InterruptedException {
        return acquire(jedis);
    }

    /**
     * Acquire lock.
     *
     * @param jedis
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    protected synchronized boolean acquire(StringRedisTemplate jedis) throws InterruptedException {
        int timeout = acquiryTimeoutInMillis;
        while (timeout >= 0) {

            final Lock newLock = asLock(System.currentTimeMillis() + lockExpiryInMillis);
//            if (jedis.setnx(lockKeyPath, newLock.toString()) == 1) {
            if (jedis.opsForValue().setIfAbsent(lockKeyPath, newLock.toString())) {
                this.lock = newLock;
                return true;
            }

            final String currentValueStr = jedis.opsForValue().get(lockKeyPath);
            final Lock currentLock = Lock.fromString(currentValueStr);
            if (currentLock.isExpiredOrMine(lockUUID)) {
                String oldValueStr = jedis.opsForValue().getAndSet(lockKeyPath, newLock.toString());
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    this.lock = newLock;
                    return true;
                }
            }

            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }

        return false;
    }

    /**
     * Renew lock.
     *
     * @return true if lock is acquired, false otherwise
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public boolean renew() throws InterruptedException {
        final Lock lock = Lock.fromString(jedis.opsForValue().get(lockKeyPath));
        if (!lock.isExpiredOrMine(lockUUID)) {
            return false;
        }

        return acquire(jedis);
    }

    /**
     * Acquired lock release.
     */
    public synchronized void release() {
        release(jedis);
    }

    /**
     * Acquired lock release.
     * @param jedis
     */
    protected synchronized void release(StringRedisTemplate jedis) {
        if (isLocked()) {
            jedis.delete(lockKeyPath);
            this.lock = null;
        }
    }

    /**
     * Check if owns the lock
     * @return  true if lock owned
     */
    public synchronized boolean isLocked() {
        return this.lock != null;
    }

    /**
     * Returns the expiry time of this lock
     * @return  the expiry time in millis (or null if not locked)
     */
    public synchronized long getLockExpiryTimeInMillis() {
        return this.lock.getExpiryTime();
    }


    private Lock asLock(long expires) {
        return new Lock(lockUUID, expires);
    }

}
