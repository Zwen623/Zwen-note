package com.zwen.ipet.commodity.service;

import java.util.List;

import com.zwen.ipet.commodity.domain.GoodsSkuDTO;
import com.zwen.ipet.commodity.domain.GoodsSkuQuery;

/**
 * 商品sku管理service接口
 * @author zwen
 *
 */
public interface GoodsSkuService {
	
	/**
	 * 根据商品id查询商品sku
	 * @param goodsId 商品id 
	 * @return 商品sku
	 * @throws Exception
	 */
	List<GoodsSkuDTO> listByGoodsId(Long goodsId) throws Exception;

	/**
	 * 批量新增商品sku
	 * @param goodsSkus 商品sku
	 * @throws Exception
	 */
	void batchSave(List<GoodsSkuDTO> goodsSkus) throws Exception;
	
	/**
	 * 根据商品id删除sku
	 * @param goodsId 商品id
	 * @throws Exception
	 */
	void removeByGoodsId(Long goodsId) throws Exception;

	/**
	 * 根据id查询商品sku
	 * @param id 商品sku id
	 * @return 商品sku
	 * @throws Exception
	 */
	GoodsSkuDTO getById(Long id) throws Exception;
	
	/**
	 * 分页查询商品sku
	 * @param query 查询条件
	 * @return 商品sku
	 * @throws Exception
	 */
	List<GoodsSkuDTO> listByPage(GoodsSkuQuery query) throws Exception;
	
}
