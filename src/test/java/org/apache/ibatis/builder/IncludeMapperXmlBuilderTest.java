/*
  *    Copyright 20092012 the original author or authors.
  *
  *    Licensed under the Apache License, Version 2.0 (the "License");
  *    you may not use this file except in compliance with the License.
  *    You may obtain a copy of the License at
  *
  *       http://www.apache.org/licenses/LICENSE2.0
  *
  *    Unless required by applicable law or agreed to in writing, software
  *    distributed under the License is distributed on an "AS IS" BASIS,
  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *    See the License for the specific language governing permissions and
  *    limitations under the License.
  */
package org.apache.ibatis.builder;

import java.io.InputStream;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jasper de Vries
 */
public class IncludeMapperXmlBuilderTest {

	@Test
	public void shouldSuccessfullyLoadIncludeXMLMapperFile() throws Exception {
		Configuration configuration = new Configuration();
		String resource = "org/apache/ibatis/builder/IncludeMapper.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
		builder.parse();

		String sqlOutput = configuration.getMappedStatement("com.domain.IncludeMapper.selectInclude").getSqlSource().getBoundSql(null).getSql();

		System.out.println(sqlOutput);

		String[] lines = sqlOutput.split("\n");
		Assert.assertEquals("w.a as a, w.b as b, w.c as c", lines[2].trim());
		Assert.assertEquals(",", lines[3].trim());
		Assert.assertEquals("x.a as a, x.b as b, x.c as c ,", lines[4].trim());
		Assert.assertEquals("y.a as q_a, y.b as q_b, y.c as q_c ,", lines[5].trim());
		Assert.assertEquals("z.p as z, z.q as y, z.r as r", lines[6].trim());
	}

}
