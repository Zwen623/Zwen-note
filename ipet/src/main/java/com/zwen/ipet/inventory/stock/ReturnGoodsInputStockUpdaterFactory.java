package com.zwen.ipet.inventory.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.inventory.dao.GoodsStockDAO;
import com.zwen.ipet.common.constant.CollectionSize;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.inventory.domain.GoodsStockDO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderItemDTO;

/**
 * 退货入库库存更新命令的工厂
 * @author zwen
 *
 */
@Component
public class ReturnGoodsInputStockUpdaterFactory<T> 
		extends AbstractStockUpdaterFactory<T> {
	
	/**
	 * 构造函数
	 * @param goodsStockDAO 商品库存管理模块的DAO组件
	 * @param dateProvider 日期辅助组件
	 */
	@Autowired
	public ReturnGoodsInputStockUpdaterFactory(
			GoodsStockDAO goodsStockDAO, 
			DateProvider dateProvider) {
		super(goodsStockDAO, dateProvider);
	}

	/**
	 * 获取商品sku id集合
	 * @return 商品sku id集合
	 * @throws Exception
	 */
	@Override
	protected List<Long> getGoodsSkuIds(T parameter) throws Exception {		
		ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO = (ReturnGoodsInputOrderDTO) parameter;
		List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItemDTOs = 
				returnGoodsInputOrderDTO.getItems();
		
		if(returnGoodsInputOrderItemDTOs == null || returnGoodsInputOrderItemDTOs.size() == 0) {
			return new ArrayList<Long>();
		}
		
		List<Long> goodsSkuIds = new ArrayList<Long>(returnGoodsInputOrderItemDTOs.size());
		
		for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItemDTO : returnGoodsInputOrderItemDTOs) {
			goodsSkuIds.add(returnGoodsInputOrderItemDTO.getGoodsSkuId());
		}
		
		return goodsSkuIds;
	}

	/**
	 * 创建库存更新命令
	 * @param goodsStockDOs 商品库存DO对象集合
	 * @return 库存更新命令
	 * @throws Exception
	 */
	@Override
	protected StockUpdater create(
			List<GoodsStockDO> goodsStockDOs,
			T parameter) throws Exception {
		ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO = (ReturnGoodsInputOrderDTO) parameter;
		List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItemDTOs = 
				returnGoodsInputOrderDTO.getItems();
		
		Map<Long, ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItemDTOMap = 
				new HashMap<Long, ReturnGoodsInputOrderItemDTO>(CollectionSize.DEFAULT);
		
		if(returnGoodsInputOrderItemDTOs != null && returnGoodsInputOrderItemDTOs.size() > 0) {
			for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItemDTO : returnGoodsInputOrderItemDTOs) {
				returnGoodsInputOrderItemDTOMap.put(returnGoodsInputOrderItemDTO.getGoodsSkuId(), 
						returnGoodsInputOrderItemDTO);
			}
		}
		
		return new ReturnGoodsInputStockUpdater(goodsStockDOs, goodsStockDAO, 
				dateProvider, returnGoodsInputOrderItemDTOMap); 
	}

}
