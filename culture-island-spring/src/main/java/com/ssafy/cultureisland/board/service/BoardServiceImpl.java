package com.ssafy.cultureisland.board.service;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import com.ssafy.cultureisland.board.model.BoardMapper;
import com.ssafy.cultureisland.board.model.CommentDto;
import com.ssafy.cultureisland.board.model.CommentMapper;
import com.ssafy.cultureisland.board.model.FileInfoDto;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardMapper boardMapper;
    private CommentMapper commentMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, CommentMapper commentMapper) {
        this.boardMapper = boardMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public BoardListDto listArticle(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "6" : map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;
        param.put("start", start);
        param.put("listSize", sizePerPage);

        String type = map.get("type");
        if (type.equals("0") || type.equals("1")) {
            param.put("pay", Integer.parseInt(type));
        } else {
            param.put("type", type);
        }

        String key = map.get("key");
        param.put("key", "subject");

        // 회원 ID 추가
        String memberId = map.get("memberId");
        if (memberId != null && !memberId.isEmpty()) {
            param.put("memberId", memberId);
        }

        List<BoardDto> list = new ArrayList<>();
        int totalArticleCount;
        int totalPageCount;

        if (map.get("my") != null && map.get("my").equals("true")) {
            list = boardMapper.memberListArticle(param);
            totalArticleCount = boardMapper.getMemberTotalArticleCount(param);
            totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
        } else {
            list = boardMapper.listArticle(param);
            totalArticleCount = boardMapper.getTotalArticleCount(param);
            totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
        }
        BoardListDto boardListDto = new BoardListDto();
        boardListDto.setArticles(list);
        boardListDto.setCurrentPage(currentPage);
        boardListDto.setTotalPageCount(totalPageCount);

        return boardListDto;
    }


    @Override
    public void writeArticle(BoardDto boardDto) throws Exception {
        System.out.println(boardDto);
        boardMapper.writeArticle(boardDto);
        List<FileInfoDto> fileInfos = boardDto.getFileInfos();
        if (fileInfos != null && !fileInfos.isEmpty()) {
            boardMapper.registerFile(boardDto);
        }
    }

    @Override
    public BoardDto getArticle(int articleNo) throws Exception {
        return boardMapper.getArticle(articleNo);
    }

    @Override
    public List<FileInfoDto> getFileList(int articleNo) throws Exception {
        System.out.println("articleNO : " + articleNo);
        return boardMapper.getFileList(articleNo);
    }

    @Override
    public void deleteArticle(int articleNo) throws Exception {
        boardMapper.deleteArticle(articleNo);
    }

    @Override
    public void updateHit(int articleNo) throws Exception {
        boardMapper.updateHit(articleNo);
    }

    @Override
    public void writeComment(CommentDto commentDto) throws Exception {
        commentMapper.writeComment(commentDto);
    }

    @Override
    public List<CommentDto> listComment(int articleNo) throws Exception {
        return commentMapper.listComment(articleNo);
    }

    @Override
    public void modifyArticle(BoardDto boardDto) throws Exception {
        boardMapper.modifyArticle(boardDto);
    }

}
