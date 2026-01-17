package org.imt.tournamentmaster.repository.reporting;

import org.imt.tournamentmaster.model.reporting.ImportReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportReportRepository extends CrudRepository<ImportReport, Long> {
}
