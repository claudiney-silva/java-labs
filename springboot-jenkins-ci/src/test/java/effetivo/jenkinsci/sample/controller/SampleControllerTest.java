package effetivo.jenkinsci.sample.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import effetivo.jenkinsci.sample.domain.Auth;

@ExtendWith(SpringExtension.class)
public class SampleControllerTest {

    @InjectMocks
    private SampleController sampleController;    

    @Test
    @DisplayName("findAll returns list of auth when successful")
    void findAll_ReturnsListOfAuth_WhenSuccessful() {
        List<Auth> auths = sampleController.findAll().getBody();
        Assertions.assertThat(auths).isNotNull().isNotEmpty().hasSize(2);
    }    
    
}
