package com.kh.Palette_BackEnd.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.BoardRepository;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CoupleRepository coupleRepository;

    //게시글 생성 기능
    @Transactional
    public BoardResDto createBoard(BoardReqDto boardReqDto, String coupleName) {
        MemberEntity member = memberRepository.findByEmail(boardReqDto.getMemberEmail())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found"));
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardReqDto.getTitle());
        boardEntity.setContents(boardReqDto.getContents());
        boardEntity.setImgUrl(boardReqDto.getImgUrl());
        boardEntity.setMember(member);
        boardEntity.setCouple(couple);

        BoardEntity savedEntity = boardRepository.save(boardEntity);
        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(savedEntity.getId());
        boardResDto.setTitle(savedEntity.getTitle());
        boardResDto.setRegDate(savedEntity.getRegDate());
        boardResDto.setImgUrl(savedEntity.getImgUrl());
        boardResDto.setContents(savedEntity.getContents());
//        boardResDto.setMemberEmail(savedEntity.getMember().getEmail());

        return boardResDto;
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<BoardResDto> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardEntity> boardEntities = boardRepository.findAll(pageable);
        return boardEntities.map(boardEntity -> {
            BoardResDto boardResDto = new BoardResDto();
            boardResDto.setId(boardEntity.getId());
            boardResDto.setTitle(boardEntity.getTitle());
            boardResDto.setRegDate(boardEntity.getRegDate());
            return boardResDto;
        });
    }
    // 커플 이름으로 게시글 목록 조회
    public List<BoardResDto> getBoardByCoupleName(String coupleName) {
        // 1. 커플 이름으로 CoupleEntity 조회
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found with name: " + coupleName));

        // 2. 해당 커플에 속한 게시글 목록 조회
        List<BoardEntity> boardEntities = boardRepository.findByCouple(couple);

        // 3. BoardEntity 리스트를 BoardResDto 리스트로 변환
        List<BoardResDto> boardResDtoList = boardEntities.stream()
                .map(boardEntity -> {
                    BoardResDto boardResDto = new BoardResDto();
                    boardResDto.setId(boardEntity.getId());
                    boardResDto.setTitle(boardEntity.getTitle());
                    boardResDto.setRegDate(boardEntity.getRegDate());
                    // 필요한 정보 추가 설정
                    return boardResDto;
                })
                .collect(Collectors.toList());

        // 4. BoardResDto 리스트 반환
        return boardResDtoList;
    }



    // 게시글 상세보기
    @Transactional(readOnly = true)
    public BoardResDto getBoardDetail(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id " + id));

        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(boardEntity.getId());
        boardResDto.setTitle(boardEntity.getTitle());
        boardResDto.setContents(boardEntity.getContents());
        boardResDto.setRegDate(boardEntity.getRegDate());
        boardResDto.setImgUrl(boardEntity.getImgUrl());

        return boardResDto;
    }

    // 게시글 수정 기능
    @Transactional
    public BoardResDto updateBoard(Long id, BoardReqDto boardReqDto) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id " + id));

        boardEntity.setTitle(boardReqDto.getTitle());
        boardEntity.setContents(boardReqDto.getContents());
        boardEntity.setImgUrl(boardReqDto.getImgUrl());

        BoardEntity updatedEntity = boardRepository.save(boardEntity);

        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(updatedEntity.getId());
        boardResDto.setTitle(updatedEntity.getTitle());
        boardResDto.setContents(updatedEntity.getContents());
        boardResDto.setRegDate(updatedEntity.getRegDate());
        boardResDto.setImgUrl(updatedEntity.getImgUrl());

        return boardResDto;
    }

    // 게시글 삭제 기능
    @Transactional
    public boolean deleteBoard(Long id) {
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(id);
        if(boardEntityOpt.isPresent()){
            BoardEntity boardEntity = boardEntityOpt.get();
            boardRepository.delete(boardEntity);
            return true;
        }
        else{
            return false;
        }
    }
    // id로 게시글 가져오기
    public BoardResDto fetchBoardById(Long id){
        Optional<BoardEntity> boardEntityOpt = boardRepository.findById(id);
        if(boardEntityOpt.isPresent()){
            BoardEntity boardEntity = boardEntityOpt.get();
            return boardEntity.toBoardResDto();
        }
        else{
            throw new EntityNotFoundException("Board with id " + id + " not found");
        }
    }
}
