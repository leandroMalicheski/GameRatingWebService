package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Game;

@RestController
public class GameServices {

	@RequestMapping(value="/listJogos")
    public ArrayList<Game> listJogos(@RequestParam(value="busca") String busca) {
		ArrayList<Game> listaJogos = new ArrayList<Game>();
		Game game1 = new Game();
		game1.setName("BF Hardline");
		game1.setId(1);
		game1.setDescription("Meu jogo " + game1.getName());
		Game game2 = new Game();
		game2.setName("BF Hardline 2");
		game2.setId(2);
		game2.setDescription("Meu jogo " + game2.getName());
		Game game3 = new Game();
		game3.setName("BF Hardline 3");
		game3.setId(3);
		game3.setDescription("Meu jogo " + game3.getName());

		if(busca.equalsIgnoreCase("bf")){
			listaJogos.add(game1);
			listaJogos.add(game2);
			listaJogos.add(game3);
		}		
		return listaJogos;
    }
	
	@RequestMapping(value="/getJogoById")
    public Game listJogoById(@RequestParam(value="id") String id) {
		Game game = new Game();
		game.setName("BF Hardline");
		game.setId(Integer.valueOf(id));
		game.setDescription("É um videojogo do genero first-person shooter, produzido pela Visceral Games em colaboração com a EA Digital Illusions CE e publicado pela Electronic Arts.");
		game.setLaunchDate("17/03/2015");
		game.setPlatforms("PlayStation 4, Xbox One, PlayStation 3, Xbox 360, Microsoft Windows");
		game.setDevs("Visceral Games, EA Digital Illusions CE, Criterion Games");
		game.setIsVisible(true);
		game.setRatingAudio(2);
		game.setRatingDiversao(7);
		game.setRatingImersao(10);
		game.setRatingJogabilidade(0);
		game.setRatingMedio(4);
		return game;		
	}
	
	@RequestMapping(value="/hideGame", method=RequestMethod.POST)
    public Game hideGame(@RequestBody Game game) {
		if(game.getIsVisible()){
			game.setIsVisible(false);
		}else{
			game.setIsVisible(true);
		}
		return game;
	}
}
