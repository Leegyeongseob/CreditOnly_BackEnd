package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/save/{coupleName}")
    public ResponseEntity<BoardResDto> createBoard(@PathVariable String coupleName, @RequestBody BoardReqDto boardReqDto ) {
        BoardResDto createdBoard = boardService.createBoard(boardReqDto, coupleName);
        return ResponseEntity.ok(createdBoard);
    }

    // 게시글 조회
    @GetMapping("/load")
    public ResponseEntity<Page<BoardResDto>> getAllBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BoardResDto> boards = boardService.getAllBoards(page, size);
        return ResponseEntity.ok(boards);
    }
    // 게시글 커플이름으로 조회
    @GetMapping("/couple/{coupleName}")
    public ResponseEntity<List<BoardResDto>> getBoardByCoupleName(@PathVariable String coupleName) {
        List<BoardResDto> boardList = boardService.getBoardByCoupleName(coupleName);
        return ResponseEntity.ok(boardList);
    }

    // 게시글 상세보기
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardResDto> getBoardDetail(@PathVariable Long id) {
        BoardResDto boardDetail = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardDetail);
    }

    // 게시글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<BoardResDto> updateBoard(@PathVariable Long id, @RequestBody BoardReqDto boardReqDto) {
        BoardResDto updatedBoard = boardService.updateBoard(id, boardReqDto);
        return ResponseEntity.ok(updatedBoard);
    }

    // 게시글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteBoard(@RequestParam Long id) {
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }
    //id로 board 정보 가져오기
    @PostMapping("/fetchBoardById")
    public ResponseEntity<BoardResDto> fetchBoardById(@RequestBody Map<String,Long> id){
        return ResponseEntity.ok(boardService.fetchBoardById(id.get("id")));
    }

    // 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
