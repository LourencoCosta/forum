package br.com.alura.forum.controllers.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;

public class RespostaDto {
	private Long id;
	private String mensagem;
	private LocalDateTime dataDaCriacao;
	private String nomeAutor;

	public RespostaDto (Resposta resposta){
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataDaCriacao = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}
	
}
