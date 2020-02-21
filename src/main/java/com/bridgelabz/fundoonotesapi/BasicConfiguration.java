package com.bridgelabz.fundoonotesapi;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tejashree Surve
 * Purpose : Contains Beans Definition so that spring IOC container can create object.
 */
@Configuration
public class BasicConfiguration {

	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}
