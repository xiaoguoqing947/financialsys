package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.User;
import org.apache.ibatis.annotations.Param;

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

}