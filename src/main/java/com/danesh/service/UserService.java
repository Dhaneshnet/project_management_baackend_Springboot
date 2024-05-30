package com.danesh.service;

import com.danesh.modal.User;

public interface UserService {

    User findUserProfileByJwt(String jwt)throws Exception;
    User findUserBYEmail(String email)throws Exception;

    User findUserById (Long userId)throws Exception;
    User updateUserProjectSize(User user ,int number);


}
