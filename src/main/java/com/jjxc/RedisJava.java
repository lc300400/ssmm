package com.jjxc;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisJava {

    public static void main(String[] args) {
        demo1();
        demo2();
    }

    /**
     * Jedis连接redis
     */
    public static void demo1(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println(jedis.smembers("myset"));
    }

    /**
     * Jedis连接池连接redis
     */
    public static void demo2(){
        //获得连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxActive(30);
        //设置最大空闲连接数
        config.setMaxIdle(10);
        //配置连接池
        JedisPool pool = new JedisPool(config,"localhost",6379);
        //获取客户端
        Jedis jedis = pool.getResource();
        try {
            jedis.set("jedis","test123");
            System.out.println(jedis.get("jedis"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
