package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public PageDto<UserDtoList,Object> getPageUserDtoListByReputationOverWeek(int page, int size){
        PageDto<UserDtoList,Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto=userDtoDao.getPageUserDtoListByReputationOverPeriodWithoutTags(page, size,7);
        List<Long> usersIdsPage = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoOnlyTagsDto=userDtoDao.getListTagDtoWithTagsPeriodWithoutReputation(usersIdsPage,7);

        pageDto.setItems(addTagsDtoToUserDtoList(listUserDtoWithoutTagsDto,listUserDtoOnlyTagsDto));

        int totalResultCount=userDtoDao.getTotalResultCountUsers();
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount/(double)size));

        return pageDto;
    }



    private List<UserDtoList> addTagsDtoToUserDtoList(List<UserDtoList> listUserDto,
                                                      List<UserDtoList> listUserDtoOnlyTagsDto){
        Map<Long,UserDtoList> map=new HashMap<>();
        for (UserDtoList u:listUserDtoOnlyTagsDto) {
            map.put(u.getId(),u);
        }

        for(UserDtoList u:listUserDto){
            if(map.containsKey(u.getId())) {
                u.setTags(map.get(u.getId()).getTags().stream().limit(3).collect(Collectors.toList()));
            } else {
                u.setTags(new ArrayList<>());
            }
        }
        return listUserDto;
    }
}
