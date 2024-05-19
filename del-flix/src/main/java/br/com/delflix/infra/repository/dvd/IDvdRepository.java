package br.com.delflix.infra.repository.dvd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.delflix.domain.entity.Dvd;
import jakarta.transaction.Transactional;

@Repository
public interface IDvdRepository extends JpaRepository<Dvd, Long>
{
    Dvd findByTitle(String title);  

    @Query("SELECT d FROM Dvd d WHERE d.Identifier = :identifier")
    Dvd findByIdentifier(@Param("identifier") String identifier);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Dvd d WHERE d.Identifier = :identifier")
    void deleteByIdentifier(@Param("identifier") String identifier);
}
