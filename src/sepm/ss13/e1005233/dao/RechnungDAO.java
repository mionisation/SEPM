package sepm.ss13.e1005233.dao;

import java.sql.Timestamp;
import sepm.ss13.e1005233.domain.Rechnung;

/**
 * 
 *
 */
public interface RechnungDAO {
	public void insertRechnung (Rechnung r);
	public Rechnung getRechnung (Timestamp date);
	public void updateRechnung (Timestamp date);
	public void deleteRechnung (Timestamp date);
}
