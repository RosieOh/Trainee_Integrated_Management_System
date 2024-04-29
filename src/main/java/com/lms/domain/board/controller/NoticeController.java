package com.lms.domain.board.controller;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.entity.Course;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.board.repository.BoardRepository;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.file.dto.FileDTO;
import com.lms.domain.file.service.FileService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.entity.Member;
import com.lms.domain.member.repository.MemberRepository;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Role;
import com.lms.global.cosntant.Subject;
import com.lms.global.util.MD5Generator;
import com.lms.global.util.PageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @Value("${upload.path}")
    private String uploadPath;

    private final MemberService memberService;
    private final BoardService boardService;
    private final CourseService courseService;
    private final FileService fileService;

    @GetMapping(value = {"/list"})
    public String noticeListAll(Model model, HttpServletRequest request, @PageableDefault(page=0, size=10, sort="title", direction= Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(required = false) String keyword, @RequestParam(required = false) Integer cno, Principal principal){
//        List<BoardDTO> boardList = boardService.findNoticeAll();
//        int pinnedCount = boardService.countPinned(boardList);
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pinnedCount", pinnedCount);

        List<CourseDTO> course_big_List = courseService.course_join_list(Subject.BIGDATA);
        List<CourseDTO> course_full_List = courseService.course_join_list(Subject.FULLSTACK);
        List<CourseDTO> course_pm_List = courseService.course_join_list(Subject.PM);
        model.addAttribute("course_big_List",course_big_List);
        model.addAttribute("course_full_List",course_full_List);
        model.addAttribute("course_pm_List",course_pm_List);

        Page<Board> boardList = boardService.searchNotice(keyword, cno, pageable);
        int pinnedCount = boardService.countPinnedPaging(boardList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pinnedCount", pinnedCount);

        //각 공지사항의 파일
        List<FileDTO> fileList = new ArrayList<>();
        for (Board board : boardList) {
            FileDTO fileDTO = fileService.getFile(board.getFileId());
            fileList.add(fileDTO);
            log.info(String.valueOf(fileDTO));
        }
        log.info(fileList+"fileList");
        model.addAttribute("fileList", fileList);

        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        PageDTO<Board, BoardDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageNow(pageNow);
        pageDTO.setPostTotal(boardList.getTotalElements());
        pageDTO.setPageTotal(boardList.getTotalPages());
        pageDTO.build(boardList);
        pageDTO.entity2dto(boardList, BoardDTO.class);

        model.addAttribute("pageDTO", pageDTO);


        //비밀글을 위한 정보 가져오기
        String id = principal.getName();
        MemberDTO memberDTO = memberService.loginId(id);
        model.addAttribute("memberDTO",memberDTO);

        return "admin/board/list";
    }

    @GetMapping("/read")
    public String readNotice(Long id, Model model, Principal principal) {
        if (id != null) {
            BoardDTO boardDTO = boardService.findById(id);
            log.info(boardDTO.getWriter());
            if (boardDTO != null) {
                FileDTO fileDTO = fileService.getFile(boardDTO.getFileId());
                //String prin = principal.getName();
                //String name = memberRepository.findId(prin).getName();
                String name = memberService.getNameById(boardDTO.getWriter());
                model.addAttribute("name", name);
                model.addAttribute("principal", principal);
                model.addAttribute("fileList", fileDTO);
                model.addAttribute("boardList", boardDTO);
            } else {
                log.info("fileDTO" + fileService);
            }
        }
        return "admin/board/read";
    }

    @GetMapping("/register")
    public String registerForm(Model model, Principal principal) {
        //String name = memberService.getMemberName(principal);
        String name = principal.getName();
        model.addAttribute("name", name);
        return "admin/board/register";
    }

    @PostMapping("/register")
    public String noticeRegister(@Valid BoardDTO boardDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 @RequestParam("file") MultipartFile files) {
        try {
            String originFilename = files.getOriginalFilename();
            String filename = new MD5Generator(originFilename).toString();
            String savePath = System.getProperty("user.dir") + "/files/";
            if(!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdirs();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String filePath = savePath + filename;

            files.transferTo(new File(filePath));

            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginFileName(originFilename);
            fileDTO.setFileName(filename);
            fileDTO.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDTO);
            boardDTO.setFileId(fileId);
            boardDTO.setWriter(boardDTO.getWriter());
            boardDTO.setBoardType("NOTICE");
            boardService.register(boardDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        return "redirect:/notice/list";
    }

    @GetMapping("/modify")
    public String noticeEditForm(Model model, Long id) {
        BoardDTO boardDTO = boardService.getBoard(id);
        FileDTO fileDTO = fileService.getFile(boardDTO.getFileId());

        model.addAttribute("fileList", fileDTO);
        model.addAttribute("boardDTO", boardDTO);
        return "admin/board/edit";
    }

    @PostMapping("/modify/{id}")
    public String noticeEdit(@PathVariable("id") Long id, @Valid BoardDTO boardDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             @RequestParam(value = "file", required = false) MultipartFile files){
//        try {
//            String originFilename = files.getOriginalFilename();
//            String filename = new MD5Generator(originFilename).toString();
//            String savePath = System.getProperty("user.dir") + "/files/";
//            if(!new File(savePath).exists()) {
//                try {
//                    new File(savePath).mkdirs();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            String filePath = savePath + filename;
//
//            files.transferTo(new File(filePath));
//
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setOriginFileName(originFilename);
//            fileDTO.setFileName(filename);
//            fileDTO.setFilePath(filePath);
//
//            Long fileId = fileService.saveFile(fileDTO);
//
//            BoardDTO boardDTO1 = boardService.getBoard(id);
//            boardDTO1.setTitle(boardDTO.getTitle());
//            boardDTO1.setContent(boardDTO.getContent());
//            boardDTO1.setPinned(boardDTO.isPinned());
//            boardDTO1.setPrivated(boardDTO.isPrivated());
//            boardDTO1.setFileId(fileId);
//            boardService.modify(boardDTO1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            // 파일이 존재하는 경우에만 파일을 업로드하고 처리
            if (files != null && !files.isEmpty()) {
                String originFilename = files.getOriginalFilename();
                String filename = new MD5Generator(originFilename).toString();
                String savePath = System.getProperty("user.dir") + "/files/";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + filename;

                files.transferTo(new File(filePath));

                // 새로운 파일 정보를 생성하여 저장
                FileDTO fileDTO = new FileDTO();
                fileDTO.setOriginFileName(originFilename);
                fileDTO.setFileName(filename);
                fileDTO.setFilePath(filePath);

                Long fileId = fileService.saveFile(fileDTO);

                // 기존 게시글 정보를 가져와서 새로운 파일 정보를 적용하여 수정
                BoardDTO boardDTO1 = boardService.getBoard(id);
                boardDTO1.setTitle(boardDTO.getTitle());
                boardDTO1.setContent(boardDTO.getContent());
                boardDTO1.setPinned(boardDTO.isPinned());
                boardDTO1.setPrivated(boardDTO.isPrivated());
                boardDTO1.setFileId(fileId);
                boardService.modify(boardDTO1);
            } else {
                // 파일이 없는 경우에는 그대로 기존 파일을 유지하고 수정
                BoardDTO boardDTO1 = boardService.getBoard(id);
                boardDTO1.setTitle(boardDTO.getTitle());
                boardDTO1.setContent(boardDTO.getContent());
                boardDTO1.setPinned(boardDTO.isPinned());
                boardDTO1.setPrivated(boardDTO.isPrivated());
                boardService.modify(boardDTO1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/notice/read?id="+id;
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.GET, RequestMethod.POST})
    public String remove(Long id, RedirectAttributes redirectAttributes) {
        log.info("remove post.. " + id);
        boardService.remove(id);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/notice/list";
    }

    private void removeFiles(List<String> files) {
        for (String fileName:files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();
            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator+"s_"+ fileName);
                    thumbnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

//   --------------------------------------클래스 공지사항 --------------------------
//    @GetMapping(value = {"/class/list"})
//    public String classNoticeAll(Model model, Long cno) {
//        List<BoardDTO> boardList = boardService.classNoticeAll(cno);
//        int pinnedCount = boardService.countPinned(boardList);
//
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pinnedCount", pinnedCount);
//        return "user/class/notice/list";
//    }

    @GetMapping(value = {"/class/list"})
    public String classNoticeAll(Model model,HttpServletRequest request, @PageableDefault(page=0, size=10, sort="title", direction= Sort.Direction.ASC) Pageable pageable,
                                 @RequestParam(required = false) String keyword, Integer cno) {


        Page<Board> boardList = boardService.searchNotice(keyword, cno,pageable);
        int pinnedCount = boardService.countPinnedPaging(boardList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pinnedCount", pinnedCount);
        model.addAttribute("cno", cno);

        int pageNow = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        PageDTO<Board, BoardDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageNow(pageNow);
        pageDTO.setPostTotal(boardList.getTotalElements());
        pageDTO.setPageTotal(boardList.getTotalPages());
        pageDTO.build(boardList);
        pageDTO.entity2dto(boardList, BoardDTO.class);

        model.addAttribute("pageDTO", pageDTO);
        return "user/class/notice/list";
    }

}
