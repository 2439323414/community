package life.majiang.community.schedule;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.model.Question;
import life.majiang.community.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private HotTagCache hotTagCache;
    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0 1 * * *")
    public void hotTagSchedule(){
        log.info("The time is now {}",new Date());
        List<Question> list = new ArrayList<>();
        Map<String,Integer> priorities = new HashMap<>();
        list=questionRepository.findAll();
        for (Question question : list) {
            String[] tags = StringUtils.split(question.getTag(), ",");
            for (String tag : tags) {
                Integer priority = priorities.get(tag);
                if (priority !=null){
                    priorities.put(tag,priority+5+question.getCommentCount());
                }else {
                    priorities.put(tag,5+question.getCommentCount());
                }
            }
        }
        hotTagCache.setTags(priorities);
//        hotTagCache.getTags().forEach(
//                (k,v)->{
//                    System.out.print(k);
//                    System.out.print(":");
//                    System.out.print(v);
//                    System.out.println();
//                }
//        );
        log.info("The time is end {}",new Date());
        hotTagCache.updateTags(priorities);
    }
}
