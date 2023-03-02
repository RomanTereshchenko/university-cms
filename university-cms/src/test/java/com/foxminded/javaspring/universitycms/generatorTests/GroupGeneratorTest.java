package com.foxminded.javaspring.universitycms.generatorTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.universitycms.generator.GroupGenerator;

@SpringBootTest
@Transactional
class GroupGeneratorTest {
	
	@Autowired
	private GroupGenerator groupGenerator;
	
	@Test
	void testGenerateNGroups() {
		System.out.println(groupGenerator.generateNGroups(5));
		assertNotNull(groupGenerator.generateNGroups(1));
	}

}
