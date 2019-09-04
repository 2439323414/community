package life.majiang.community.service.imp;

import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import life.majiang.community.repository.CommentRepository;
import life.majiang.community.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuestionRepository questionRepository;
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
}
