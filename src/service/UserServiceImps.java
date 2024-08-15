package service;

import java.util.Map;
import java.util.UUID;

import dao.UserDao;
import dao.UserDaoImps;
import pojo.UserPojo;

public class UserServiceImps implements UserService {
	private static UUID currentUserUuid;
	private static final String ADMIN_EMAIL = "admin@example.com";
	private static final String ADMIN_PASSWORD = "adminpassword";
	
	UserDao userDao;
	
	public UserServiceImps() {
		userDao = new UserDaoImps();
	}

	@Override
	public boolean loginAsync(String email, String password) {
		if(email == null && password == null) {
			return false;
		}
		if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
			setCurrentUserUuid(null); // Admin user does not have UUID
			return true;
		}
		boolean User = userDao.login(email,password);
		if (User) {
			setCurrentUserUuid(userDao.getUuid(email));
			return true;
		}
		return false;
	}

	@Override
	public UserPojo registerAsync(String email, String password) {
		UserPojo userRegistration = userDao.register(email, password);
		if (userRegistration == null) {
			return null;
		}
		return userRegistration;
	}

	@Override
	public Map<String, UserPojo> getAllUsersAsync() {
		return userDao.getAllUsers();
	}

	@Override
	public UserPojo getUserAsync(String email) {
		return userDao.getUser(email);
	}

	@Override
	public UserPojo updateUserAsync(String email, String newPassword) {
		return userDao.updateUser(email, newPassword);
	}

	@Override
	public boolean deleteUserAsync(String email) {
		return userDao.deleteUser(email);
	}

	@Override
	public UUID getCurrentUserUuidAsync() {
		return getCurrentUserUuid() ;
	}

	public static UUID getCurrentUserUuid() {
		return currentUserUuid;
	}

	public static void setCurrentUserUuid(UUID currentUserUuid) {
		UserServiceImps.currentUserUuid = currentUserUuid;
	}
	
}
