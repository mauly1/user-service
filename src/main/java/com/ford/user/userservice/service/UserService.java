package com.ford.user.userservice.service;

import com.ford.user.userservice.entity.User;
import com.ford.user.userservice.repository.UserRepository;
import com.ford.user.userservice.vo.Department;
import com.ford.user.userservice.vo.ResponseTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVo getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment method of UserService is called with userId "+userId);
        ResponseTemplateVo responseTemplateVo = new ResponseTemplateVo();
        User user = userRepository.findByUserId(userId);

        Department department = restTemplate.
                getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(),
                        Department.class);
        log.info("Inside getUserWithDepartment method of UserService is called with userId"+userId+"" +
                " and return department name is "+department.getDepartmentName() +" "+department.getDepartmentId() );
        responseTemplateVo.setUser(user);
        responseTemplateVo.setDepartment(department);
        return responseTemplateVo;
    }
}
