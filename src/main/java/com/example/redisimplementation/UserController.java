package com.example.redisimplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisTemplate<String,User> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/add_user")
    public void addValuer(@RequestParam("id") String id, @RequestBody() User user){
        redisTemplate.opsForValue().set(id, user);
    }

    @GetMapping("/get_user")
    public User getUser(@RequestParam("id")String id){

       User user = redisTemplate.opsForValue().get(id);

       return  user;
    }

    @PostMapping("/lpush")
    public void lpush(@RequestParam("key")String key,@RequestBody User user){
        redisTemplate.opsForList().leftPush(key, user);
    }

    @GetMapping("/lpop")
    public List<User> lpop(@RequestParam("key")String key,@RequestParam("count") int count){
        List<User> user = redisTemplate.opsForList().leftPop(key,count);
        return  user;
    }

    @PostMapping("/hmset")
     public void hmset(@RequestParam("key")String key,@RequestBody User user){
        Map map = objectMapper.convertValue(user, Map.class);
        redisTemplate.opsForHash().putAll(key,map);
    }
    @GetMapping("/hmget")
    public String getAttribute(@RequestParam("Key")String key,@RequestParam("attribute") String attribute){
        Map map = redisTemplate.opsForHash().entries(key);
        return (String) map.get(attribute);
    }






}
