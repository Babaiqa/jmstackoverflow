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
    public PageDto<UserDtoList,Object> getPageUserDtoListByName(int page, int size, String name){

        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        int countUsersByName = userDtoDao.getCountUsersByName(name);

        pageDto.setItems(userDtoDao.getPageUserDtoListByName(page, size, name));
        pageDto.setTotalResultCount(countUsersByName);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(countUsersByName / (double) size));

        return pageDto;
    }

}
