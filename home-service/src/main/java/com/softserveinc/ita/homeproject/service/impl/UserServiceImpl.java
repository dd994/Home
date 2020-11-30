package com.softserveinc.ita.homeproject.service.impl;

import com.softserveinc.ita.homeproject.homedata.entity.User;
import com.softserveinc.ita.homeproject.homedata.repository.RoleRepository;
import com.softserveinc.ita.homeproject.homedata.repository.UserRepository;
import com.softserveinc.ita.homeproject.service.UserService;
import com.softserveinc.ita.homeproject.service.dto.CreateUserDto;
import com.softserveinc.ita.homeproject.service.dto.ReadUserDto;
import com.softserveinc.ita.homeproject.service.dto.UpdateUserDto;
import com.softserveinc.ita.homeproject.service.exception.AlreadyExistException;
import com.softserveinc.ita.homeproject.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

import static com.softserveinc.ita.homeproject.service.constants.Roles.USER_ROLE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public ReadUserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("User with email" + createUserDto.getEmail() +" is already exists");
        } else {
            User toCreate = conversionService.convert(createUserDto, User.class);
            toCreate.setEnabled(true);
            toCreate.setExpired(false);
            toCreate.setRoles(Set.of(roleRepository.findByName(USER_ROLE)));
            toCreate.setCreateDate(LocalDateTime.now());

            userRepository.save(toCreate);

            return conversionService.convert(toCreate, ReadUserDto.class);
        }
    }

    @Override
    public ReadUserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        if (userRepository.findById(id).isPresent()) {
            User toUpdate = conversionService.convert(updateUserDto, User.class);
            toUpdate.setUpdateDate(LocalDateTime.now());
            userRepository.save(toUpdate);
            return conversionService.convert(toUpdate, ReadUserDto.class);
        } else {
            throw new NotFoundException("User with id:" + id + " is not found");
        }
    }

    @Override
    public Page<ReadUserDto> getAllUsers(Integer pageNumber, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(pageNumber-1, pageSize))
                .map(user -> conversionService.convert(user, ReadUserDto.class));
    }

    @Override
    public ReadUserDto getUserById(Long id) {
        User toGet = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id:"+ id + " is not found"));
        return conversionService.convert(toGet, ReadUserDto.class);
    }

    @Override
    public void deactivateUser(Long id) {
        User toDelete = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id:" + id + " is not found"));
        toDelete.setEnabled(false);
    }
}
