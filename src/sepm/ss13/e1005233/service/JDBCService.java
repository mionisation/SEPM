package sepm.ss13.e1005233.service;

import java.util.List;

import sepm.ss13.e1005233.dao.PferdDAO;
import sepm.ss13.e1005233.dao.RechnungDAO;
import sepm.ss13.e1005233.domain.Pferd;
import sepm.ss13.e1005233.domain.Rechnung;

public class JDBCService implements Service {

	private PferdDAO pferdDao;
	private RechnungDAO rechnungDao;
	
	@Override
	public void insertPferd(Pferd p) throws PferdValidationException {
		
		try {
			pferdDao.insertPferd(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createRechnung(Rechnung r) throws RechnungValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Pferd getPferd(Pferd p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePferd(Pferd p) throws PferdValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePferd(Pferd p) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Pferd> findAllPferde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pferd> getPopularPferde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verteurePferde(List<Pferd> lp) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rechnung getRechnung(Rechnung r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rechnung> findAllRechnungen() {
		// TODO Auto-generated method stub
		return null;
	}

}
