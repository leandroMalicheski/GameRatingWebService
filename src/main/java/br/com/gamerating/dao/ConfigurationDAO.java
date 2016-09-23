package br.com.gamerating.dao;

import br.com.gamerating.bean.RateConfiguration;

public interface ConfigurationDAO {

	int getDateConfiguration();
	void updateDateConfiguration(String days, String user);
	RateConfiguration getRateConfiguration();
	void updateRateConfiguration(RateConfiguration rateConfig);

}
