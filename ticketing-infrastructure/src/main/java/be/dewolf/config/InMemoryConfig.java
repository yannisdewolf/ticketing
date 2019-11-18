package be.dewolf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@Profile("data")
public class InMemoryConfig {
	private final String SAMPLE_DATA = "classpath:production-data.sql";
	
	@Autowired
	private DataSource datasource;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void loadIfInMemory() throws Exception {
		Resource resource = applicationContext.getResource(SAMPLE_DATA);
		ScriptUtils.executeSqlScript(datasource.getConnection(), resource);
	}
}
