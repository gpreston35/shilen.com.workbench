package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shilen.app.workbench.model.admin.AdminRole;
import com.shilen.app.workbench.model.admin.AdminUser;

public interface AdminUserMapper {

	@Select("SELECT u.id, u.username, u.enabled, u.first_name, u.last_name, u.sms_email, "
			+ "       group_concat(r.role order by r.role separator ', ') roles "
			+ "  FROM operations.user u "
			+ "  LEFT JOIN operations.user_role_mapping m ON m.user_id = u.id "
			+ "  LEFT JOIN operations.user_role r ON r.id = m.user_role_id "
			+ " WHERE upper(u.username) like concat('%', upper(#{searchTerm}), '%') "
			+ "    OR upper(ifnull(u.first_name, '')) like concat('%', upper(#{searchTerm}), '%') "
			+ "    OR upper(ifnull(u.last_name, '')) like concat('%', upper(#{searchTerm}), '%') "
			+ " GROUP BY u.id, u.username, u.enabled, u.first_name, u.last_name, u.sms_email "
			+ " ORDER BY u.username")
	List<AdminUser> searchUsers(@Param("searchTerm") String searchTerm);

	@Select("SELECT id, username, enabled, first_name, last_name, sms_email "
			+ "  FROM operations.user "
			+ " WHERE id = #{id}")
	AdminUser readUser(@Param("id") int id);

	@Select("SELECT id, role FROM operations.user_role ORDER BY role")
	List<AdminRole> listRoles();

	@Select("SELECT user_role_id "
			+ "  FROM operations.user_role_mapping "
			+ " WHERE user_id = #{userId} "
			+ " ORDER BY user_role_id")
	List<Integer> readUserRoleIds(@Param("userId") int userId);

	@Select("SELECT count(*) FROM operations.user WHERE username = #{username}")
	int countByUsername(@Param("username") String username);

	@Insert("INSERT INTO operations.user "
			+ "       (username, password, enabled, first_name, last_name, sms_email) "
			+ "VALUES (#{username}, #{password}, #{enabled}, #{first_name}, #{last_name}, #{sms_email})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void insertUser(AdminUser user);

	@Update("UPDATE operations.user "
			+ "   SET enabled = #{enabled}, "
			+ "       first_name = #{first_name}, "
			+ "       last_name = #{last_name}, "
			+ "       sms_email = #{sms_email} "
			+ " WHERE id = #{id}")
	void updateUser(AdminUser user);

	@Update("UPDATE operations.user "
			+ "   SET password = #{password} "
			+ " WHERE id = #{id}")
	void updatePassword(@Param("id") int id, @Param("password") String password);

	@Delete("DELETE FROM operations.user_role_mapping WHERE user_id = #{userId}")
	void deleteUserRoles(@Param("userId") int userId);

	@Insert("INSERT INTO operations.user_role_mapping (user_id, user_role_id) "
			+ "VALUES (#{userId}, #{roleId})")
	void insertUserRole(@Param("userId") int userId, @Param("roleId") int roleId);
}
