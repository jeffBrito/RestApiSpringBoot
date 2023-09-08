package com.example.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dto.ProdutoRecordDto;
import com.example.springboot.model.Produto;
import com.example.springboot.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
public class ProdutoController {

	//ponto de injenção de dados
	@Autowired
	ProdutoRepository produtoRepository;

	@PostMapping("/novoProduto")
	public ResponseEntity<Produto> saveProduto(@RequestBody @Valid ProdutoRecordDto produtoRecordDto){
		var produto = new Produto();
		
		//Converter de DTO para model
		BeanUtils.copyProperties(produtoRecordDto, produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Produto>> getAllProduto(){
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
	}
	
	@GetMapping("/{idProduto}")
	public ResponseEntity<Object> getOneProduto(@PathVariable(value="idProduto") Long idProduto){
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		if(produto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não existe em nosso sistema.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(produto.get());
	}
	
}
