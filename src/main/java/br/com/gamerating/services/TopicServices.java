package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.bean.User;

@RestController
public class TopicServices {

	@RequestMapping(value="/getTopicsByGameId")
	public ArrayList<Topic> getTopicsByGameId(@RequestParam(value="id") String id) {
		ArrayList<Topic> listaTopicos = new ArrayList<Topic>();
		Topic topic = new Topic();
		topic.setTitle("Topico 1");
		topic.setId(1);
		Topic topic2 = new Topic();
		topic2.setTitle("Topico 2");
		topic2.setId(2);
		Topic topic3 = new Topic();
		topic3.setTitle("Topico 3");
		topic3.setId(3);
		listaTopicos.add(topic);
		listaTopicos.add(topic2);
		listaTopicos.add(topic3);		
		return listaTopicos;
    }
	
	@RequestMapping(value="/getCommentsByTopicId")
	public ArrayList<Comment> getCommentsByTopicId(@RequestParam(value="id") String id) {
		ArrayList<Comment> listaComments = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setUser("Usuario1");
		comment.setBody("Fiz um Comentário");
		Comment comment2 = new Comment();
		comment2.setUser("Usuario2");
		comment2.setBody("Fiz um Comentário");
		Comment comment3 = new Comment();
		comment3.setUser("Usuario3");
		comment3.setBody("Fiz um Comentário");
		listaComments.add(comment);
		listaComments.add(comment2);
		listaComments.add(comment3);
		return listaComments;
	}
	
	@RequestMapping(value="/getTopicById")
	public Topic getTopicById(@RequestParam(value="id") String id) {
		Topic topic = new Topic();
		topic.setTitle("Topico 1");
		topic.setBody("Eu sou o corpo do Tópico 1");
		topic.setId(1);
			
		return topic;
    }
	
	@RequestMapping(value="/addComentario", method=RequestMethod.POST)
    public Topic login(@RequestBody Comment comment) {
		//Adicionar Comentario
		//Carregar Topico
		return new Topic();
    }
}
