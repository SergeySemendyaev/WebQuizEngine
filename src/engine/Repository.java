package engine;

import org.springframework.data.repository.PagingAndSortingRepository;

@org.springframework.stereotype.Repository
public interface Repository extends PagingAndSortingRepository<Quiz, Long> {
}
