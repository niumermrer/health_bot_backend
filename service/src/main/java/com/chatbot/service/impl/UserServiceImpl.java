package com.chatbot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chatbot.config.JwtUtils;
import com.chatbot.config.Result;
import com.chatbot.mapper.AddressMapper;
import com.chatbot.mapper.UserAddressMapper;
import com.chatbot.mapper.UserMapper;
import com.chatbot.pojo.Address;
import com.chatbot.pojo.User;
import com.chatbot.pojo.UserAddress;
import com.chatbot.service.AddressService;
import com.chatbot.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AddressService addressService;

    @Resource
    private UserAddressMapper userAddressMapper;

    @Resource
    private AddressMapper addressMapper;

    @Override
    public Result login(JSONObject jsonObj) {

        String phone = jsonObj.getString("phone");
        String password = jsonObj.getString("password");
        Map<String, String> map = new HashMap<>();
        User user = userMapper.login(phone, password);
        if (user == null) {
            return Result.error(1011,"用户名或密码错误");
        }
        if (user.getStatus() == 0){
            return Result.error(1023,"该用户已被禁用");
        }
        map.put("phone", String.valueOf(user.getPhone()));
        map.put("password", user.getPassword());
        String usertoken = JwtUtils.genereteToken(map);
        JSONObject data = new JSONObject();
        data.put("token", usertoken);
        return Result.success("登录成功",data);
    }




    @Override
    public Result register(JSONObject jsonObj) {
        // 验证必填字段不为空
        if (StringUtils.hasText(jsonObj.getString("phone")) && StringUtils.hasText(jsonObj.getString("password")) &&
                jsonObj.getJSONArray("security") != null && jsonObj.getJSONArray("security").size() == 3) {


            User user = new User();
            String username = jsonObj.getString("username");

            // 检查用户名是否已存在
            User existedUser = userMapper.selectByUsername(username);
            if (existedUser != null) {
                return Result.error("该用户名已被注册，请选择其他用户名");
            }

            user.setUsername(username);
            user.setPhone(jsonObj.getString("phone"));
            user.setPassword(jsonObj.getString("password"));

            byte[] userAvatarBytes;
            // 检查avatar字段是否存在并转换为字节数组
            String avatarStr = jsonObj.getString("avatar");
            if (StringUtils.hasText(avatarStr)) {
                try {
                    user.setAvatar(avatarStr);
                } catch (Exception e) {
                    log.error("将avatar字符串转换为字节失败", e);
                    return Result.error("设置头像失败");
                }
            }
//            else{
//                //若无上传头像图片，则从本地上传一张图片作为默认头像
//            userAvatarBytes = DefaultAvatarUtil.getDefaultAvatarBytes();
//            String avatarString = Base64.getEncoder().encodeToString(userAvatarBytes);
//            user.setAvatar(avatarString);
//            }

            JSONArray securityArray = jsonObj.getJSONArray("security");
            JSONObject o1 = securityArray.getJSONObject(0);
            JSONObject o2 = securityArray.getJSONObject(1);
            JSONObject o3 = securityArray.getJSONObject(2);
            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
            Timestamp updateTime = Timestamp.from(zdt.toInstant());
            user.setSQ1(o1.getString("question1"));
            user.setSQ2(o2.getString("question2"));
            user.setSQ3(o3.getString("question3"));
            user.setAS1(o1.getString("answer1"));
            user.setAS2(o2.getString("answer2"));
            user.setAS3(o3.getString("answer3"));
            user.setStatus(1);
            user.setCreateTime(updateTime);
            // 执行插入操作
            try {
                if (userMapper.insert(user) > 0) {
                    return Result.success("注册成功",200);
                } else {
                    return Result.error("用户注册失败，请稍后重试",500);
                }
            } catch (Exception e) {
                log.error("用户注册时发生错误", e);
                return Result.error("服务器内部错误，请联系管理员",500);
            }
        }
        return Result.error("缺少必要的注册信息",500);
    }

    @Override
    public Result getUserInfo(HttpServletRequest request) {
            String token = request.getHeader("check-token");
            Map<String, Object> map = new HashMap<>();
            if (token == null) {
                return Result.error(5001,"Token未找到");
            }

            try {

                DecodedJWT jwt = JwtUtils.getTokenInfo(token);
                String phone = jwt.getClaim("phone").asString();

                if (phone != null) {
                    // 根据电话号码查询用户
                    User user = userMapper.selectByPhone(phone);

                    if (user != null) {
                        if (user.getAge()==null){
                            user.setAge(0);
                        }
                        UserAddress userAddresses = userAddressMapper.selectByUserId(user.getUserid());
                        if (userAddresses != null){
                            Address address = addressMapper.selectByAddressId(userAddresses.getAddressId());
                            user.setAddress(address.getProvince()+","+address.getCity()+","+address.getDistrict());
//                            map.put("user", user);
                        }
//                        map.put("user", user);
                        return Result.success("查询成功", user);
                    } else {
                        return Result.error(5002,"没有找到该用户");
                    }
                } else {
                    return Result.error(5003,"Token中不包含phone的负载");
                }
            } catch (ExpiredJwtException e) {
                return Result.error(5004,"token已过期");
            } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
                return Result.error(5005,"无效token");
            }
