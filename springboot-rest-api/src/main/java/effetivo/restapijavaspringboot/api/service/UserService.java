package effetivo.restapijavaspringboot.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.exception.BadRequestException;
import effetivo.restapijavaspringboot.api.mapper.UserMapper;
import effetivo.restapijavaspringboot.api.repository.UserRepository;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;
import effetivo.restapijavaspringboot.api.request.UserPutRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> listAllNonPageable() {
        return userRepository.findAll();
    }

    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public User findByIdOrThrowBadRequestException(long id) {
        /*
         * return userRepository.findById(id) .orElseThrow(() -> new
         * ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
         */
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));

    }

    @Transactional
    public User save(UserPostRequestBody userPostRequestBody) {
        return userRepository.save(UserMapper.INSTANCE.toUser(userPostRequestBody));
    }

    public void replace(UserPutRequestBody userPutRequestBody) {
        findByIdOrThrowBadRequestException(userPutRequestBody.getId());
        userRepository.save(UserMapper.INSTANCE.toUser(userPutRequestBody));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

}
