package com.travelapp.backend.tools;

import com.travelapp.backend.entity.Newsletter;
import com.travelapp.backend.entity.User;
import com.travelapp.backend.entity.dtos.NewsletterDto;
import com.travelapp.backend.entity.dtos.UserDto;
import com.travelapp.backend.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    public User mapToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public Newsletter mapToNewsletter(NewsletterDto model) {
        return mapper.map(model, Newsletter.class);
    }

    public NewsletterDto mapToNewsletterDto(Newsletter newsletter) {
        return mapper.map(newsletter, NewsletterDto.class);
    }
}
