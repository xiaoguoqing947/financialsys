package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.BookMark;

import java.util.List;

public interface BookMarkSer {
    List<BookMark> findList(String username);

    boolean addBookMark(BookMark bookMark);

    boolean deleteBookMark(String id);

    boolean updateBookMark(BookMark bookMark);
}
