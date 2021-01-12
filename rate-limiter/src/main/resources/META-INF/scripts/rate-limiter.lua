local rateLimitKey = KEYS[1]
local uniqHash = ARGV[1]
local now = tonumber(ARGV[2])
local window = tonumber(ARGV[3])
local limit = tonumber(ARGV[4])
local clearBefore = now - window

redis.call('ZREMRANGEBYSCORE', rateLimitKey, 0, clearBefore)

local amount = redis.call('ZCARD', rateLimitKey)
if amount < limit then
    redis.call('ZADD', rateLimitKey, now, uniqHash)
end

redis.call('EXPIRE', rateLimitKey, window)

return limit - amount
