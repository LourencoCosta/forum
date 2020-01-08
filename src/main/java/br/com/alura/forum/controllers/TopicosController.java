package br.com.alura.forum.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controllers.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controllers.dto.TopicoDto;
import br.com.alura.forum.controllers.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controllers.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicosRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	TopicosRepository topicosRepository;

	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		
		if ( nomeCurso == null || nomeCurso.isEmpty()){
			List<Topico> topicos = topicosRepository.findAll();
			return TopicoDto.converter (topicos);
		}else{
			List<Topico> topicos = topicosRepository.findByCurso_Nome(nomeCurso);
			return TopicoDto.converter (topicos);
		}
	}
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
		
		Topico topico = form.converter(cursoRepository);
		topicosRepository.save(topico);
		
		URI uri = uriBuilder.path ("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity <DetalhesDoTopicoDto> detalhar(@PathVariable Long id){
		Optional <Topico> topico = topicosRepository.findById(id);
		if (topico.isPresent()){
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar (@PathVariable Long id,@RequestBody @Valid AtualizacaoTopicoForm  form){
		
		Optional <Topico> optional = topicosRepository.findById(id);
		if (optional.isPresent()){
			Topico topico = form.Atualizar(id, topicosRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover (@PathVariable Long id){
		Optional <Topico> optional = topicosRepository.findById(id);
		if (optional.isPresent()){
			topicosRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
		
		
	}
}