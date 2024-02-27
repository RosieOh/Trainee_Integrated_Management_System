package com.kdt.landing.domain.apply.controller;

import com.kdt.landing.domain.apply.dto.ApplyDTO;
import com.kdt.landing.domain.apply.service.ApplyService;
import com.kdt.landing.domain.user.dto.MemberJoinDTO;
import com.kdt.landing.domain.user.entity.Member;
import com.kdt.landing.domain.user.repository.MemberRepository;
import com.kdt.landing.domain.user.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @GetMapping("/list")
    public String applyList(Model model) throws Exception{
        List<ApplyDTO> applyDTOList = applyService.findAll();
        model.addAttribute("applyDTOList", applyDTOList);
        return "apply/list";
    }

    @GetMapping("/register")
    public String applyRegister() throws Exception{
        return "apply/register";
    }

    @PostMapping("/register")
    public String applyRegister(Model model, ApplyDTO applyDTO) throws Exception{
            applyService.register(applyDTO);
        return "redirect:apply/list";
    }

    @PostMapping("/statusModify")
    public String applyStatus(Model model, ApplyDTO applyDTO) throws Exception{

        applyService.modify(applyDTO);
        return "redirect:apply/list";
    }

}