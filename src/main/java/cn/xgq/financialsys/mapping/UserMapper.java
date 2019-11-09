package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.User;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select({
            "select count(*)",
            "from user",
            "where username=#{un} and",
            "password=#{pw}"
    })
    int queryByLogin(@Param("un") String username, @Param("pw") String password);

    @Select(
            {
                    "select *",
                    "from user",
                    "where username=#{un}"
            })
    User queryUserByUsername(@Param("un") String username);

    @Insert({
            "insert into user",
            "(username,password,email,regist_date)",
            "values(#{u.username},#{u.password},#{u.email},#{u.registDate})"
    })
    int insertUser(@Param("u") User user);

    @Select({
            "select power from user where username=#{un}"
    })
    int queryUserPower(@Param("un") String username);
}