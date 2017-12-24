package com.taobao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.common.utils.HttpClientUtil;
import com.taobao.common.utils.JsonUtils;
import com.taobao.common.utils.TaobaoResult;
import com.taobao.pojo.TbItemDesc;
import com.taobao.pojo.TbItemParamItem;
import com.taobao.portal.pojo.ItemInfo;
import com.taobao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	@Override
	public ItemInfo getItemById(long itemId) {
		try {
			// 调用 rest 的服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaobaoResult taobaoResult = TaobaoResult.formatToPojo(json, ItemInfo.class);
				if (taobaoResult.getStatus() == 200) {
					ItemInfo itemInfo = (ItemInfo) taobaoResult.getData();
					return itemInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 商品描述 Service
	 */
	@Override
	public String getItemDescById(long itemId) {
		try {
			// 查询商品描述
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			// 转换成 java 对象
			TaobaoResult taobaoResult = TaobaoResult.formatToPojo(json, TbItemDesc.class);
			if (taobaoResult.getStatus() == 200) {
				TbItemDesc itemDesc = (TbItemDesc) taobaoResult.getData();
				// 取商品描述信息
				String result = itemDesc.getItemDesc();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getItemParam(long itemId) {
		try {
			//查询商品规格参数
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
				//把json转换成java对象
				TaobaoResult taobaoResult = TaobaoResult.formatToPojo(json, TbItemParamItem.class);
				if(taobaoResult.getStatus() == 200){
					TbItemParamItem itemParamItem = (TbItemParamItem) taobaoResult.getData();
					String paramData = itemParamItem.getParamData();
					//生成html
					// 把规格参数json数据转换成java对象
					List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for(Map m1:jsonList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
						sb.append("        </tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for(Map m2:list2) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
							sb.append("            <td>"+m2.get("v")+"</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");
					//返回html片段
					return sb.toString();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
