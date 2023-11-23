package com.ssafy.moment.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final String deniedUrl = "/auth/access-denied";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().println(
//            new ObjectMapper().writeValueAsString(
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("접근거부: 로그인이 필요합니다!")
//            )
//        );
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.sendRedirect(request.getContextPath() + deniedUrl);
    }

}