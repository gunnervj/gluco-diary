package com.gluco.diary.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HazelCastConfig {
	@Value("${token.validity}")
	private Integer timeToLiveInMilliSeconds;
	
	@Value("${token.idle.validity}")
	private Integer idleTimeToLiveInMilliSeconds;
	
	@Value("${hazelcast.mgmt.cent.url}")
	private String managementCenterUrl;
	
	@Value("${HZ_CAST_MGMGT_PASSWORD}")
	private String hzPassword;
	
	
	@Value("${HZ_CAST_MGMGT_USER}")
	private String hzUser;
	
	@Bean
    public Config configureHazlecast(){
		Config config = new Config();
		log.debug("timeToLiveInMilliSeconds=>>>>>>>>>>>>>>>>>>" + timeToLiveInMilliSeconds);
		log.debug("idleTimeToLiveInMilliSeconds=>>>>>>>>>>>>>>>>>>" + idleTimeToLiveInMilliSeconds);
		log.debug("managementCenterUrl=>>>>>>>>>>>>>>>>>>" + managementCenterUrl);
		log.debug("hzUser=>>>>>>>>>>>>>>>>>>" + hzUser);
		config.setInstanceName("user-auth-tokens")
			.addMapConfig(new MapConfig()
							.setName("hz-configuration")
							.setMaxSizeConfig(new MaxSizeConfig(100, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
							.setEvictionPolicy(EvictionPolicy.LRU)
							.setTimeToLiveSeconds(timeToLiveInMilliSeconds/1000)
							.setMaxIdleSeconds(idleTimeToLiveInMilliSeconds/1000));
		config.getGroupConfig()
				.setName(hzUser.toString())
				.setPassword(hzPassword.toString());
		ManagementCenterConfig mgmtCentConfig = new ManagementCenterConfig()
												.setUrl(managementCenterUrl)
												.setUpdateInterval(5)
												.setEnabled(true);
		config.setManagementCenterConfig(mgmtCentConfig);

		
		return config;
	}
}
