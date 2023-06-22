package com.example.board.boardController.service.Impl;

import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.entity.Reaction;
import com.example.board.memberController.entity.ReactionEnum;
import com.example.board.memberController.repository.ReactionRepository;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final ReactionRepository reactionRepository;


    @Override
    @Transactional
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional
    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }


    // 게시글 생성
    @Override
    @Transactional
    public Long board(Long id
            , CategoryEnum category
            , String title
            , String contents) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        Board board = new Board();
        board.createBoard(member, category, title, contents);
        boardRepository.save(board);

        return board.getId();
    }


    @Override
    @Transactional
    public Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));

        board.setCategory(category);
        board.setTitle(updatedTitle);
        board.setContents(updatedContents);
        board.setUpdateDate(LocalDateTime.now());

        return board.getId();
    }

    @Override
    @Transactional
    public boolean deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        board.getMember().getBoardList().removeIf(targetBoard -> targetBoard.equals(board));


        boardRepository.deleteById(boardId);
        return true;
    }

    @Override
    public Board findOne(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }


    @Override
    @Transactional
    public Board toggleReaction(Long boardId, Long id, ReactionEnum reactionEnum) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Reaction existingReaction = findReaction(member, board);

        if (existingReaction != null) {
            // 이미 반응이 있는 경우
            if (existingReaction.getReactionEnum() == reactionEnum) {
                // 이미 같은 종류의 반응이면 반응을 제거합니다.
                board.getReactions().remove(existingReaction);
                reactionRepository.delete(existingReaction);

                if (reactionEnum == ReactionEnum.LIKE) {
                    board.decrementLikes();
                } else if (reactionEnum == ReactionEnum.DISLIKE) {
                    board.decrementDislikes();
                }
            } else {
                // 다른 종류의 반응이면 반응을 업데이트합니다.
                existingReaction.setReactionEnum(reactionEnum);
                reactionRepository.save(existingReaction);

                if (reactionEnum == ReactionEnum.LIKE) {
                    board.incrementLikes();
                    board.decrementDislikes();
                } else if (reactionEnum == ReactionEnum.DISLIKE) {
                    board.incrementDislikes();
                    board.decrementLikes();
                }
            }
        } else {
            // 반응이 없는 경우 새로운 반응을 추가합니다.
            Reaction newReaction = new Reaction();
            newReaction.setMember(member);
            newReaction.setBoard(board);
            newReaction.setReactionEnum(reactionEnum);

            board.getReactions().add(newReaction);
            reactionRepository.save(newReaction);

            if (reactionEnum == ReactionEnum.LIKE) {
                board.incrementLikes();
            } else if (reactionEnum == ReactionEnum.DISLIKE) {
                board.incrementDislikes();
            }
        }

        return board;
    }


    private Reaction findReaction(Member member, Board board) {
        return board.getReactions().stream()
                .filter(reaction -> reaction.getMember().equals(member))
                .findFirst()
                .orElse(null);
    }



}
