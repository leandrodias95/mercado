package com.mercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mercado.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query("Select p from Produto p join p.cliente c where upper(c.nome) like upper(:nomeCliente) "
			+ "and p.codProduto like :codProduto and DAY(p.dataCompra) =:dia and MONTH(p.dataCompra) =:mes and YEAR(p.dataCompra) =:ano")
	List<Produto> meetProduct(
	@Param("nomeCliente") String nomeCliente, @Param("codProduto")String codProduto, @Param("dia")Integer dia, @Param("mes") Integer mes, @Param("ano") Integer ano);

	@Query("Select p from Produto p join p.cliente c where upper(c.nome) like upper(:nomeCliente) "
			+ "and p.codProduto like :codProduto")
	List<Produto> meetProductCod(
	@Param("nomeCliente") String nomeCliente, @Param("codProduto")String codProduto);

	@Query("Select p from Produto p join p.cliente c where upper(c.nome) like upper(:nomeCliente)")
	List<Produto> meetProductCodPorNomeCliente(String nomeCliente);
	
}
