package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int queryByLogin(@Param("un") String username, @Param("pw") String password);

    User queryUserByUsername(@Param("un") String username);

    int insertUser(@Param("u") User user);

    int queryUserPower(@Param("un") String username);

    int updatePwdByUname(@Param("un") String username, @Param("pw") String password);

    int findUserCount(Map<String, Object> searchMap);

    List<User> findUserList(Map<String, Object> searchMap);

    int deleteUserByUsername(@Param("un") String username);
}