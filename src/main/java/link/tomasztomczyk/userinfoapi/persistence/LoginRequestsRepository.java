package link.tomasztomczyk.userinfoapi.persistence;

import org.springframework.data.repository.CrudRepository;

public interface LoginRequestsRepository extends CrudRepository<LoginCounter, String> {
}
