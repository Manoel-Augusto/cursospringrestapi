package curso.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	// Ingeção repository
	@Autowired
	private UsuarioRepository usuarioRepository;

	// EXEMPLO Gerar relatorio PDF
	@GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
	public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id,
			@PathVariable(value = "venda") Long venda) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		// o retorno seria um relatorio
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	// Buscar por id
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> init(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = usuarioRepository.findById(id);

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	// Buscar todos
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario() {
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}

	// Salvando dados
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
		
		for(int pos=0; pos<usuario.getTelefones().size();pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	// EXEMPLO post venda 
	@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity<Usuario> cadastrarVenda(@PathVariable Long iduser, @PathVariable Long idvenda) {
		/* Processo de venda */
		// Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity("id user:" + iduser + "idvenda:" + idvenda, HttpStatus.OK);
	}

	// Atualizar dados
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	// Atualizar VENDA
		@PutMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
		public ResponseEntity atualizarVenda(@RequestBody Usuario usuario) {
			
			//Usuario usuarioSalvo = usuarioRepository.save(usuario);

			return new ResponseEntity("Venda atualizada", HttpStatus.OK);
		}
		
		// Deletar por id
		@DeleteMapping(value = "/{id}", produces = "application/text")
		public String delete(@PathVariable(value = "id") Long id) {

			usuarioRepository.deleteById(id);

			return "ok";
		}
		
		
		// Deletar Venda por ID
				@DeleteMapping(value = "/{id}/venda", produces = "application/text")
				public String deleteVenda(@PathVariable(value = "id") Long id) {

					usuarioRepository.deleteById(id);/*Deleta todas as vendas do usuario*/

					return "ok";
				}

}
