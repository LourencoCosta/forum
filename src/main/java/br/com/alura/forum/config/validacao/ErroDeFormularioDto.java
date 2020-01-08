package br.com.alura.forum.config.validacao;

public class ErroDeFormularioDto {

	private String field;
	private String mensagem;
	
	public ErroDeFormularioDto(String field, String mensagem) {
		this.field = field;
		this.mensagem = mensagem;
	}

	public String getField() {
		return field;
	}

	public String getMensagem() {
		return mensagem;
	}

}
