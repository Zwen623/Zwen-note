package com.zwen.ipet.schedule.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.schedule.dao.ScheduleGoodsStockDAO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsStockDO;
import com.zwen.ipet.schedule.mapper.ScheduleGoodsStockMapper;

/**
 * 商品库存管理DAO组件
 * @author zwen
 *
 */
@Repository
public class ScheduleGoodsStockDAOImpl implements ScheduleGoodsStockDAO {
	
	/**
	 * 商品库存管理mapper组件
	 */
	@Autowired
	private ScheduleGoodsStockMapper stockMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 根据商品sku id查询商品库存
	 * @param goodsSkuId 商品sku id
	 * @return 商品库存
	 */
	@Override
	public ScheduleGoodsStockDO getBySkuId(Long goodsSkuId) throws Exception {
		ScheduleGoodsStockDO goodsStock = stockMapper.getBySkuId(goodsSkuId) ;
		
		if(goodsStock == null) {
			goodsStock = new ScheduleGoodsStockDO();
			goodsStock.setGoodsSkuId(goodsSkuId); 
			goodsStock.setAvailableStockQuantity(0L); 
			goodsStock.setLockedStockQuantity(0L); 
			goodsStock.setOutputStockQuantity(0L); 
			save(goodsStock);  
		}
		
		return goodsStock;
	}
	
	/**
	 * 新增商品库存
	 * @param goodsStockDO 商品库存DO对象
	 */
	@Override
	public void save(ScheduleGoodsStockDO goodsStock) throws Exception {
		goodsStock.setGmtCreate(dateProvider.getCurrentTime()); 
		goodsStock.setGmtModified(dateProvider.getCurrentTime()); 
		stockMapper.save(goodsStock); 
	}
	
	/**
	 * 更新商品库存
	 * @param goodsStockDO 商品库存DO对象
	 */
	@Override
	public void update(ScheduleGoodsStockDO goodsStock) throws Exception {
		goodsStock.setGmtModified(dateProvider.getCurrentTime()); 
		stockMapper.update(goodsStock); 
	}

}
