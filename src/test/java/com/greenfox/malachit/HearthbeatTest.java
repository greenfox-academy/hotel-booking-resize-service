package com.greenfox.malachit;

import com.greenfox.malachit.model.HealthCheck;
import com.greenfox.malachit.repository.HealthCheckRepository;
import com.greenfox.malachit.service.HearthBeatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HearthbeatTest {

  @Test
  public void getHearthbeatError() {
    ArrayList<HealthCheck> toReturn = new ArrayList<>();
    HealthCheckRepository mockedHealthCheckRepository = Mockito.mock(HealthCheckRepository.class);
    Mockito.when(mockedHealthCheckRepository.findAllByOrderById())
            .thenReturn(toReturn);
    HearthBeatService hearthBeatServiceUnderTest = new HearthBeatService(mockedHealthCheckRepository);
    assertEquals(hearthBeatServiceUnderTest.healthStatus().getDatabase(), "error");
  }

  @Test
  public void getHearthbeatOk() {
    ArrayList<HealthCheck> toReturn = new ArrayList<>();
    toReturn.add(new HealthCheck(true));
    HealthCheckRepository mockedHealthCheckRepository = Mockito.mock(HealthCheckRepository.class);
    Mockito.when(mockedHealthCheckRepository.findAllByOrderById())
            .thenReturn(toReturn);
    HearthBeatService hearthBeatServiceUnderTest = new HearthBeatService(mockedHealthCheckRepository);
    assertEquals(hearthBeatServiceUnderTest.healthStatus().getDatabase(), "ok");
  }

}