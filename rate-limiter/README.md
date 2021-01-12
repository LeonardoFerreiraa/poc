resources:
- https://engagor.github.io/blog/2017/05/02/sliding-window-rate-limiter-redis/
- https://levelup.gitconnected.com/implementing-a-sliding-log-rate-limiter-with-redis-and-golang-79db8a297b9e


redis

- https://redis.io/topics/data-types#sorted-sets
- https://redis.io/commands/#sorted_set
    - ZREMRANGEBYSCORE: Removes all elements in the sorted set stored at key with a score between min and max (inclusive).
    - ZCARD: Returns the sorted set cardinality (number of elements) of the sorted set stored at key.
    - ZADD: Adds all the specified members with the specified scores to the sorted set stored at key.
