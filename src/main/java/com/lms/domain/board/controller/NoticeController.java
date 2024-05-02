package com.lms.domain.board.controller;

import com.lms.domain.Course.dto.CourseDTO;
import com.lms.domain.Course.service.CourseService;
import com.lms.domain.board.dto.BoardDTO;
import com.lms.domain.board.entity.Board;
import com.lms.domain.board.service.BoardService;
import com.lms.domain.file.dto.FileDTO;
import com.lms.domain.file.service.FileService;
import com.lms.domain.member.dto.MemberDTO;
import com.lms.domain.member.service.MemberService;
import com.lms.global.cosntant.Subject;
import com.lms.global.util.MD5Generator;
import com.lms.global.util.PageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("searchTotal", boardList.getTotalElements());

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
        log.info(String.valueOf(memberDTO));
        model.addAttribute("memberDTO",memberDTO);

        return "admin/board/list";
    }


    @GetMapping("/fileList")
    @ResponseBody
    public List<Long> getFileList(Long boardId) {
        List<Long> fileIds = fileService.getFileIdsByBoardId(boardId);
        return fileIds;
    }

    @GetMapping("/read")
    public String readNotice(Long id, Model model, Principal principal) {
        BoardDTO boardDTO = boardService.findById(id);
        List<FileDTO> files = fileService.findByBoardId(id);

        model.addAttribute("principal", principal);
        model.addAttribute("boardList", boardDTO);
        model.addAttribute("files",files);

        return "admin/board/read";
    }

    @GetMapping("/register")
    public String registerForm(Model model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("name", name);
        return "admin/board/register";
    }

    @PostMapping("/register")
    public String noticeRegister(@Valid BoardDTO boardDTO, Model model, @RequestParam("files") MultipartFile[] files) {
        try {
            boardDTO.setWriter(boardDTO.getWriter());
            boardDTO.setBoardType("NOTICE");
            boardDTO.setPinned(boardDTO.isPinned());
            boardDTO.setPrivated(boardDTO.isPrivated());
            Long boardId = boardService.register(boardDTO);

            List<FileDTO> uploadFiles = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originFilename = file.getOriginalFilename();
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

                    file.transferTo(new File(filePath));

                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setOriginFileName(originFilename);
                    fileDTO.setFileName(filename);
                    fileDTO.setFilePath(filePath);
                    fileDTO.setBoardId(boardId);

                    uploadFiles.add(fileDTO);
                }
            }
            List<Long> fileIds = fileService.saveFiles(uploadFiles);

        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        return "redirect:/notice/list";
    }

    @GetMapping("/modify")
    public String noticeEditForm(Model model, Long id) {
        BoardDTO boardDTO = boardService.getBoard(id);
        List<FileDTO> fileList = fileService.findByBoardId(id);

        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("fileList", fileList);
        return "admin/board/edit";
    }

    @PostMapping("/modify/{id}")
    public String noticeEdit(@PathVariable("id") Long id, @Valid BoardDTO boardDTO, @RequestParam("files") MultipartFile[] files){

        List<FileDTO> existingFiles = fileService.findByBoardId(id);
        for (FileDTO file : existingFiles) {
            file.setBoardId(id);
        }

        try {
            BoardDTO boardDTO1 = boardService.getBoard(id);
            boardDTO1.setTitle(boardDTO.getTitle());
            boardDTO1.setContent(boardDTO.getContent());
            boardDTO1.setPinned(boardDTO.isPinned());
            boardDTO1.setPrivated(boardDTO.isPrivated());
            boardDTO1.setWriter(boardDTO.getWriter());
            boardDTO1.setBoardType("NOTICE");
            boardService.modify(boardDTO1);

            List<FileDTO> uploadFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originFilename = file.getOriginalFilename();
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

                    file.transferTo(new File(filePath));

                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setOriginFileName(originFilename);
                    fileDTO.setFileName(filename);
                    fileDTO.setFilePath(filePath);
                    fileDTO.setBoardId(id);

                    uploadFiles.add(fileDTO);
                }
            }

            List<FileDTO> allFiles = new ArrayList<>();
            allFiles.addAll(existingFiles);
            allFiles.addAll(uploadFiles);

            List<Long> fileIds = fileService.saveFiles(allFiles);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/notice/read?id="+id;
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@RequestBody Map<String, Long> requestBody) {

        Long fileId = requestBody.get("fileId");

        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok().body("파일이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/remove")
    public String remove(Long id, boolean deleteType) {

        BoardDTO board = boardService.getBoard(id);

        board.setDeleteType(deleteType);
        boardService.modify(board);

        return "redirect:/notice/list";
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
        model.addAttribute("searchTotal", boardList.getTotalElements());

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
