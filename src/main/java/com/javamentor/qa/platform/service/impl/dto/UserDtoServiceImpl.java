package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public Optional<UserDto> getUserDtoById(long id) {
        return userDtoDao.getUserById(id);
    }

    @Override
    public PageDto<UserDtoList,Object> getPageUserDtoListByReputationOverPeriod(int page, int size){

        PageDto<UserDtoList,Object> pageDto = new PageDto<>();

        int totalResultCount=userDtoDao.getTotalResultCountUsers();

        pageDto.setItems(userDtoDao.getPageUserDtoListByReputationOverPeriod(page, size));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pageDto;
    }
}
