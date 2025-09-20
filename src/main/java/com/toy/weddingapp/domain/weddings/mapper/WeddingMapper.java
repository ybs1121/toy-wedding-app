package com.toy.weddingapp.domain.weddings.mapper;

import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.weddings.dto.WeddingAddRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeddingMapper {
    Weddings toEntity(WeddingAddRequest weddingAddRequest, User user);

    WeddingResponse toDTO(Weddings weddings);
}
