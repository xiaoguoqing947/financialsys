package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.BookMark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BookMark record);

    int insertSelective(BookMark record);

    BookMark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BookMark record);

    int updateByPrimaryKey(BookMark record);

    List<BookMark> findList(@Param("un") String username);
}