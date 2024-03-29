package com.zwen.ipet.common.json;

/**
 * json表达式接口
 * @author zwen
 *
 */
public interface JsonExpression {

	/**
	 * 解释表达式
	 * @param context 上下文
	 * @return 结果
	 * @throws Exception
	 */
	Object interpret(JsonExpressionContext context) throws Exception;
	
}
