package sepm.ss13.e1005233.dao;

import sepm.ss13.e1005233.domain.Pferd;

/**
 *
 *
 */
public interface PferdDAO {
	public void insertPferd (Pferd p);
	public Pferd getPferd (int id);
	public void updatePferd (int id);
	public void deletePferd(int id);
}
