package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Game;

@RestController
public class GameServices {

	@RequestMapping("/listJogos")
    public ArrayList<Game> listJogos() {
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
		game1.setId(3);
		game3.setDescription("Meu jogo " + game3.getName());
		
		listaJogos.add(game1);
		listaJogos.add(game2);
		listaJogos.add(game3);
		return listaJogos;
    }
}
