package com.example.HelloWorld.helloWorld.controller;

import com.example.HelloWorld.helloWorld.model.*;
import com.example.HelloWorld.helloWorld.model.Class;
import com.example.HelloWorld.helloWorld.service.ClassService;
import com.example.HelloWorld.helloWorld.service.SubjectService;
import com.example.HelloWorld.helloWorld.service.TranscriptService;
import com.example.HelloWorld.helloWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;
    @Autowired
    TranscriptService transcriptService;
    @Value("${file.path}")
    String path;
    @Autowired
    PasswordEncoder passwordEncoder;

    User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByName(authentication.getName());
    }


    @GetMapping("")
    public String admin(Model model, HttpSession session) {
        User user = (User) session.getAttribute(getCurrentUser().getUsername());
        model.addAttribute("username", user.getUser_full_name());
        return "/teacher/teacher-home-page";
    }

    @GetMapping("/registerForTeaching")
    public String registerForTeaching(Model model) {
        model.addAttribute("list", subjectService.getList());
        return "/teacher/register-for-teaching";
    }

    @GetMapping("/registerForTeaching/{SubjectId}")
    public String submit(@PathVariable(name = "SubjectId") int SubjectId, Model model) {
        Class newClass = new Class(getCurrentUser(), subjectService.getById(SubjectId));
        classService.addClass(newClass);
        return registerForTeaching(model);
    }

    @GetMapping("/manageClass")
    public String manageClass(Model model) {
        model.addAttribute("list", classService.getClassById(getCurrentUser().getUser_id()));
        return "/teacher/manageClass";
    }

    @GetMapping("/closeClass/{ClassId}")
    public String closeClass(@PathVariable(name = "ClassId") int ClassId, Model model) {
        classService.closeCLass(ClassId);
        return manageClass(model);
    }

    @GetMapping("/insertMark")
    public String getListClass(Model model) {
        model.addAttribute("list", classService.getClassById(getCurrentUser().getUser_id())
                .stream()
                .filter(c -> c.getStatus().equals(Status.DANG_HOC))
                .collect(Collectors.toList()));

        return "/teacher/myClass";
    }

    @GetMapping("/insertMark/{ClassId}")
    public String insertMark(Model model, @PathVariable(name = "ClassId") int ClassId, @RequestParam(name = "status", required = false, defaultValue = "") String status) {
        if (status.equals("true")) {
            model.addAttribute("status", "bảng điểm đã được cập nhật!");
        }
        ListTranscript list = new ListTranscript(transcriptService.findByClass_id(ClassId));
        model.addAttribute("ClassId", ClassId);
        model.addAttribute("listTranscript", list);
        return "/teacher/insertMark";
    }

    @PostMapping("/insertMark/{ClassId}")
    public String submitTranscript(Model model
            , @PathVariable int ClassId
            , @Valid @ModelAttribute ListTranscript listTranscript
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("--------------------co loi");
            ListTranscript list = new ListTranscript(transcriptService.findByClass_id(ClassId));
            model.addAttribute("ClassId", ClassId);
            model.addAttribute("listTranscript", listTranscript);
            return "/teacher/insertMark";

        }
        transcriptService.submitTranscript(listTranscript, ClassId);
        return insertMark(model, ClassId, "true");
    }

    @GetMapping("personalInformation")
    public String personalinfor(Model model) {
        model.addAttribute("user", getCurrentUser());
        return "/teacher/teacher-infor";
    }

    @GetMapping("/editInfor/{UserId}")
    public String getformedit(Model model, @PathVariable int UserId) {
        model.addAttribute("user", getCurrentUser());

        return "/teacher/edit-form";
    }

    @PostMapping("/submitedit")
    public String submitedit(Model model, @ModelAttribute User user, @RequestParam(name = "img", required = false) MultipartFile img) {
        if (!img.isEmpty()) {
            user.setImage(img.getOriginalFilename());
            try {
                FileCopyUtils.copy(img.getBytes()
                        , new File(path + img.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.updateUser(user);
        return personalinfor(model);
    }

    @GetMapping("/changePassword")
    public String changepw(Model model, @RequestParam(name = "status", required = false) String status) {
        model.addAttribute("status", status);
        return "/teacher/teacher-change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "oldpassword", required = true) String oldpassword
            , @RequestParam String newpassword
            , RedirectAttributes redirectAttributes) {
        User user = getCurrentUser();
        if (newpassword.isEmpty()) {
            redirectAttributes.addAttribute("status", "blank");
            return "redirect:/teacher/changePassword";
        }
        if (passwordEncoder.matches(oldpassword, user.getPassword())) {
            userService.updatePassword(passwordEncoder.encode(newpassword));
            redirectAttributes.addAttribute("status", "success");
        } else {
            redirectAttributes.addAttribute("status", "failed");
        }
        System.out.println(getCurrentUser().getPassword());

        return "redirect:/teacher/changePassword";
    }


}
