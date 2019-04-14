package db.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import db.entity.Deliver;



@Repository("deliverDao")
public class DeliverDao {

	@Resource
	private BaseDao<Deliver> baseDao;
	
	public Deliver getDeliverByDelivername(String username) {
		return baseDao.get("select c from Deliver c where c.deliver_name=?0", new Object[] {username}, Deliver.class);
	}
	
	public void saveDeliver(Deliver deliver) {
		baseDao.save(deliver);
	}
	
	public void saveOrUpdateDeliver(Deliver deliver) {
		baseDao.saveOrUpdate(deliver);
	}
	
}
