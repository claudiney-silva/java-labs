package br.com.effetivo.account.testcontainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.effetivo.account.testcontainer.config.ContainerEnvironment;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestContainer extends ContainerEnvironment {
    
    @DisplayName("When start container expect it running")
    @Test
    public void testContainerExpectRunning()  {
        assertTrue(ContainerEnvironment.cassandraContainer.isRunning());
    }    
}
