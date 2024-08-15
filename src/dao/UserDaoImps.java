package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pojo.UserPojo;

public class UserDaoImps implements UserDao {
	private static Map<String, UserPojo> users = new HashMap<>();
	@Override
	public boolean login(String email, String password) {
		UserPojo user = users.get(email);
		if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
	}

	@Override
	public UserPojo register(String email, String password) {
        if (users.containsKey(email)) {
            return null; // User already exists
        }
        UserPojo user = new UserPojo(email, password);
        return users.put(email, user);
	}

	@Override
	public Map<String, UserPojo> getAllUsers() {
		return new HashMap<>(users);
	}

	@Override
	public UserPojo getUser(String email) {
		 return users.get(email);
	}

	@Override
	public UserPojo updateUser(String email, String newPassword) {
		UserPojo user = users.get(email);
        if (user != null) {
            user.setPassword(newPassword);
            return user;
        }
        return null;
	}

	@Override
	public boolean deleteUser(String email) {
		if (users.containsKey(email)) {
            users.remove(email);
            return true;
        }
		return false;
	}

	@Override
	public UUID getUuid(String email) {
		UserPojo user = users.get(email);
		return user.getUuid();
	}


}
