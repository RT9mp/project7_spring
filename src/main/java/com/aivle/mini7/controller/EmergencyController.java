package com.aivle.mini7.controller;

import com.aivle.mini7.dto.EmergencyRequest;
import com.aivle.mini7.dto.EmergencyResponse;
import com.aivle.mini7.entity.Log;
import com.aivle.mini7.service.EmergencyService;
import com.aivle.mini7.service.LogService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final LogService logService;

    @GetMapping
    public String getForm(HttpSession session, @CookieValue(value = "user", required = false) String userCookie, Model model) {
        boolean isLoggedIn = false;

        if (session.getAttribute("user") != null || userCookie != null) {
            isLoggedIn = true;
        }

        model.addAttribute("isLoggedIn", isLoggedIn);

        return "input";
    }

    @PostMapping
    public String sendData(@Validated @ModelAttribute("data") EmergencyRequest requestDTO, Model model) {
        EmergencyResponse responseDTO = emergencyService.sendData(requestDTO);
        model.addAttribute("data", responseDTO);

        if (responseDTO != null) {
            logService.saveLogWithDetails(responseDTO);
            return "result";
        } else {
            throw new IllegalArgumentException("전송에 실패했습니다.");
        }
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session, @CookieValue(value = "user", required = false) String userCookie) {
        if (session.getAttribute("user") != null || userCookie != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpServletResponse response, HttpSession session) {
        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("user", username);
            session.setAttribute("role", "ADMIN");

            Cookie rememberMeCookie = new Cookie("user", username);
            rememberMeCookie.setMaxAge(600);        // 600초 유지
            rememberMeCookie.setHttpOnly(true);    // 보안 강화
            response.addCookie(rememberMeCookie);

            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpSession session) {
        session.invalidate(); // 세션 무효화
        Cookie logoutCookie = new Cookie("user", "");
        logoutCookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(logoutCookie);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String getLogs(@RequestParam(defaultValue = "0") int page, Model model,
                          @CookieValue(value = "user", required = false) String userCookie, HttpSession session
    ) {
        if ("admin".equals(userCookie)) {
            session.setAttribute("role", "ADMIN");
        }

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/";
        } else {
            Page<Log> logs = logService.getLogs(page, 5);

            model.addAttribute("logs", logs);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", logs.getTotalPages());

            return "admin";
        }
    }

    @GetMapping("/find")
    public String searchForm(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page, Model model) {

        if (startDate == null) {
            startDate = LocalDate.now(); // 기본값
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // 기본값
        }

        // 날짜 범위에 맞는 로그 조회
        Page<Log> logs = logService.getLogsByDateRange(startDate, endDate, page, 5);

        model.addAttribute("logs", logs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logs.getTotalPages());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "find"; // 검색 폼이 있는 페이지로 이동
    }

    @PostMapping("/find")
    public String getLogs(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page, Model model) {

        Page<Log> logs = logService.getLogsByDateRange(startDate, endDate, page, 5);

        model.addAttribute("logs", logs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logs.getTotalPages());

        // 날짜 값을 다시 전달하여 폼에서 유지
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "find";
    }

}
