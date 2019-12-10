package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.BookMark;
import cn.xgq.financialsys.mapping.BookMarkMapper;
import cn.xgq.financialsys.service.inter.BookMarkSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookMarkSerImpl implements BookMarkSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookMarkSerImpl.class);
    @Resource
    private BookMarkMapper bookMarkMapper;

    @Override
    public List<BookMark> findList(String username) {
        return bookMarkMapper.findList(username);
    }

    @Override
    public boolean addBookMark(BookMark bookMark) {
        int num = 0;
        try {
            num = bookMarkMapper.insertSelective(bookMark);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
