package engine.completions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    @Autowired
    CompletionRepository repository;

    public void save(Completion completion) {
        repository.save(completion);
    }

    public Page<Completion> findCompleted(String userName, Pageable pageable) {
        return repository.findAllByUserName(userName, pageable);
    }
}
