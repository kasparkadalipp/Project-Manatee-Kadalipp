package ee.cyber.manatee.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import ee.cyber.manatee.dto.ApplicationDto;
import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.dto.InterviewDto;
import ee.cyber.manatee.model.Interview;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationDto entityToDto(Application entity);

    Application dtoToEntity(ApplicationDto dto);

    Interview dtoToEntity(InterviewDto dto);

    List<ApplicationDto> entitiesToDtoList(List<Application> entity);
}
