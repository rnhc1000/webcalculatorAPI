package br.dev.ferreiras.webcalculatorapi.repository;

import br.dev.ferreiras.webcalculatorapi.entity.Records;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecordsRepository extends JpaRepository<Records, Long> {
  /**
   * @param username username associated to his records
   * @param paging   initial page and total pages
   * @return List of records bound to a username
   */

  @Query(
      """
              SELECT r FROM Records r where r.username = ?1% AND r.deleted = false
      """
  )
  Page<Records> findRecordsByUsername(String username, Pageable paging);

  @Query (
      """
              SELECT r FROM Records r where r.username = ?1% AND r.deleted = true
      """
  )
  Page<Records> findSoftDeletedRecordsByUsername(String username, Pageable paging);


}
