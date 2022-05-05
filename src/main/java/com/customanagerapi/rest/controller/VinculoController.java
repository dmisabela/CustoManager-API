package com.customanagerapi.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.customanagerapi.domain.entity.VinculoUsuarioEmpresa;
import com.customanagerapi.service.VinculoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vinculos")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://customanager.netlify.app"})
public class VinculoController {
	
	private final VinculoService vinculoService; 
	
	
	@PostMapping("/criar-vinculo")
	@ApiOperation("Cadastro de vinculo")
	@ResponseStatus(HttpStatus.CREATED)
	public VinculoUsuarioEmpresa insertVinculo(@RequestBody @Valid VinculoUsuarioEmpresa vinculo) throws Exception {		
		return vinculoService.salvar(vinculo);
	}
	
	@GetMapping("/get-vinculos-by-empresa-id")
	@ApiOperation("Obter todos os vínculos de uma empresa específica")
	public List<VinculoUsuarioEmpresa> getVinculosByEmpresaId(long idEmpresa) throws Exception {		
		
		return vinculoService.getVinculosByEmpresaId(idEmpresa);
	}
	
	@PutMapping("/update")
	@ApiOperation("Alterar vínculo")
	public VinculoUsuarioEmpresa updateVinculo(@RequestBody @Valid VinculoUsuarioEmpresa vinculo) throws Exception {
		return vinculoService.update(vinculo);
	}
	

}
