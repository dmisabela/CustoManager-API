package com.customanagerapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.customanagerapi.domain.entity.Usuario;
import com.customanagerapi.domain.utils.SearchRequest;
import com.customanagerapi.domain.utils.SearchSpecification;
import com.customanagerapi.exception.RegistroNaoEncontradoException;
import com.customanagerapi.exception.RegraNegocioException;
import com.customanagerapi.exception.UsuarioOuSenhaInvalidaException;
import com.customanagerapi.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {	
	
	@Autowired
	private PasswordEncoder encoder;

		
	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public Usuario cadastrar(Usuario usuario) throws Exception {	
		
		try {

			this.existsByCpf(usuario);
			this.existsByEmail(usuario);			
			calculaIdade(usuario.getDataNascimento());	
			this.formatData(usuario);	
			
			String senhaCriptografada = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);	
			usuario.setDataCriacao(LocalDateTime.now());
			
			return repository.save(usuario);
		}
		
		catch(Exception e) {
			throw new Exception("Erro: " + e.getMessage());
		}
		
	}	
	
	@Transactional
	public Usuario update(Usuario usuario) throws Exception {	
		
		try {

			this.existsByCpf(usuario);
			this.existsByEmail(usuario);			
			calculaIdade(usuario.getDataNascimento());				
			this.formatData(usuario);			
			
			return repository.save(usuario);
		}
		
		catch(Exception e) {
			throw new Exception("Erro: " + e.getMessage());
		}
		
	}
	
	@Transactional
	public Usuario changePassword(Usuario usuario, String senhaInformada) throws Exception {	
		
		try {

			Usuario actualUser = repository.findById(usuario.getId());			
			String senhaAntiga = actualUser.getSenha();
			
			if(!encoder.matches(senhaInformada, senhaAntiga)) {
				throw new Exception("Senha antiga informada não coincide com a cadastrada.");
			}
			
			else {
				
				String novaSenha = encoder.encode(usuario.getSenha());				
				usuario.setSenha(novaSenha);	
				
				return repository.save(usuario);
			}	
			
		}		
		catch(Exception e) {
			throw new Exception("Erro: " + e.getMessage());
		}
		
	}
	
	
	public Boolean existsByCpf(Usuario usuario) {
		
		if(repository.existsById(usuario.getId())) {
			
			Usuario actualUser = repository.findById(usuario.getId());
			
			String oldCpf = actualUser.getCpf();
			String newCpf = usuario.getCpf();
			
			if(!newCpf.equals(oldCpf) && repository.existsByCpf(newCpf)) {
				throw new RegraNegocioException("CPF já cadastrado");
			}	
			else {
				return false;
			}
		}	
		
		else {	
			
			if(repository.existsByCpf(usuario.getCpf())) {
				throw new RegraNegocioException("CPF já cadastrado");
			}		
			else {
				return false;
			}
			
		}
		
	}
	
	
	public Boolean existsByEmail(Usuario usuario) {
		
		if(repository.existsById(usuario.getId())) {
			
			Usuario actualUser = repository.findById(usuario.getId());
			
			String oldEmail = actualUser.getLogin();
			String newEmail = usuario.getLogin();
			
			if(!newEmail.equals(oldEmail) && repository.existsByLogin(newEmail)) {
				throw new RegraNegocioException("E-mail já cadastrado");
			}	
			else {
				return false;
			}
		}	
		
		else {	
			
			if(repository.existsByLogin(usuario.getLogin())) {
				throw new RegraNegocioException("E-mail já cadastrado");
			}		
			else {
				return false;
			}
			
		}
		
	}
	
	
	public Usuario formatData(Usuario usuario) {
		
		usuario.setNome(usuario.getNome().replaceAll("[ ]+", " "));
		usuario.setEndereco(usuario.getEndereco().replaceAll("[ ]+", " "));
		
		return usuario;
	}
	
	
	public static int calculaIdade(LocalDate dataNasc) {

        Calendar dataNascimento = Calendar.getInstance();        
        dataNascimento.set(dataNasc.getYear(), dataNasc.getMonthValue()-1, dataNasc.getDayOfMonth());
       
        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            idade--;
        } else {
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)
                    && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--;
            }
        }
        

		if(idade < 18 || idade > 120) {
			throw new RegraNegocioException("Idade deve ser entre 18 e 120!");
		}

        return idade;
    }
	
    public Page<Usuario> searchUsuarios(SearchRequest request, String orderBy, 
    		Boolean orderAsc, Integer pageNumber, Integer pageSize) {
        SearchSpecification<Usuario> specification = new SearchSpecification<>(request);
        Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        
        return repository.findAll(specification, pageable);
    }

	
	@Transactional
	public Page<Usuario> getAllUsers(
			String orderBy, 
			Boolean orderAsc,
			Integer pageNumber, 
			Integer pageSize) {	
		
		Sort sort = orderAsc ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		return repository.findAll(pageable);
	} 
	
	@Transactional
	public Usuario getUserById(long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public Usuario getByCpf(String cpf) {		
		
		Usuario user = repository.findByCpf(cpf);	
		
		if(user == null) {
			throw new RegistroNaoEncontradoException("Usuário não encontrado");
		}
		
		return user;
	}
		
	@Transactional
	public void delete(Long id) { 
		repository.deleteById(id);
	}
	
	
	public UserDetails autenticar(Usuario usuario) throws UsuarioOuSenhaInvalidaException {		
		
		UserDetails user = loadUserByUsername(usuario.getLogin());
		
		boolean senhaCorreta = encoder.matches(usuario.getSenha(), user.getPassword());
		
		
		if(senhaCorreta) {			
			Usuario verifyClaims = repository.findByLogin(usuario.getLogin())
									.orElseThrow(() -> new UsuarioOuSenhaInvalidaException());
			
			usuario.setId(verifyClaims.getId());
			usuario.setAdmin(verifyClaims.isAdmin());	
			usuario.setNome(verifyClaims.getNome());	
			return user;
		}
		
		throw new UsuarioOuSenhaInvalidaException();
		
	}	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsuarioOuSenhaInvalidaException {
		
		Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsuarioOuSenhaInvalidaException());
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

}
