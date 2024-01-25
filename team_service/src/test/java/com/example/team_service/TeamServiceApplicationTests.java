package com.example.team_service;

import com.example.team_service.services.impl.ParticipantServiceImplTest;
import com.example.team_service.services.impl.TeamServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ParticipantServiceImplTest.class,
    TeamServiceImplTest.class
})
public class TeamServiceApplicationTests {


}
