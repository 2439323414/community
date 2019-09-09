package life.majiang.community.service.imp;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.repository.CommentRepository;
import life.majiang.community.repository.QuestionRepository;
import life.majiang.community.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void save(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Optional<Comment> dbComment = commentRepository.findByParentId(comment.getParentId());
            if (!dbComment.isPresent()){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NO_FOUND);
            }
            commentRepository.save(comment);
        }else {
            //回复问题
            Optional<Question> question = questionRepository.findById(comment.getParentId());
            if (!question.isPresent()){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentRepository.save(comment);
            questionRepository.incCommentCount(question.get().getId());
        }
    }

    public List<CommentDTO> listByQuestionId(Integer id) {
        List<Comment> comments = commentRepository.findByParentIdAndType(id, CommentTypeEnum.QUESTION.getType());

        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<String> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<String> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人转化为map
        List<User> users = userRepository.findByAccountId(userIds);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));
        //转换 comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
