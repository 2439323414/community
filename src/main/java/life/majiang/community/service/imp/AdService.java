package life.majiang.community.service.imp;

import life.majiang.community.model.Advertisement;
import life.majiang.community.repository.AdvertismentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdvertismentRepository advertismentRepository;

    public List<Advertisement> list(){
        return advertismentRepository.findAll();
    }
}
