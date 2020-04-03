package com.board.web.controller;

import com.board.web.dto.BoardDto;
import com.board.web.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;


    /* 게시글 목록 */
    @GetMapping("/home")
    public String home() {

        return "board/home.html";
    }
    /* 게시글 목록 */
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list.html";
    }



    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model,HttpServletRequest request,
                         HttpServletResponse response) {

        BoardDto boardDTO = this.boardService.getPost(no);

        int countCheck = 0;

        Cookie[] cookies = request.getCookies(); //쿠키를 조회

        if (cookies != null) { //만약 쿠키가 있
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("count"+boardDTO.getId())){
                    countCheck = 0;
                    break;
                }else{
                    Cookie cookie = new Cookie("count"+boardDTO.getId(), String.valueOf(boardDTO.getId()));
                    cookie.setMaxAge(60*60*24);
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    countCheck += 1;
                }
            }
        }
        //상세정보 조회시 카운트 증가
        if(countCheck > 0){
            boardService.viewCount(no);
        }

        model.addAttribute("boardDTO", boardDTO);
        return "board/detail.html";
    }


    @PostMapping("/recommend/{no}")
    public String recommending(@PathVariable("no") Long no){
        BoardDto boardDTO = boardService.getPost(no);
        boardService.recommending(boardDTO);
        return "board/list.html";
    }


    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);
        return "redirect:/post/{no}";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list.html";
    }



}