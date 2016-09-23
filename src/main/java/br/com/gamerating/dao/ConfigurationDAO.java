package br.com.gamerating.dao;

public interface ConfigurationDAO {

	int getDateConfiguration();
	void updateDateConfiguration(String days, String user);

}
