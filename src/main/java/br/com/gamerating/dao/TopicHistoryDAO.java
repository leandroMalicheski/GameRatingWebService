package br.com.gamerating.dao;

import br.com.gamerating.bean.Topic;

public interface TopicHistoryDAO {

	void addEditInfo(Topic newTopicInfo, Topic oldTopicInfo, String userResponsible);

}
