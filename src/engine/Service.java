package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    Repository repository;

    public void save(Quiz quiz) {
        repository.save(quiz);
    }

    public Optional<Quiz> findById(long id) {
        return repository.findById(id);
    }

    public Page<Quiz> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void delete(Quiz quiz) {
        repository.delete(quiz);
    }
}
