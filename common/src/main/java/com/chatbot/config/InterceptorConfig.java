package com.chatbot.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


//@Component
@Slf4j
public class InterceptorConfig implements HandlerInterceptor , ApplicationContextAware {




    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("check-token");
        Map<String,Object> infoMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            //token验证
            DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
            String phone = tokenInfo.getClaim("phone").asString();
//                infoMap.put("code",10010);
//                infoMap.put("msg","账号被禁用");
//                String mapInfoJson = objectMapper.writeValueAsString(infoMap);
//                response.setContentType("application/json;charset=UTF-8");
//                response.setCharacterEncoding("UTF-8");
//                System.out.println(mapInfoJson);
//                PrintWriter writer = response.getWriter();
//                writer.write(mapInfoJson);
//                writer.close();
            //添加返回信息
            infoMap.put("msg","验证成功");
            return true;
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            infoMap.put("msg","无效签名");
        }catch (TokenExpiredException e){
            infoMap.put("msg","token已过期");
        }catch (AlgorithmMismatchException e){
            infoMap.put("msg","算法不一致");
        }catch (Exception e){
            infoMap.put("msg","无效签名");
        }
        String mapInfoJson = objectMapper.writeValueAsString(infoMap);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapInfoJson);
        writer.close();
        return false;
    }


}