//            catch (Exception e) {
//                return Result.error(5006,"出错了");
//            }

    }

    @Override
    public Result CheckUserIsExist(JSONObject jsonObj) {
        String phone = jsonObj.getString("phone");
        String username = jsonObj.getString("username");
        User user = userMapper.selectByPhoneAndName(phone, username);
        if (user!= null) {
            return Result.success("查询成功", user);
        }
        return Result.error(1015,"该用户不存在");
    }

    @Override
    public Result CheckAccount(JSONObject jsonObj) {
        String username = jsonObj.getString("username");
        String phone = jsonObj.getString("phone");
        String A1 = jsonObj.getString("securityA1");
        String A2 = jsonObj.getString("securityA2");
        String A3 = jsonObj.getString("securityA3");
        User user = userMapper.selectByPhoneAndNameR(phone, username);
        if ((A1.equals(user.getAS1())) && (A2.equals(user.getAS2())) && (A3.equals(user.getAS3()))){
            return Result.success("密保问题通过！",200);
        }
        return Result.error(5007,"密保问题回答错误");
    }

    @Override
    public Result updatePassword(JSONObject jsonObj) {
        String phone = jsonObj.getString("phone");
        String newPwd = jsonObj.getString("newPwd");
        User user = userMapper.selectByPhone(phone);
        if (user.getPassword().equals(newPwd)) {
            return Result.error("新密码不能与原密码相同");

        } else {
            user.setPassword(newPwd);
            user.setUpdateTime(new Date());
            userMapper.updateById(user);
            return Result.success("修改成功");
        }
    }

    @Override
    public Result updateUserInfo(JSONObject jsonObj, HttpServletRequest request) throws Exception {
//        String token = request.getHeader("check-token");
//        DecodedJWT jwt = JwtUtils.getTokenInfo(token);
//        String phone1 = jwt.getClaim("phone").asString();

        Integer userId = jsonObj.getInteger("userid");
        String phone = jsonObj.getString("phone");
        String username = jsonObj.getString("username");
        String avatar = jsonObj.getString("avatar");
        String email = jsonObj.getString("email");
        String gender = jsonObj.getString("gender");
        Integer age = jsonObj.getInteger("age");
        String description = jsonObj.getString("description");
        String contract = jsonObj.getString("contract");
        Date birthDate = jsonObj.getDate("birthDate");
        String Q1 = jsonObj.getString("securityQ1");
        String Q2 = jsonObj.getString("securityQ2");
        String Q3 = jsonObj.getString("securityQ3");
        String A1 = jsonObj.getString("securityA1");
        String A2 = jsonObj.getString("securityA2");
        String A3 = jsonObj.getString("securityA3");
        Integer status = jsonObj.getInteger("status");

        JSONArray addresses = jsonObj.getJSONArray("address");
        Address address = new Address();
        if (addresses != null) {
            UUID uuid = UUID.randomUUID();

//            JSONObject addressObj = addresses.getJSONObject();
            address.setAddressId(uuid.toString());
            address.setProvince(addresses.getString(0));
            address.setCity(addresses.getString(1));
            address.setDistrict(addresses.getString(2));
            addressMapper.insert(address);

            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(userId);
            userAddress.setAddressId(address.getAddressId());
            userAddressMapper.update(userAddress, new QueryWrapper<UserAddress>().eq("userId", userId));
//            addressService.addAddressAndUpdateUserAddress(address, userId);
        }


        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        Timestamp updateTime = Timestamp.from(zdt.toInstant());

        Boolean flag = userMapper.updateUserById(userId, phone, username, status, avatar, email, gender, age, contract, description, Q1, Q2, Q3, A1, A2, A3, birthDate, updateTime);
        Map<String, String> map = new HashMap<>();
        User user = userMapper.selectById(userId);
        map.put("phone", String.valueOf(user.getPhone()));
        map.put("password", user.getPassword());
        String usertoken = JwtUtils.genereteToken(map);
        JSONObject data = new JSONObject();
        data.put("token", usertoken);

        if (flag){
            return Result.success("修改成功", data);
        }else {
            return Result.error(5008,"修改失败");
        }

    }




    @Override
    public Result updateAvatar(JSONObject jsonObj, HttpServletRequest request) {
        String token = request.getHeader("check-token");
        DecodedJWT jwt = JwtUtils.getTokenInfo(token);
        String phone = jwt.getClaim("phone").asString();
        String avatar = jsonObj.getString("avatar");

        Boolean flag = userMapper.updateAvatarByPhone(phone, avatar);
        if (flag){
            return Result.success("头像更新成功!",200);
        }
        return Result.error("头像更新失败!",5009);

    }

    @Override
    public Result resetPwd(JSONObject jsonObj, HttpServletRequest request) {
        String token = request.getHeader("check-token");
        DecodedJWT jwt = JwtUtils.getTokenInfo(token);
        String phone = jwt.getClaim("phone").asString();
        String oldPwd = jsonObj.getString("oldPwd");
        String newPwd = jsonObj.getString("newPwd");
        User user = userMapper.selectByPhone(phone);
        if (user.getPassword().equals(oldPwd)) {
            user.setPassword(newPwd);
            user.setUpdateTime(new Date());
            userMapper.updateById(user);
            return Result.success("修改成功",200);
        } else {

            return Result.error("原密码输入错误",5010);
        }
    }

    @Override
    public Result getChatMessage(JSONObject jsonObj) {
        if (userMapper.selectById(jsonObj.getInteger("userid")) != null){
            User user = userMapper.selectById(jsonObj.getInteger("userid"));
            return Result.success("获取成功",user.getChatMessage());
        }
        return Result.error("获取消息失败",5011);
    }

    @Override
    public Result resetChatMessage(JSONObject jsonObj) {

        if (userMapper.selectById(jsonObj.getInteger("userid")) != null){
            User user = userMapper.selectById(jsonObj.getInteger("userid"));

            userMapper.updateChatMessageById(user.getUserid(), "");

            return Result.success("重置成功",200);
        }
        return Result.error("重置消息失败",5012);
    }

    @Override
    public Result updateChatMessage(JSONObject jsonObj) {

        if (userMapper.selectById(jsonObj.getInteger("userid")) != null){
            User user = userMapper.selectById(jsonObj.getInteger("userid"));
            JSONObject chatMessage = jsonObj.getJSONObject("chatMessage");
            String s = chatMessage.toString();
            userMapper.updateChatMessageById(user.getUserid(), s);
            return Result.success("更新成功",200);
        }
        return Result.error("重置消息失败",5013);
    }


}
