package com.taobao.portal.pojo;

import com.taobao.pojo.TbItem;

public class ItemInfo extends TbItem {
	
	public String[] getImages(){
		String image = getImage();
		if(image != null){
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
