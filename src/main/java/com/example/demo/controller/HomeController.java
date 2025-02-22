package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @RequestMapping("/")
  public String greet() {
    return "Welcome World !";
  }

  @RequestMapping("/lists")
  public String about() {
    return "about this";
  }

  @PostMapping("/addIt")
  public void addI(@RequestBody String str) {
    System.out.println(str);

  }

}
