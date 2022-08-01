package com.example.HelloWorld.helloWorld.controller;

import com.example.HelloWorld.helloWorld.model.Class;
import com.example.HelloWorld.helloWorld.model.Status;
import com.example.HelloWorld.helloWorld.model.Transcript;
import com.example.HelloWorld.helloWorld.model.User;
import com.example.HelloWorld.helloWorld.service.ClassService;
import com.example.HelloWorld.helloWorld.service.TranscriptService;
import com.example.HelloWorld.helloWorld.service.UserService;
import com.example.HelloWorld.helloWorld.userconfig.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;
    @Autowired
    TranscriptService transcriptService;
    @Autowired
    CurrentUser currentUser;
    @Value("${file.path}")
    String path;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    boolean canRegister(int classid) {
        int subject_id = classService.getClassByClassId(classid).getSubject().getSubject_id();
        return transcriptService.findByUser_id(currentUser.getCurrentUSer().getUser_id())
                .stream()
                .filter(t -> t.getAClass().getSubject().getSubject_id() == subject_id)
                .collect(Collectors.toList()).isEmpty();
    }

    @GetMapping("")
    public String user(Model model) {
        model.addAttribute("fullname", currentUser.getCurrentUSer().getUser_full_name());
        return "/student/user-home-page";
    }

    @GetMapping("/getTranscript")
    public String getTranscript(Model model) {
        List<Transcript> list = transcriptService.findByUser_id(currentUser.getCurrentUSer().getUser_id());
        model.addAttribute("list", list);
        return "/student/transcript";
    }


    @GetMapping("/registerForStudying")
    public String registerForStudying(Model model, @RequestParam(name = "status", required = false, defaultValue = "") String status) {

        if (!status.isEmpty()) {
            model.addAttribute("status", status);
        }
        model.addAttribute("list", classService.getAllClass().stream().
                filter(c -> c.getStatus().equals(Status.DANG_DANG_KI))
                .collect(Collectors.toList()));
        return "/student/register-for-studying";
    }

    @GetMapping("/registerForStudying/{ClassId}")
    public String submitregister(Model model, @PathVariable(name = "ClassId") int ClassId) {
        if (canRegister(ClassId)) {
            transcriptService.addTranscript(new Transcript(currentUser.getCurrentUSer(), ClassId));
            classService.updateClassSize(ClassId);
            return registerForStudying(model, "true");
        }
        return registerForStudying(model, "false");

    }

    @GetMapping("/classTranscript/{ClassId}")
    public String classTranscript(Model model, @PathVariable(name = "ClassId") int CLassId) {
        List<Transcript> list = transcriptService.findByClass_id(CLassId);
        model.addAttribute("list", list);
        return "/student/class-transcript";
    }

    @GetMapping("/personalInformation")
    public String getPersonalInfor(Model model) {
        model.addAttribute("user", currentUser.getCurrentUSer());
        System.out.println(currentUser.getCurrentUSer().getImagePath());

        return "/student/user-infor";
    }

    @GetMapping("/editInfor/{UserId}")
    public String getEditForm(Model model, @PathVariable(name = "UserId") int UserId) {
        model.addAttribute("user", currentUser.getCurrentUSer());
        return "/student/edit-form";
    }

    @PostMapping("/submitedit")
    public String sunmitedit(@ModelAttribute User user, Model model, @RequestPart(name = "img", required = false) MultipartFile img) {

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
        return getPersonalInfor(model);
    }

    @GetMapping("/changePassword")
    public String changepw(Model model, @RequestParam(name = "status", required = false) String status) {
        model.addAttribute("status", status);
        return "/student/student-change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "oldpassword", required = true) String oldpassword
            , @RequestParam String newpassword
            , RedirectAttributes redirectAttributes) {
        User user = currentUser.getCurrentUSer();
        if (newpassword.isEmpty()) {
            redirectAttributes.addAttribute("status", "blank");
            return "redirect:/student/changePassword";
        }
        if (bCryptPasswordEncoder.matches(oldpassword, user.getPassword())) {
            userService.updatePassword(bCryptPasswordEncoder.encode(newpassword));
            redirectAttributes.addAttribute("status", "success");
        } else {
            redirectAttributes.addAttribute("status", "failed");
        }
        System.out.println(currentUser.getCurrentUSer().getPassword());

        return "redirect:/student/changePassword";
    }


}
