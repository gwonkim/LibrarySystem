package com.lms.controller;

import com.lms.entity.Book;
import com.lms.entity.BookReturn;
import com.lms.entity.Category;
import com.lms.entity.Department;
import com.lms.entity.State;
import com.lms.entity.User;
import com.lms.model.Pagination;
import com.lms.model.UserRegistration;
import com.lms.repository.BookRepository;
import com.lms.repository.BookReturnRepository;
import com.lms.repository.CategoryRepository;
import com.lms.repository.DepartmentRepository;
import com.lms.repository.StateRepository;
import com.lms.repository.UserRepository;
import com.lms.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class AdminController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  DepartmentRepository departmentRepository;

  @Autowired
  BookRepository bookRepository;

  @Autowired
  BookReturnRepository bookReturnRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired StateRepository stateRepository;

  @Autowired UserService userService;

  @RequestMapping("index")
  public String index(Model model) {
    return "admin/index";
  }

  // USER
  @RequestMapping("user/list")
  public String userList(Model model) {
    List<User> users = userRepository.findAll();
    model.addAttribute("users", users);
    return "admin/user/list";
  }

  @GetMapping("user/register")
  public String userCreate(Model model) {
    model.addAttribute("userRegistration", new UserRegistration());
    model.addAttribute("departments", departmentRepository.findAll());
    return "admin/user/edit";
  }

  @PostMapping("user/register")
  public String userCreate(
    Model model,
    @Valid UserRegistration userRegistration,
    BindingResult bindingResult
  ) {
    if (userService.hasErrors(userRegistration, bindingResult)) {
      // System.out.println("관리자 회원 등록 : bindingResult" + bindingResult); //
      model.addAttribute("departments", departmentRepository.findAll());
      return "admin/user/edit";
    }
    userService.save(userRegistration);
    return "redirect:list";
  }

  @GetMapping("user/edit")
  public String userEdit(Model model, @RequestParam("id") int id) {
    User user = userRepository.findById(id).get();
    List<Department> departments = departmentRepository.findAll();
    model.addAttribute("userRegistration", user);
    model.addAttribute("departments", departments);
    return "admin/user/edit";
  }

  @PostMapping("user/edit")
  public String userEdit(Model model, User userRegistration) {
    userRegistration.setEnabled(true);
    userRepository.save(userRegistration);
    return "redirect:list";
  }

  @RequestMapping("user/delete")
  public String userDelete(Model model, @RequestParam("id") int id) {
    userRepository.deleteById(id);
    return "redirect:list";
  }

  // BOOK
  @RequestMapping("book/list")
  public String bookList(Model model, Pagination pagination) {
    List<Book> books2 = bookRepository.findByCategoryId(pagination);
    List<Book> books = bookRepository.findAll();

    model.addAttribute("category", categoryRepository.findAll());
    model.addAttribute("books", books);
    model.addAttribute("books2", books2);
    return "admin/book/list";
  }

  @GetMapping("book/register")
  public String bookRegister(Model model) {
    Book book = new Book();
    List<State> staties = stateRepository.findAll();
    List<Category> categories = categoryRepository.findAll();
    model.addAttribute("book", book);
    model.addAttribute("staties", staties);
    model.addAttribute("categories", categories);
    return "admin/book/edit";
  }

  @PostMapping("book/register")
  public String bookRegister(Model model, Book book, Pagination pagination) {
    bookRepository.save(book);
    pagination.setDi(0);
    int lastPage = (int) Math.ceil(
      (double) bookRepository.count() / pagination.getSz()
    );
    pagination.setPg(lastPage);
    return "redirect:list?" + pagination.getQueryString();
  }

  @GetMapping("book/edit")
  public String bookEdit(Model model, @RequestParam("id") int id) {
    Book book = bookRepository.findById(id).get();
    List<State> staties = stateRepository.findAll();
    List<Category> categories = categoryRepository.findAll();
    model.addAttribute("book", book);
    model.addAttribute("staties", staties);
    model.addAttribute("categories", categories);
    return "admin/book/edit";
  }

  @PostMapping(value = "book/edit", params = "cmd=save")
  public String bookEdit(Model model, Book book, Pagination pagination) {
    bookRepository.save(book);
    return "redirect:list?" + pagination.getQueryString();
  }

  @PostMapping(value = "book/edit", params = "cmd=delete")
  public String bookDelete(
    Model model,
    @RequestParam("id") int id,
    Pagination pagination
  ) {
    bookRepository.deleteById(id);
    return "redirect:list?" + pagination.getQueryString();
  }

  // Department
  @RequestMapping("department/list")
  public String departmentList(Model model) {
    List<Department> departments = departmentRepository.findAll();
    model.addAttribute("departments", departments);
    return "admin/department/list";
  }

  @RequestMapping("department/group")
  public String departmentGroup(Model model) {
    List<Department> departments = departmentRepository.findAll();
    model.addAttribute("departments", departments);
    return "admin/department/group";
  }

  @GetMapping("department/create")
  public String departmentCreate(Model model) {
    Department department = new Department();
    model.addAttribute("department", department);
    return "admin/department/edit";
  }

  @PostMapping("department/create")
  public String departmentCreate(Model model, Department department) {
    departmentRepository.save(department);
    return "redirect:group";
  }

  @GetMapping("department/edit")
  public String departmentEdit(Model model, @RequestParam("id") int id) {
    Department department = departmentRepository.findById(id);
    model.addAttribute("department", department);
    return "admin/department/edit";
  }

  @PostMapping("department/edit")
  public String departmentEdit(Model model, Department department) {
    departmentRepository.save(department);
    return "redirect:group";
  }

  @RequestMapping("department/delete")
  public String departmentDelete(Model model, @RequestParam("id") int id) {
    departmentRepository.deleteById(id);
    return "redirect:group";
  }

  // BOOK RETURN
  @RequestMapping("return/list")
  public String returnList(Model model) {
    List<BookReturn> bookreturns = bookReturnRepository.findAll();
    model.addAttribute("bookreturns", bookreturns);
    return "admin/return/list";
  }
}