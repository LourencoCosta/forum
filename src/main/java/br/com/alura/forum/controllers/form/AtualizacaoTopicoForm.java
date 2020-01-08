package br.com.alura.forum.controllers.form;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import com.sun.istack.NotNull;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicosRepository;

public class AtualizacaoTopicoForm {

	@NotEmpty @NotNull @Length(min=5)
	private String titulo;
	@NotEmpty @NotNull @Length(min=5)
	private String mensagem;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Topico Atualizar (Long id, TopicosRepository topicosRepository){
		Topico topico = topicosRepository.getOne(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		return topico;
		
		
		
	}
	
	
}
