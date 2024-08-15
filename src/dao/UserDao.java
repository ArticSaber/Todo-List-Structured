package dao;
import pojo.UserPojo;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
public interface UserDao {
	boolean login (String email, String password);
	UserPojo register (String email, String password);
	Map<String, UserPojo> getAllUsers();
	UserPojo getUser(String email);
	UserPojo updateUser(String email, String newPassword);
	boolean deleteUser(String email);
	UUID getUuid(String email);
}
