package br.com.gamerating.services;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.RateConfiguration;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.dao.ConfigurationDAO;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.impl.ConfigurationDAOImpl;
import br.com.gamerating.dao.impl.TopicDAOImpl;

@RestController
public class SystemToolsServices {
	ConfigurationDAO configurationDAO = ConfigurationDAOImpl.getInstance();
	TopicDAO topicDAO = TopicDAOImpl.getInstance();
	
	@RequestMapping(value="/getDateConfiguration")
    public int getDateConfiguration() {
		return configurationDAO.getDateConfiguration();
    }

	@RequestMapping(value="/updateDateConfiguration")
    public void updateDateConfiguration(@RequestParam(value="days") String days, @RequestParam(value="user") String user) {
		configurationDAO.updateDateConfiguration(days,user);
	}

	@RequestMapping(value="/checkIfCanClose")
	public int checkIfCanClose(@RequestParam(value="id") String topicId) {
		Topic topic = topicDAO.getTopicById(topicId);
		int days = configurationDAO.getDateConfiguration();
		Date createDate = topic.getCreatedDate();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		if(createDate.compareTo(new Date(calendar.getTimeInMillis())) <= 0 ){
			return 0;
		}else{
			return 1;
		}
	}
	
	@RequestMapping(value="/getRateConfiguration")
    public RateConfiguration getRateConfiguration() {
		return configurationDAO.getRateConfiguration();
    }

	@RequestMapping(value="/updateRateConfiguration", method=RequestMethod.POST)
    public void updateRateConfiguration(@RequestBody RateConfiguration rateConfiguration) {
		configurationDAO.updateRateConfiguration(rateConfiguration);
	}
}
