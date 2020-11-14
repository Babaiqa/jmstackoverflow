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
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverYear(int page, int size) {
        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto = userDtoDao.getPageUserDtoListByReputationOverPeriodWithoutTags(page, size, 365);
        List<Long> usersIds = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoListOnlyTagsDto = userDtoDao.getListTagDtoWithTagsPeriodWithOnlyTags(usersIds, 365);

        return getUserDtoListObjectPageDto(page, size, pageDto, listUserDtoWithoutTagsDto, listUserDtoListOnlyTagsDto);
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverWeek(int page, int size) {
        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto = userDtoDao.getPageUserDtoListByReputationOverPeriodWithoutTags(page, size, 7);
        List<Long> usersIds = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoListOnlyTagsDto = userDtoDao.getListTagDtoWithTagsPeriodWithOnlyTags(usersIds, 7);

        return getUserDtoListObjectPageDto(page, size, pageDto, listUserDtoWithoutTagsDto, listUserDtoListOnlyTagsDto);
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputation(int page, int size) {
        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto = userDtoDao.getPageUserDtoListByReputationWithoutTags(page, size);
        List<Long> usersIds = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoListOnlyTagsDto = userDtoDao.getListTagDtoWithTagsWithOnlyTags(usersIds);

        return getUserDtoListObjectPageDto(page, size, pageDto, listUserDtoWithoutTagsDto, listUserDtoListOnlyTagsDto);
    }

    private List<UserDtoList> addTagsDtoToUserDtoList(List<UserDtoList> listUserDto,
                                                      List<UserDtoList> listUserDtoListOnlyTagsDto) {
        Map<Long, UserDtoList> map = new HashMap<>();
        for (UserDtoList u : listUserDtoListOnlyTagsDto) {
            map.put(u.getId(), u);
        }

        for (UserDtoList u : listUserDto) {
            if (map.containsKey(u.getId())) {
                u.setTags(map.get(u.getId()).getTags().stream().limit(3).collect(Collectors.toList()));
            } else {
                u.setTags(new ArrayList<>());
            }
        }
        return listUserDto;
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverMonth(int page, int size) {
        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto = userDtoDao.getPageUserDtoListByReputationOverPeriodWithoutTags(page, size, 30);
        List<Long> usersIdsPage = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoOnlyTagsDto = userDtoDao.getListTagDtoWithTagsPeriodWithOnlyTags(usersIdsPage, 30);

        return getUserDtoListObjectPageDto(page, size, pageDto, listUserDtoWithoutTagsDto, listUserDtoOnlyTagsDto);
    }

    private PageDto<UserDtoList, Object> getUserDtoListObjectPageDto(int page, int size, PageDto<UserDtoList, Object> pageDto, List<UserDtoList> listUserDtoWithoutTagsDto, List<UserDtoList> listUserDtoOnlyTagsDto) {
        pageDto.setItems(addTagsDtoToUserDtoList(listUserDtoWithoutTagsDto, listUserDtoOnlyTagsDto));

        int totalResultCount = userDtoDao.getTotalResultCountUsers();
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByName(int page, int size, String name) {

        PageDto<UserDtoList, Object> pageDto = new PageDto<>();

        List<UserDtoList> listUserDtoWithoutTagsDto = userDtoDao.getPageUserDtoListByNameWithoutTags(page, size, name);
        List<Long> usersIdsPage = listUserDtoWithoutTagsDto.stream().map(UserDtoList::getId).collect(Collectors.toList());

        List<UserDtoList> listUserDtoOnlyTagsDto = userDtoDao.getListTagDtoByUserNameWithOnlyTags(usersIdsPage, name);
        return getUserDtoListByNameObjectPageDto(page, size, name, pageDto, listUserDtoWithoutTagsDto, listUserDtoOnlyTagsDto);
    }

    private PageDto<UserDtoList, Object> getUserDtoListByNameObjectPageDto(int page, int size, String name, PageDto<UserDtoList, Object> pageDto, List<UserDtoList> listUserDtoWithoutTagsDto, List<UserDtoList> listUserDtoOnlyTagsDto) {
        pageDto.setItems(addTagsDtoToUserDtoList(listUserDtoWithoutTagsDto, listUserDtoOnlyTagsDto));

        int countUsersByName = userDtoDao.getCountUsersByName(name);
        pageDto.setTotalResultCount(countUsersByName);
        pageDto.setCurrentPageNumber(page);
        pageDto.setItemsOnPage(size);
        pageDto.setTotalPageCount((int) Math.ceil(countUsersByName / (double) size));

        return pageDto;
    }

}
