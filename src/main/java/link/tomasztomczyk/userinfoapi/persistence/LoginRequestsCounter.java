package link.tomasztomczyk.userinfoapi.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginRequestsCounter {
    private final LoginRequestsRepository repository;

    @Autowired
    public LoginRequestsCounter(LoginRequestsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void inc(String login) {
        repository.findById(login).ifPresentOrElse(
                entity->{
                    entity.setCount(entity.getCount()+1);
                    repository.save(entity);
                },
                ()->repository.save(new LoginCounter(login, 1))
        );
    }
}
