package service;

import java.util.Map;
import java.util.UUID;

import pojo.UserPojo;

public interface UserService {
	boolean loginAsync (String email, String password);
	UserPojo registerAsync (String email, String password);
	Map<String,UserPojo> getAllUsersAsync();
	UserPojo getUserAsync(String email);
	UserPojo updateUserAsync(String email, String newPassword);
	boolean deleteUserAsync(String email);
	UUID getCurrentUserUuidAsync();
	

}
