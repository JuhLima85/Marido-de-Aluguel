package com.codedeving.Marido.de.Aluguel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codedeving.Marido.de.Aluguel.entity.ServicoPrestado;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {
		@Query("SELECT sp FROM ServicoPrestado sp JOIN sp.cliente c "
				+ "WHERE UPPER(c.nome) LIKE UPPER(:nome) AND MONTH(sp.data) = :mes")
		List<ServicoPrestado> findByClienteNomeContainingIgnoreCaseAndData_Month(@Param("nome") String nome,
				@Param("mes") Integer mes);

		@Query("SELECT sp FROM ServicoPrestado sp JOIN sp.cliente c " + "WHERE UPPER(c.nome) LIKE UPPER(:nome)")
		List<ServicoPrestado> findByClienteNomeContainingIgnoreCase(@Param("nome") String nome);

		@Query("SELECT sp FROM ServicoPrestado sp WHERE MONTH(sp.data) = :mes")
		List<ServicoPrestado> findByData_Month(@Param("mes") Integer mes);
		
		@Query("SELECT sp FROM ServicoPrestado sp JOIN sp.cliente c " + " WHERE c.cpf = :cpf")
		List<ServicoPrestado> findByCpf(@Param("cpf") String cpf);
		
		@Query("SELECT sp FROM ServicoPrestado sp JOIN sp.cliente c " + " WHERE c.id = :id")
		List<ServicoPrestado> findByIdCliente(@Param("id") Integer id);
		
		@Modifying
		@Query("DELETE FROM ServicoPrestado sp WHERE sp.cliente.id = :id")
		void deletarServicosDeUmCliente(@Param("id") Integer id);

		List<ServicoPrestado> findAll();		

}
