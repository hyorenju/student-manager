package vn.edu.vnua.fita.student.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.vnua.fita.student.entity.dto.PointDTO;
import vn.edu.vnua.fita.student.entity.table.Point;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new PropertyMap<Point, PointDTO>() {
//            @Override
//            protected void configure() {
//                map().setId(source.getId());
//            }
//        });
        return modelMapper;
    }
}
