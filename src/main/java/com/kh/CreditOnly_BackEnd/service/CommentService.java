package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.constant.Authority;
import com.kh.CreditOnly_BackEnd.dto.reqdto.CommentReqDto;
import com.kh.CreditOnly_BackEnd.dto.resdto.CommentResDto;
import com.kh.CreditOnly_BackEnd.entity.CommentEntity;
import com.kh.CreditOnly_BackEnd.entity.InformationEntity;
import com.kh.CreditOnly_BackEnd.entity.MemberEntity;
import com.kh.CreditOnly_BackEnd.repository.CommentRepository;
import com.kh.CreditOnly_BackEnd.repository.InformationRepository;
import com.kh.CreditOnly_BackEnd.repository.MemberRepository;
import com.kh.CreditOnly_BackEnd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final InformationRepository informationRepository;
    private final MemberRepository memberRepository;

    // 모든 댓글 조회 (관리자 전용)
    @Transactional
    public List<CommentResDto> getAllComments() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!currentMember.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new RuntimeException("모든 댓글 조회 권한이 없습니다.");
        }

        List<CommentEntity> commentEntities = commentRepository.findAll();
        return commentEntities.stream()
                .map(this::mapToResDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResDto createComment(CommentReqDto commentReqDto) {
        // 정보 엔티티 조회
        InformationEntity information = informationRepository.findById(commentReqDto.getInformationId())
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        // 현재 로그인한 사용자의 MemberEntity를 가져옵니다.
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 부모 댓글이 있을 경우 조회
        CommentEntity parentComment = null;
        if (commentReqDto.getParentId() != null) {
            parentComment = commentRepository.findById(commentReqDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("부모 댓글을 찾을 수 없습니다."));
        }

        // 댓글 엔티티 생성 및 저장
        CommentEntity commentEntity = CommentEntity.builder()
                .information(information)
                .member(member)
                .content(commentReqDto.getContent())
                .publishedDate(LocalDateTime.now()) // 현재 시간을 publishedDate로 설정
                .parent(parentComment)
                .build();

        CommentEntity savedComment = commentRepository.save(commentEntity);
        return mapToResDto(savedComment);
    }

    @Transactional
    public CommentResDto updateComment(Long id, CommentReqDto commentReqDto) {
        // 댓글 엔티티 조회
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 현재 로그인한 사용자의 MemberEntity를 가져옵니다.
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 관리자가 아닌 경우 작성자와 현재 사용자를 비교
        if (!currentMember.getAuthority().equals(Authority.ROLE_ADMIN) &&
                !commentEntity.getMember().getId().equals(currentMember.getId())) {
            throw new RuntimeException("댓글 수정 권한이 없습니다.");
        }

        // 댓글 내용 및 수정 날짜 업데이트
        commentEntity.setContent(commentReqDto.getContent());
        commentEntity.setPublishedDate(LocalDateTime.now()); // 댓글 수정 시 현재 시간을 publishedDate로 설정

        // 댓글 저장 및 반환
        CommentEntity updatedComment = commentRepository.save(commentEntity);
        return mapToResDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        // 댓글 엔티티 조회
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 현재 로그인한 사용자의 MemberEntity를 가져옵니다.
        Long memberId = SecurityUtil.getCurrentMemberId();
        MemberEntity currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 관리자가 아닌 경우 작성자와 현재 사용자를 비교
        if (!currentMember.getAuthority().equals(Authority.ROLE_ADMIN) &&
                !commentEntity.getMember().getId().equals(currentMember.getId())) {
            throw new RuntimeException("댓글 삭제 권한이 없습니다.");
        }


        // 댓글 삭제
        commentRepository.delete(commentEntity);
    }

    @Transactional
    public List<CommentResDto> getCommentsByInformationId(Long informationId) {
        List<CommentEntity> commentEntities = commentRepository.findByInformationId(informationId);
        return commentEntities.stream()
                .map(this::mapToResDto)
                .collect(Collectors.toList());
    }

    private CommentResDto mapToResDto(CommentEntity commentEntity) {
        return CommentResDto.builder()
                .id(commentEntity.getId())
                .informationId(commentEntity.getInformation().getId())
                .memberId(commentEntity.getMember().getId())
                .memberName(commentEntity.getMember().getName())
                .memberImg(commentEntity.getMember().getProfileImgUrl())
                .content(commentEntity.getContent())
                .publishedDate(commentEntity.getPublishedDate())
                .parentId(commentEntity.getParent() != null ? commentEntity.getParent().getId() : null)
                .children(commentEntity.getChildren() != null ? commentEntity.getChildren().stream()
                        .map(this::mapToResDto)
                        .collect(Collectors.toSet()) : null)
                .build();
    }
}
