package com.lms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.entity.Book;
import com.lms.model.UserRegistration;
import com.lms.repository.BookRepository;
import com.lms.repository.DepartmentRepository;
import com.lms.repository.StateRepository;
import com.lms.service.UserService;

@Controller
public class HomeController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserService userService;

    @RequestMapping({ "/", "index" })
    public String index(Model model) {
        List<Book> newBooks = bookRepository.findByNewBook(true);
        model.addAttribute("newBooks", newBooks);
        return "home/index";
    }

    @RequestMapping("login")
    public String login(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_HONOR")
                || request.isUserInRole("ROLE_USER")) {
            return "redirect:";
        }
        return "home/login";
    }

    @GetMapping("signup")
    public String register(Model model, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_HONOR")
        || request.isUserInRole("ROLE_USER")) {
            return "redirect:";
        }
        model.addAttribute("userRegistration", new UserRegistration());
        return "home/signup";
    }
    
    @PostMapping("signup")
    public String register(Model model, @Valid UserRegistration userRegistration, BindingResult bindingResult) {
        userRegistration.setDepartment(departmentRepository.findById(2));
        if (userService.hasErrors(userRegistration, bindingResult)) {
            return "home/signup";
        }
        userService.save(userRegistration);
        return "redirect:";
    }
    
    @RequestMapping("useInfo")
    public String useInfo(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());        
        return "home/useInfo";
    }

    @RequestMapping("intro")
    public String intro(Model model) {
        return "home/intro";
    }
}
